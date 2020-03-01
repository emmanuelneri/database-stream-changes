package br.com.emmanuelneri.customer.schema;

import br.com.emmanuelneri.mapper.Mapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class Customer {

    public static final Serializer<Customer> serializer = (topic, customer) -> Mapper.INSTANCE.writeValueAsBytes(customer);
    public static final Deserializer<Customer> deserializer = (topic, bytes) -> Mapper.INSTANCE.readValue(bytes, Customer.class);

    private Long id;
    private String name;

    public Customer() {}

    public Customer(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
