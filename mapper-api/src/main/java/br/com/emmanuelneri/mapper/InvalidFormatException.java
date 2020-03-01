package br.com.emmanuelneri.mapper;

public class InvalidFormatException extends RuntimeException {

    public InvalidFormatException(final String json, final Throwable cause) {
        super(String.format("invalid format: %s", json), cause);
    }
}
