package br.com.emmanuelneri.sale.schema;

import br.com.emmanuelneri.customer.schema.Customer;
import br.com.emmanuelneri.mapper.Mapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.math.BigDecimal;

public class EnrichedSale {

    public static final Serializer<EnrichedSale> serializer = (topic, enrichedSale) -> Mapper.INSTANCE.writeValueAsBytes(enrichedSale);
    public static final Deserializer<EnrichedSale> deserializer = (topic, bytes) -> Mapper.INSTANCE.readValue(bytes, EnrichedSale.class);

    private Long id;
    private String identifier;
    private Customer customer;
    private String product;
    private BigDecimal total;

    public EnrichedSale(final Sale sale, final Customer customer) {
        this.id = sale.getId();
        this.identifier = sale.getIdentifier();
        this.product = sale.getProduct();
        this.total = sale.getTotal();
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
