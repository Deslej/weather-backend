FROM openjdk:21-jdk-slim

COPY target/weather-backend-0.0.1-SNAPSHOT.jar weather-backend-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "weather-backend-0.0.1-SNAPSHOT.jar"]