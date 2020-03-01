package br.com.emmanuelneri.customer.stream;

import br.com.emmanuelneri.customer.schema.Customer;
import br.com.emmanuelneri.debezium.api.Struct;

public final class CustomerMapper {

    private CustomerMapper() {
    }

    public static Customer to(final Struct value) {
        return new Customer(
                value.getLong("id"),
                value.getString("name")
        );
    }
}
