package br.com.emmanuelneri.debezium.api;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EventType {
    INSERT("c"),
    UPDATE("u"),
    DELETE(null);

    private static final Map<String, EventType> MAP = create();
    private final String operation;

    EventType(String operation) {
        this.operation = operation;
    }

    public static EventType fromOperation(final String operation) {
        return MAP.get(operation);
    }

    private static Map<String, EventType> create() {
        return Stream.of(EventType.values())
                .collect(Collectors.toMap(eventType -> eventType.operation, Function.identity()));
    }
}
