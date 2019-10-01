package br.com.emmanuelneri.debezium.api;

import org.junit.Assert;
import org.junit.Test;

public class DebeziumObjectTest {

    @Test
    public void shouldBeInsertEvent() {
        final String key = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"}],\"optional\":false,\"name\":\"db.public.customer.Key\"},\"payload\":{\"id\":6}}";
        final String value = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":true,\"field\":\"name\"}],\"optional\":true,\"name\":\"db.public.customer.Value\",\"field\":\"before\"},{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":true,\"field\":\"name\"}],\"optional\":true,\"name\":\"db.public.customer.Value\",\"field\":\"after\"},{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":true,\"field\":\"version\"},{\"type\":\"string\",\"optional\":true,\"field\":\"connector\"},{\"type\":\"string\",\"optional\":false,\"field\":\"name\"},{\"type\":\"string\",\"optional\":false,\"field\":\"db\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"ts_usec\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"txId\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"lsn\"},{\"type\":\"string\",\"optional\":true,\"field\":\"schema\"},{\"type\":\"string\",\"optional\":true,\"field\":\"table\"},{\"type\":\"boolean\",\"optional\":true,\"default\":false,\"field\":\"snapshot\"},{\"type\":\"boolean\",\"optional\":true,\"field\":\"last_snapshot_record\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"xmin\"}],\"optional\":false,\"name\":\"io.debezium.connector.postgresql.Source\",\"field\":\"source\"},{\"type\":\"string\",\"optional\":false,\"field\":\"op\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"ts_ms\"}],\"optional\":false,\"name\":\"db.public.customer.Envelope\"},\"payload\":{\"before\":null,\"after\":{\"id\":6,\"name\":\"Insert Customer\"},\"source\":{\"version\":\"0.9.5.Final\",\"connector\":\"postgresql\",\"name\":\"db\",\"db\":\"cdc\",\"ts_usec\":1569894889318915,\"txId\":562,\"lsn\":23785688,\"schema\":\"public\",\"table\":\"customer\",\"snapshot\":false,\"last_snapshot_record\":null,\"xmin\":null},\"op\":\"c\",\"ts_ms\":1569894889323}}";

        final DebeziumObject debeziumObject = new DebeziumObject(key, value);
        Assert.assertEquals(EventType.INSERT, debeziumObject.getEventType());
    }

    @Test
    public void shouldBeUpdateEvent() {
        final String key = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"}],\"optional\":false,\"name\":\"db.public.customer.Key\"},\"payload\":{\"id\":1}}";
        final String value = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":true,\"field\":\"name\"}],\"optional\":true,\"name\":\"db.public.customer.Value\",\"field\":\"before\"},{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":true,\"field\":\"name\"}],\"optional\":true,\"name\":\"db.public.customer.Value\",\"field\":\"after\"},{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":true,\"field\":\"version\"},{\"type\":\"string\",\"optional\":true,\"field\":\"connector\"},{\"type\":\"string\",\"optional\":false,\"field\":\"name\"},{\"type\":\"string\",\"optional\":false,\"field\":\"db\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"ts_usec\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"txId\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"lsn\"},{\"type\":\"string\",\"optional\":true,\"field\":\"schema\"},{\"type\":\"string\",\"optional\":true,\"field\":\"table\"},{\"type\":\"boolean\",\"optional\":true,\"default\":false,\"field\":\"snapshot\"},{\"type\":\"boolean\",\"optional\":true,\"field\":\"last_snapshot_record\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"xmin\"}],\"optional\":false,\"name\":\"io.debezium.connector.postgresql.Source\",\"field\":\"source\"},{\"type\":\"string\",\"optional\":false,\"field\":\"op\"},{\"type\":\"int64\",\"optional\":true,\"field\":\"ts_ms\"}],\"optional\":false,\"name\":\"db.public.customer.Envelope\"},\"payload\":{\"before\":null,\"after\":{\"id\":1,\"name\":\"Customer 1\"},\"source\":{\"version\":\"0.9.5.Final\",\"connector\":\"postgresql\",\"name\":\"db\",\"db\":\"cdc\",\"ts_usec\":1569894888562681,\"txId\":560,\"lsn\":23784808,\"schema\":\"public\",\"table\":\"customer\",\"snapshot\":false,\"last_snapshot_record\":null,\"xmin\":null},\"op\":\"u\",\"ts_ms\":1569894888610}}";

        final DebeziumObject debeziumObject = new DebeziumObject(key, value);
        Assert.assertEquals(EventType.UPDATE, debeziumObject.getEventType());
    }

    @Test
    public void shouldBeDeleteEvent() {
        final String key = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"}],\"optional\":false,\"name\":\"db.public.customer.Key\"},\"payload\":{\"id\":4}}";
        final String value = null;

        final DebeziumObject debeziumObject = new DebeziumObject(key, value);
        Assert.assertEquals(EventType.DELETE, debeziumObject.getEventType());
    }
}