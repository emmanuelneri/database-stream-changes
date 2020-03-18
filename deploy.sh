#!/usr/bin/env bash

kafkaURL=localhost:29092
kafkaConnectorURL=http://localhost:8083/connectors

#Debezium topics
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db.public.customer
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db.public.sale

#Cotext topics
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic customer
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic sale

curl -v POST -H "Content-Type: application/json" -d @setup/connector/debezium-kafka-connector.json ${kafkaConnectorURL}

docker run -d \
    --name customer-stream \
    --network=database-stream-changes \
    --link kafka \
    database-stream-changes/customer-stream

docker run -d \
    --name sale-stream \
    --network=database-stream-changes \
    --link kafka \
    database-stream-changes/sale-stream

docker run -d \
    --name sale-consumer \
    --network=database-stream-changes \
    --link kafka \
    database-stream-changes/sale-consumer