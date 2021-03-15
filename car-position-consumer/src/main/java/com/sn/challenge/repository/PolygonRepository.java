package com.sn.challenge.repository;

import com.sn.challenge.model.polygon.MyPolygon;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ehsan Sh
 */

public interface PolygonRepository extends MongoRepository<MyPolygon,String> {
}
