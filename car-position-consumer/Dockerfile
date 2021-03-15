FROM openjdk:11.0-jre

LABEL maintainer="eshakeri@hotmail.com"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-position-consumer.jar
ENTRYPOINT ["java", "-jar", "car-position-consumer.jar"]


