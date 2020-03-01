package br.com.emmanuelneri.sale.stream;

import br.com.emmanuelneri.debezium.api.Struct;
import br.com.emmanuelneri.sale.schema.Sale;

public final class SaleMapper {

    private SaleMapper() {
    }

    public static Sale to(final Struct vale) {
        return new Sale(
                vale.getLong("id"),
                vale.getString("identifier"),
                vale.getLong("customer_id"),
                vale.getString("product"),
                vale.getBytes("total")
        );
    }
}
