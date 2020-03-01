package br.com.emmanuelneri.sale.consumer;

import br.com.emmanuelneri.sale.consumer.interfaces.SaleConsumer;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class SaleConsumerApplication {

    public static void main(final String[] args) {
        final JsonObject configuration = new JsonObject()
                .put("bootstrap.servers", "localhost:9092")
                .put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
                .put("auto.offset.reset", "earliest")
                .put("enable.auto.commit", "false");

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SaleConsumer(configuration));
    }
}
