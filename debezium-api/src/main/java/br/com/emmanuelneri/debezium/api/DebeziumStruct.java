package br.com.emmanuelneri.debezium.api;


abstract class DebeziumStruct {

    private static final String PAYLOAD_FIELD = "payload";

    private final Struct struct;

    DebeziumStruct(final String jsonValue) {
        if (jsonValue == null) {
            this.struct = null;
        } else {
            final Struct value = new Struct(jsonValue);
            this.struct = this.getAtribute(value, PAYLOAD_FIELD);
        }
    }

    Struct getStruct() {
        return struct;
    }

    Struct getAtribute(final Struct struct, final String attribute) {
        return struct.getObject(attribute);
    }

}
