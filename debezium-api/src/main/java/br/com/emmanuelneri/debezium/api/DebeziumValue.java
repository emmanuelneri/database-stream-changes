package br.com.emmanuelneri.debezium.api;

import io.vertx.core.json.JsonObject;

final class DebeziumValue extends DebeziumStruct {

    private static final String BEFORE_FIELD = "before";
    private static final String AFTER_FIELD = "after";

    private final JsonObject before;
    private final JsonObject after;

    DebeziumValue(final String jsonValue) {
        super(jsonValue);
        if (getPayload() == null) {
            this.before = null;
            this.after = null;
        } else {
            this.before = getAtribute(getPayload(), BEFORE_FIELD);
            this.after = getAtribute(getPayload(), AFTER_FIELD);
        }
    }

    JsonObject getOldValue() {
        return this.before;
    }

    JsonObject getNewValue() {
        return this.after;
    }
}
