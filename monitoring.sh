#!/usr/bin/env bash

docker run -it \
    --name control-center \
    --network=database-stream-changes \
    --link kafka \
    --link zookeeper \
    --link kafka-connect \
    -p "9021:9021" \
    -e CONTROL_CENTER_BOOTSTRAP_SERVERS=kafka:29092 \
    -e CONTROL_CENTER_ZOOKEEPER_CONNECT=zookeeper:2181 \
    -e CONTROL_CENTER_CONNECT_CLUSTER=kafka-connect:8083 \
    -e CONTROL_CENTER_REPLICATION_FACTOR=1 \
    -e CONTROL_CENTER_INTERNAL_TOPICS_PARTITIONS=1 \
    -e CONTROL_CENTER_MONITORING_INTERCEPTOR_TOPIC_PARTITIONS=1 \
    confluentinc/cp-enterprise-control-center:5.5.1