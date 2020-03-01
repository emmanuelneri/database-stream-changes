package br.com.emmanuelneri.debezium.api;

final class DebeziumKey extends DebeziumStruct {

    private static final String IDENTIFIER_FIELD = "id";

    private final String identifier;

    DebeziumKey(final String jsonValue) {
        super(jsonValue);
        this.identifier = getStruct().getString(IDENTIFIER_FIELD);
    }

    String getIdentifier() {
        return identifier;
    }
}
