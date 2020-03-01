package br.com.emmanuelneri.debezium.api;

import br.com.emmanuelneri.mapper.Mapper;

import java.util.Map;
import java.util.Objects;

public class Struct {

    private Map<String, Object> map;

    public Struct(final String json) {
        this.map = Mapper.INSTANCE.readValue(json, Map.class);
    }

    public Struct(final Map map) {
        this.map = map;
    }

    public String getString(final String attribute) {
        final Object value = map.get(attribute);
        return Objects.nonNull(value) ? value.toString() : null;
    }

    public Long getLong(final String attribute) {
        final Object value = map.get(attribute);
        return Objects.nonNull(value) && value instanceof Number ? ((Number) value).longValue() : null;
    }

    public Struct getObject(final String attribute) {
        final Object value = map.get(attribute);
        return value instanceof Map ? new Struct((Map) value) : (Struct) value;
    }

    public byte[] getBytes(final String total) {
        final Object value = map.get(total);
        return Objects.nonNull(value) ? total.getBytes() : null;
    }
}
