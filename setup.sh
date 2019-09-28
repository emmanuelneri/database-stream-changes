#!/usr/bin/env bash

kafkaURL=kafka:29092
kafkaConnectorURL=http://localhost:8083/connectors

docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db.public.customer
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db.public.sale

curl -v POST -H "Content-Type: application/json" -d @setup/connector/debezium-kafka-connector.json ${kafkaConnectorURL}