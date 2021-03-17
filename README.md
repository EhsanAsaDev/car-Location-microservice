Share Now Challenge
---
This is my technical assignment!

### What I did
First of all, as you mention in the task description I tried to design a solution for real production.
I preferred using microservice architecture. with this approach, we can scale this project just by adding new resources.

I designed and developed three microservices:

* car-position-producer: this microservice has a scheduler for getting car data from an external service and pushing that as an event to the topic of Apache Kafka.

* car-position-consumer: this microservice has a Kafka listener which consumes events, determined for each car in which
  polygon and finally save car with polygon Id in MongoDB.

* car-webservices: this microservice expose to rest webservice 1)getVehicleByVin 2)getVehiclesByPolygonId

with this approach, you can call car data service even each second, produce events for Kafa, run multiple consumers
how much you need and finally getting the car in which polygon with high accuracy.



### How to launch

First, you should build and package jar files with maven.
Then,  I provided a docker file for each microservice, so you should create an image for each microservice.
Finally, by the docker-compose file, you can launch it.
Via the home page (http://localhost:8083/) you can access with swagger to test APIs.
