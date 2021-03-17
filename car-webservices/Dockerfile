FROM openjdk:11.0-jre

LABEL maintainer="eshakeri@hotmail.com"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-webServices.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://docker-mongodb:27017/car","-Djava.security.egd=file:/dev/./urandom", "-jar", "car-webServices.jar"]


