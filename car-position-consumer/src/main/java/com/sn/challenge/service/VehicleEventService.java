package com.sn.challenge.service;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.repository.VehicleRepository;
import com.sn.challenge.util.PolygonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

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

    @Autowired
    KafkaTemplate<String, Vehicle> kafkaTemplate;

    public void processVehicleEvent(ConsumerRecord<String, Vehicle> consumerRecord) {
        Vehicle vehicle = consumerRecord.value();
        log.info("vehicleEvent : {} ", vehicle);

        vehicle.setPolygonId(polygonHelper.findVehiclePolygon(vehicle.getPosition()));
        vehicle.setLastModified(Instant.now());

        vehicleRepository.save(vehicle);

    }


    public void handleRecovery(ConsumerRecord<String,Vehicle> record){
        log.error("handleRecovery for {}", record);

        String key = record.key();
        Vehicle message = record.value();
        //String message = record.value().replace(":0",":-1");

        ListenableFuture<SendResult<String,Vehicle>> listenableFuture = kafkaTemplate.sendDefault(key, message);
        listenableFuture.addCallback(new VehicleEventListenableFutureCallback(key,message));


    }

}
