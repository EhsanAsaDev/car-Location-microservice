
![Architecture Model](/car_position_diagram.png)

Car Location Implementation based on Microservices Architecture
---

The complete walk-through tutorial about this implementation, I've published blog post here:

[Car Location Implementation based on Microservices Architecture](https://ehsanasadev.github.io/How_to_use_Spring_and_Kafka_to_build_a_project_based_on_microservices_architecture/)

The goal is to calculate the position of vehicles inside strategic geojson-polygons and serve the cars and polygons via a REST API
mostly for demonstration purposes!

The implementation consists of Three microservices implemented in Java using Spring Boot and Spring Cloud:

-   `car-position-producer`: this microservice has a scheduler for getting car data from an external service and pushing that as an event to the topic of Apache Kafka.

-   `car-position-consumer`: this microservice has a Kafka listener which consumes events, determined for each car in which
    polygon and finally save car with polygon id in MongoDB.

-   `car-webservices`: this microservice expose two rest webservice 1)getVehicleByVin 2)getVehiclesByPolygonId

with this approach, you can scale this project just by adding new resources. you can call car data service even each second, produce events for Kafka, run multiple consumers
how much you need and finally getting the car in which polygon with high accuracy.

Technologies
------------
- `Spring Boot`
- `Spring Cloud`, used for building Microservices applications in Java
- `Spring MVC`, for creating RESTful API
- `Apache Kafka`, an open-source distributed event streaming platform
- `MongoDB`, the most popular NoSQL database for modern apps 
- `Docker`, for containerization of services
- `Docker-Compose`, to link the containers
- `Swagger`, Swagger-UI, for API documentation


### How to launch

First, you should build and package jar files with Maven.

//mvn clean package

Then,  I provided a docker file for each microservice, so you should create an image for each microservice.

//docker build -t car-position-producer .

//docker build -t car-position-consumer .

//docker build -t car-webservices .



Finally, by the docker-compose file, you can launch it.

//docker-compose up


Via the home page (http://localhost:8083/) you can access with swagger to test APIs.
