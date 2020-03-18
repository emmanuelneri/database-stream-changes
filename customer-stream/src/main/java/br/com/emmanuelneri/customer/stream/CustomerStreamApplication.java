package br.com.emmanuelneri.customer.stream;

import br.com.emmanuelneri.customer.schema.Customer;
import br.com.emmanuelneri.debezium.api.DebeziumObject;
import br.com.emmanuelneri.debezium.api.EventType;
import br.com.emmanuelneri.debezium.api.Struct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class CustomerStreamApplication {

    private static final Logger LOGGER = Logger.getLogger(CustomerStreamApplication.class.getName());

    private static final String APPLICATION_ID = "customer-stream";
    private static final String CLIENT_ID = "customer-consumer";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String OFFSET_CONFIG = "earliest";

    private static final String DEBEZIUM_CUSTOMER_TOPIC = "db.public.customer";
    private static final String CUSTOMER_TOPIC = "customer";

    public static void main(final String[] args) {
        LOGGER.info("starting KStream: " + APPLICATION_ID);
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, Customer> customerStream = builder.stream(DEBEZIUM_CUSTOMER_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .map((KeyValueMapper<String, String, KeyValue<String, Customer>>) (key, value) -> {
                    final DebeziumObject debeziumObject = new DebeziumObject(key, value);
                    if (debeziumObject.getEventType() == EventType.DELETE) {
                        final String identifier = debeziumObject.getIdentifier();
                        return KeyValue.pair(identifier, null);
                    }

                    final Struct newValue = debeziumObject.getNewValue();
                    final Customer customer = CustomerMapper.to(newValue);
                    return KeyValue.pair(customer.getId().toString(), customer);
                });

        customerStream.to(CUSTOMER_TOPIC, Produced.with(Serdes.String(), Serdes.serdeFrom(Customer.serializer, Customer.deserializer)));

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
