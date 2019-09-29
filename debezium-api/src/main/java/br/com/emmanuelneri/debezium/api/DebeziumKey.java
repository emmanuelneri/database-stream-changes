package br.com.emmanuelneri.debezium.api;

final class DebeziumKey extends DebeziumStruct {

    private static final String IDENTIFIER_FIELD = "id";

    private final Long identifier;

    DebeziumKey(final String jsonValue) {
        super(jsonValue);
        this.identifier = getPayload().getLong(IDENTIFIER_FIELD);
    }

    Long getIdentifier() {
        return identifier;
    }
}
