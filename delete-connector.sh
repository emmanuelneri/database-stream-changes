#!/usr/bin/env bash

kafkaConnectorURL=http://localhost:8083/connectors

curl -X DELETE ${kafkaConnectorURL}/debezium-connector
curl ${kafkaConnectorURL}