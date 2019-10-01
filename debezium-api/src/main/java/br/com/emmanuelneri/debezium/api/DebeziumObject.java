package br.com.emmanuelneri.debezium.api;

import io.vertx.core.json.JsonObject;

public final class DebeziumObject {

    private final DebeziumKey key;
    private final DebeziumValue value;
    private final EventType eventType;

    public DebeziumObject(final String jsonKey, final String jsonValue) {
        this.key = new DebeziumKey(jsonKey);
        this.value = new DebeziumValue(jsonValue);
        this.eventType = defineEvent();
    }

    private EventType defineEvent() {
        if (value.getNewValue() == null) {
            return EventType.DELETE;
        }

        return value.getOperation();
    }

    public EventType getEventType() {
        return eventType;
    }

    public JsonObject getOldValue() {
        return this.value.getOldValue();
    }

    public JsonObject getNewValue() {
        return this.value.getNewValue();
    }

    public Long getIdentifier() {
        return this.key.getIdentifier();
    }
}
