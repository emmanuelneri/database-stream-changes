create table if not exists customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200)
);

create table if not exists sale (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    identifier VARCHAR(50) NOT NULL,
    product VARCHAR(100) NOT NULL,
    total NUMERIC(19, 2) NOT NULL,
    CONSTRAINT sale_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer(id)
);

INSERT INTO customer (name) VALUES ('Customer 1');
INSERT INTO customer (name) VALUES ('Customer 2');
INSERT INTO customer (name) VALUES ('Customer 3');
INSERT INTO customer (name) VALUES ('Customer 4');
INSERT INTO customer (name) VALUES ('Customer 5');
