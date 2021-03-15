package com.sn.challenge.service;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.repository.VehicleRepository;
import com.sn.challenge.util.PolygonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author Ehsan Sh
 */

@Service
@Slf4j
public class VehicleEventService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PolygonHelper polygonHelper;

    public void processCarEvent(ConsumerRecord<String, Vehicle> consumerRecord) {
        Vehicle vehicle = consumerRecord.value();
        log.info("vehicleEvent : {} ", vehicle);

        vehicle.setPolygonId(polygonHelper.findVehiclePolygon(vehicle.getPosition()));
        vehicle.setLastModified(Instant.now());

        vehicleRepository.save(vehicle);

    }

}
