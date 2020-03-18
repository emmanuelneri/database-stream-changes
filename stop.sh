#!/usr/bin/env bash

docker-compose stop
docker-compose rm -f
docker rm -f customer-stream
docker rm -f sale-stream
docker rm -f sale-consumer