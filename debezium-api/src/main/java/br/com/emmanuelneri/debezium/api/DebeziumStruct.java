package br.com.emmanuelneri.debezium.api;

import io.vertx.core.json.JsonObject;

abstract class DebeziumStruct {

    private static final String PAYLOAD_FIELD = "payload";

    private final JsonObject payload;

    DebeziumStruct(final String jsonValue) {
        if (jsonValue == null) {
            this.payload = null;
        } else {
            final JsonObject value = new JsonObject(jsonValue);
            this.payload = this.getAtribute(value, PAYLOAD_FIELD);
        }
    }

    JsonObject getPayload() {
        return payload;
    }

    JsonObject getAtribute(final JsonObject jsonObject, final String atribute) {
        return jsonObject.getJsonObject(atribute);
    }

}
