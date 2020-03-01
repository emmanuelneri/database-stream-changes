package br.com.emmanuelneri.debezium.api;

final class DebeziumValue extends DebeziumStruct {

    private static final String BEFORE_FIELD = "before";
    private static final String AFTER_FIELD = "after";
    private static final String OPERATION_FIELD = "op";

    private final Struct before;
    private final Struct after;
    private final EventType operation;

    DebeziumValue(final String jsonValue) {
        super(jsonValue);
        if (getStruct() == null) {
            this.before = null;
            this.after = null;
            this.operation = null;
        } else {
            this.before = getAtribute(getStruct(), BEFORE_FIELD);
            this.after = getAtribute(getStruct(), AFTER_FIELD);
            this.operation = EventType.fromOperation(getStruct().getString(OPERATION_FIELD));
        }
    }

    Struct getOldValue() {
        return this.before;
    }

    Struct getNewValue() {
        return this.after;
    }

    EventType getOperation() {
        return operation;
    }
}
