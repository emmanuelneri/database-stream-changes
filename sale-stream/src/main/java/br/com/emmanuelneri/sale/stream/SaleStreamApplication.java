package br.com.emmanuelneri.sale.stream;

import br.com.emmanuelneri.customer.schema.Customer;
import br.com.emmanuelneri.debezium.api.DebeziumObject;
import br.com.emmanuelneri.debezium.api.EventType;
import br.com.emmanuelneri.debezium.api.Struct;
import br.com.emmanuelneri.sale.schema.EnrichedSale;
import br.com.emmanuelneri.sale.schema.Sale;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class SaleStreamApplication {

    private static final Logger LOGGER = Logger.getLogger(SaleStreamApplication.class.getName());

    private static final String APPLICATION_ID = "sale-stream";
    private static final String CLIENT_ID = "sale-stream-consumer";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String OFFSET_CONFIG = "earliest";

    private static final String DEBEZIUM_SALE_TOPIC = "db.public.sale";
    private static final String SALE_TOPIC = "sale";

    private static final String CUSTOMER_STORE = "customer-store";
    private static final String CUSTOMER_STREAM = "customer";

    public static void main(final String[] args) {
        LOGGER.info("starting KStream: " + APPLICATION_ID);
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, Sale> saleStream = builder.stream(DEBEZIUM_SALE_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .map((KeyValueMapper<String, String, KeyValue<String, Sale>>) (key, value) -> {

                    final DebeziumObject debeziumObject = new DebeziumObject(key, value);
                    if (debeziumObject.getEventType() == EventType.DELETE) {
                        final String identifier = debeziumObject.getIdentifier();
                        return KeyValue.pair(identifier, null);
                    }

                    final Struct newValue = debeziumObject.getNewValue();
                    final Sale sale = SaleMapper.to(newValue);
                    return KeyValue.pair(sale.getId().toString(), sale);
                });

        final GlobalKTable<String, Customer> customerTable = builder.globalTable(CUSTOMER_STREAM,
                Materialized.<String, Customer, KeyValueStore<Bytes, byte[]>>as(CUSTOMER_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.serdeFrom(Customer.serializer, Customer.deserializer)));

        saleStream.leftJoin(customerTable,
                (saleId, sale) -> sale.getCustomerId().toString(), EnrichedSale::new)
                .to(SALE_TOPIC, Produced.with(Serdes.String(),
                        Serdes.serdeFrom(EnrichedSale.serializer, EnrichedSale.deserializer)));

        start(builder);
    }

    private static void start(final StreamsBuilder builder) {
        final Properties streamsConfiguration = createConfig();
        final KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);

        LOGGER.info("Configs: " + streamsConfiguration);

        kafkaStreams.cleanUp(); // Only to test
        kafkaStreams.start();
    }

    private static Properties createConfig() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaBootstrapServers());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_CONFIG);
        return streamsConfiguration;
    }

    private static String getKafkaBootstrapServers() {
        final String kafkaBootstrapServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
        return Objects.nonNull(kafkaBootstrapServers) ? kafkaBootstrapServers : "localhost:9092";
    }

}
