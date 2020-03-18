#!/usr/bin/env bash

mvn clean install -DskipTest
mvn -f customer-stream/pom.xml docker:build
mvn -f sale-stream/pom.xml docker:build
mvn -f sale-consumer/pom.xml docker:build