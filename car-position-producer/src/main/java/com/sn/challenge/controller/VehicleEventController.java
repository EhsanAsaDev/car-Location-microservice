package com.sn.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.challenge.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * @author Ehsan Sh
 */

@Component
@Slf4j
public class VehicleEventController {

    private final String topic = "vehicle-position-events";
    private final KafkaTemplate<String,Vehicle> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public VehicleEventController(KafkaTemplate<String, Vehicle> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public ListenableFuture<SendResult<String,Vehicle>> sentVehicleEvents(Vehicle vehicle) {

        String key = vehicle.getVin();
        //String value = objectMapper.writeValueAsString(VehicleEvent);

        ProducerRecord<String,Vehicle> producerRecord = buildProducerRecord(key, vehicle, topic);

        ListenableFuture<SendResult<String,Vehicle>> listenableFuture =  kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new VehicleEventListenableFutureCallback(key,vehicle));

        return listenableFuture;
    }

    private ProducerRecord<String, Vehicle> buildProducerRecord(String key, Vehicle value, String topic) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "Rest Call".getBytes()));

        return new ProducerRecord<String, Vehicle>(topic, null, key, value, recordHeaders);
    }
}   
