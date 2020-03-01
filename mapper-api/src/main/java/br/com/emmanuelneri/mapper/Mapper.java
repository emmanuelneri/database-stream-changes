package br.com.emmanuelneri.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public enum Mapper {
    INSTANCE;

    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readValue(final String json, final Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new InvalidFormatException(json, e);
        }
    }

    public <T> T readValue(final byte[] bytes, final Class<T> tClass) {
        try {
            return objectMapper.readValue(bytes, tClass);
        } catch (IOException e) {
            throw new InvalidFormatException(null, e);
        }
    }

    public <T> byte[] writeValueAsBytes(final T bytes) {
        try {
            return objectMapper.writeValueAsBytes(bytes);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
