package br.com.emmanuelneri.sale.consumer;

import br.com.emmanuelneri.sale.consumer.interfaces.SaleConsumer;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

public class SaleConsumerApplication {

    public static void main(final String[] args) {
        final JsonObject configuration = new JsonObject()
                .put("bootstrap.servers", getKafkaBootstrapServers())
                .put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .put("auto.offset.reset", "earliest")
                .put("enable.auto.commit", "false");

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SaleConsumer(configuration));
    }

    private static String getKafkaBootstrapServers() {
        final String kafkaBootstrapServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
        return Objects.nonNull(kafkaBootstrapServers) ? kafkaBootstrapServers : "localhost:9092";
    }
}
