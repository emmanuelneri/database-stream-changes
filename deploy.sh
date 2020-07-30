#!/usr/bin/env bash

kafkaURL=localhost:29092
kafkaConnectorURL=http://localhost:8083/connectors

##Debezium topics
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic debezium.history
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db.dbo.customer
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic db.dbo.sale

##Context topics
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic customer
docker exec -d kafka kafka-topics --create --bootstrap-server ${kafkaURL} --replication-factor 1 --partitions 1 --topic sale

docker exec -it db /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P '!Password1' -Q "CREATE DATABASE salesCdc;"
docker exec -it db /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P '!Password1' -i /tmp/init.sql
docker exec -it db /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P '!Password1' -i /tmp/active-cdc.sql

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