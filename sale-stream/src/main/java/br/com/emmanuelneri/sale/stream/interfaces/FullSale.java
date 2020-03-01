package br.com.emmanuelneri.sale.stream.interfaces;

import io.vertx.core.json.Json;

import java.math.BigDecimal;

public class FullSale {

    private Long id;
    private String identifier;
    private Customer customer;
    private String product;
    private BigDecimal total;

    public FullSale(final String saleAsString, final String customerAsString) {
        final Sale sale = Json.decodeValue(saleAsString, Sale.class);
        this.id = sale.getId();
        this.identifier = sale.getIdentifier();
        this.product = sale.getProduct();
        this.total = sale.getTotalAsBigDecimal();
        this.customer = Json.decodeValue(customerAsString, Customer.class);
    }

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
