package br.com.emmanuelneri.sale.stream;

import br.com.emmanuelneri.debezium.api.DebeziumObject;
import br.com.emmanuelneri.debezium.api.EventType;
import br.com.emmanuelneri.sale.stream.interfaces.FullSale;
import br.com.emmanuelneri.sale.stream.interfaces.Sale;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
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

import java.util.Properties;

public class SaleStreamApplication {

    private static final String APPLICATION_ID = "sale-stream";
    private static final String CLIENT_ID = "sale-stream-consumer";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String OFFSET_CONFIG = "earliest";

    private static final String DEBEZIUM_SALE_TOPIC = "db.public.sale";
    private static final String SALE_TOPIC = "sale";

    private static final String CUSTOMER_STORE = "customer-store";
    private static final String CUSTOMER_STREAM = "customer";

    public static void main(final String[] args) {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<Long, String> saleStream = builder.stream(DEBEZIUM_SALE_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .map((KeyValueMapper<String, String, KeyValue<Long, String>>) (key, value) -> {

                    final DebeziumObject debeziumObject = new DebeziumObject(key, value);
                    if (debeziumObject.getEventType() == EventType.DELETE) {
                        final Long identifier = debeziumObject.getIdentifier();
                        return KeyValue.pair(identifier, null);
                    }

                    final JsonObject newValue = debeziumObject.getNewValue();
                    final Sale sale = Json.decodeValue(newValue.toString(), Sale.class);
                    return KeyValue.pair(sale.getId(), newValue.toString());
                });


        final GlobalKTable<Long, String> customerTable = builder.globalTable(CUSTOMER_STREAM,
                Materialized.<Long, String, KeyValueStore<Bytes, byte[]>>as(CUSTOMER_STORE)
                        .withKeySerde(Serdes.Long())
                        .withValueSerde(Serdes.String()));

        saleStream.leftJoin(customerTable,
                (saleId, saleAsString) -> {
                    final Sale sale = Json.decodeValue(saleAsString, Sale.class);
                    return sale.getCustomerId();
                }, (sale, customer) -> Json.encode(new FullSale(sale, customer)))
                .to(SALE_TOPIC, Produced.with(Serdes.Long(), Serdes.String()));

        start(builder);
    }

    private static void start(final StreamsBuilder builder) {
        final Properties streamsConfiguration = createConfig();
        final KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);
        kafkaStreams.cleanUp(); // Only to test
        kafkaStreams.start();
    }

    private static Properties createConfig() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_CONFIG);
        return streamsConfiguration;
    }

}
