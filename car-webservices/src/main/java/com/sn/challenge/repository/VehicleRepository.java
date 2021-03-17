package com.sn.challenge.repository;

import com.sn.challenge.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends MongoRepository<Vehicle,String> {
    List<Vehicle> findByPolygonId(String polygonId);
}
