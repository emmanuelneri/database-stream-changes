FROM openjdk:8-jre-alpine
COPY target/sale-consumer-*.jar app.jar
ENV KAFKA_BOOTSTRAP_SERVERS "kafka:29092"
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
