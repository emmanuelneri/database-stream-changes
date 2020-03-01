package br.com.emmanuelneri.sale.schema;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Sale {

    private static final int DECIMAL_SCALE = 2;

    private Long id;
    private String identifier;

    private Long customerId;

    private String product;
    private BigDecimal total;

    public Sale(final Long id, final String identifier, final Long customerId, final String product, final byte[] total) {
        this.id = id;
        this.identifier = identifier;
        this.customerId = customerId;
        this.product = product;
        this.total = new BigDecimal(new BigInteger(total), DECIMAL_SCALE);
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
