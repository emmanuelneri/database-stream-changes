#!/usr/bin/env bash

kafkaURL=http://localhost:8083/connectors

curl -v POST -H "Content-Type: application/json" -d @config/connector/debezium-kafka-connector.json $kafkaURL