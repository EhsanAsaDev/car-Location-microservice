package com.sn.challenge.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.challenge.model.Position;
import com.sn.challenge.model.Vehicle;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Ehsan Sh
 */

@ExtendWith(MockitoExtension.class)
public class VehicleEventProducerUnitTest {
    @Mock
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    VehicleEventProducer vehicleEventProducer;

    @Test
    void sendVehicleEvent_failure() {
        //given
        Vehicle vehicle = Vehicle.builder()
                .id(1)
                .vin("1FT2F30528U1H0CA4")
                .numberPlate("HQ8916")
                .position(new Position(49.12,9.12))
                .fuel(0.7)
                .model("SMART_42_PASSION")
                .build();


        SettableListenableFuture future = new SettableListenableFuture();

        future.setException(new RuntimeException("Exception Calling Kafka"));
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);
        //when

        assertThrows(Exception.class, ()-> vehicleEventProducer.sendVehicleEvents(vehicle).get());

    }

    @Test
    void sendVehicleEvent_success() throws JsonProcessingException, ExecutionException, InterruptedException {
        //given
        Vehicle vehicle = Vehicle.builder()
                .id(1)
                .vin("1FT2F30528U1H0CA4")
                .numberPlate("HQ8916")
                .position(new Position(49.12,9.12))
                .fuel(0.7)
                .model("SMART_42_PASSION")
                .build();

        String record = objectMapper.writeValueAsString(vehicle);
        SettableListenableFuture future = new SettableListenableFuture();

        ProducerRecord<Integer, String> producerRecord = new ProducerRecord("Vehicle-events", vehicle.getVin(),record );
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("Vehicle-events", 1),
                1,1,342,System.currentTimeMillis(), 1, 2);
        SendResult<Integer, String> sendResult = new SendResult<Integer, String>(producerRecord,recordMetadata);

        future.set(sendResult);
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);
        //when

        ListenableFuture<SendResult<String,Vehicle>> listenableFuture =  vehicleEventProducer.sendVehicleEvents(vehicle);

        //then
        SendResult<String,Vehicle> sendResult1 = listenableFuture.get();
        assert sendResult1.getRecordMetadata().partition()==1;

    }

}   
