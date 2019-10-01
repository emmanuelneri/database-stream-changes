package br.com.emmanuelneri.debezium.api;

import io.vertx.core.json.JsonObject;

final class DebeziumValue extends DebeziumStruct {

    private static final String BEFORE_FIELD = "before";
    private static final String AFTER_FIELD = "after";
    private static final String OPERATION_FIELD = "op";

    private final JsonObject before;
    private final JsonObject after;
    private final EventType operation;

    DebeziumValue(final String jsonValue) {
        super(jsonValue);
        if (getPayload() == null) {
            this.before = null;
            this.after = null;
            this.operation = null;
        } else {
            this.before = getAtribute(getPayload(), BEFORE_FIELD);
            this.after = getAtribute(getPayload(), AFTER_FIELD);
            this.operation = EventType.fromOperation(getPayload().getString(OPERATION_FIELD));
        }
    }

    JsonObject getOldValue() {
        return this.before;
    }

    JsonObject getNewValue() {
        return this.after;
    }

    EventType getOperation() {
        return operation;
    }
}
