package com.sn.challenge.controller;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.service.VehicleEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Ehsan Sh
 */

@Component
@Slf4j
public class CarPositionEventsConsumer {


    @Autowired
    VehicleEventService vehicleEventService;

    @KafkaListener(topics = {"vehicle-position-events"})
    public void onMessage(ConsumerRecord<String, Vehicle> consumerRecord) {
        log.info("ConsumerRecord : {} ", consumerRecord );
        vehicleEventService.processCarEvent(consumerRecord);

    }
}