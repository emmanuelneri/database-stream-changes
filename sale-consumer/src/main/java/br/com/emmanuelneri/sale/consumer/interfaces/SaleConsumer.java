package br.com.emmanuelneri.sale.consumer.interfaces;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.kafka.client.consumer.KafkaConsumer;

import java.util.Map;
import java.util.stream.Collectors;

public class SaleConsumer extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleConsumer.class);


    private static final String CONSUMER_GROUP_ID = "sale-consumer";
    private static final String SALE_TOPIC = "sale";

    private final JsonObject configuration;

    public SaleConsumer(final JsonObject configuration) {
        this.configuration = configuration;
    }

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        final KafkaConsumer<String, String> kafkaConsumer = KafkaConsumer.create(this.vertx, createConfig(configuration));
        kafkaConsumer.subscribe(SALE_TOPIC);

        startPromise.complete();

        kafkaConsumer.handler(consumerRecord ->
                kafkaConsumer.commit(asyncResult -> {
                    if (asyncResult.failed()) {
                        LOGGER.error("commit failed", asyncResult.cause());
                        return;
                    }

                    LOGGER.info("sale consumed: {0}", consumerRecord);
                }));
    }

    public Map<String, String> createConfig(final JsonObject configuration) {
        final Map<String, String> config = configuration.getMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().toString()));
        config.put("group.id", CONSUMER_GROUP_ID);

        LOGGER.info("Kafka Consumer configuration: {0}", config);

        return config;
    }
}
