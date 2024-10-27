FROM openjdk:21
LABEL authors="Nick552"

WORKDIR /app
COPY ./target /app/target

ENTRYPOINT ["java", "-jar", "target/healthy-smile-0.0.1-SNAPSHOT.jar"]