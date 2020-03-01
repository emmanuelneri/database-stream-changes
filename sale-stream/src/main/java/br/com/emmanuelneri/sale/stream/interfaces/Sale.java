package br.com.emmanuelneri.sale.stream.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Sale {

    private static final int DECIMAL_SCALE = 2;

    private Long id;
    private String identifier;

    @JsonProperty("customer_id")
    private Long customerId;

    private String product;
    private byte[] total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public byte[] getTotal() {
        return total;
    }

    public BigDecimal getTotalAsBigDecimal() {
        return new BigDecimal(new BigInteger(this.total), DECIMAL_SCALE);
    }

    public void setTotal(byte[] total) {
        this.total = total;
    }
}
