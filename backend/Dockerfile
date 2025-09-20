FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/football-0.0.1-SNAPSHOT.jar app.jar

COPY src/main/resources/data /app/src/main/resources/data

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
