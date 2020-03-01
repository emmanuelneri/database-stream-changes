package br.com.emmanuelneri.mapper;

public class SerializationException extends RuntimeException {

    public SerializationException(final Throwable cause) {
        super("serializing error", cause);
    }
}
