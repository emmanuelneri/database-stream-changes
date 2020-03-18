FROM openjdk:8-jre-alpine
RUN apk update && apk add --no-cache libc6-compat && \
    ln -s /lib/libc.musl-x86_64.so.1 /lib/ld-linux-x86-64.so.2
COPY target/customer-stream-*-jar-with-dependencies.jar app.jar
ENV KAFKA_BOOTSTRAP_SERVERS "localhost:9092"
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
