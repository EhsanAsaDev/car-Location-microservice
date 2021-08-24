package com.sn.challenge.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.challenge.model.Position;
import com.sn.challenge.model.Vehicle;
import com.sn.challenge.model.polygon.MyPolygon;
import com.sn.challenge.repository.VehicleRepository;
import com.sn.challenge.service.VehicleEventListenableFutureCallback;
import com.sn.challenge.service.VehicleEventService;
import com.sn.challenge.service.VehiclesService;
import com.sn.challenge.util.PolygonHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * @author Ehsan Sh
 */

@SpringBootTest
@EmbeddedKafka(topics = {"vehicle-position-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}"
        , "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
@AutoConfigureDataMongo
public class VehicleEventsConsumerIntegrationTest {
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaTemplate<String, Vehicle> kafkaTemplate;

    @Autowired
    KafkaListenerEndpointRegistry endpointRegistry;

    @SpyBean
    VehicleEventsConsumer vehicleEventsConsumerSpy;

    @SpyBean
    VehicleEventService vehicleEventServiceSpy;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private PolygonHelper polygonHelper;


    @BeforeEach
    void setUp() {

        for (MessageListenerContainer messageListenerContainer : endpointRegistry.getListenerContainers()){
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }

    @AfterEach
    void tearDown() {
        vehicleRepository.deleteAll();
    }


    @Test
    void publishVehicleEvent() throws ExecutionException, InterruptedException, JsonProcessingException {
        //given
        Vehicle vehicle = Vehicle.builder()
                .id(1l)
                .vin("1FT2F30528U1H0CA4")
                .numberPlate("HQ8916")
                .position(new Position(49.12,9.12))
                .fuel(0.7)
                .model("SMART_42_PASSION")
                .build();

        ProducerRecord<String,Vehicle> producerRecord =
                buildProducerRecord(vehicle.getVin(),
                vehicle,
                "vehicle-position-events");

        ListenableFuture<SendResult<String,Vehicle>> listenableFuture =  kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new VehicleEventListenableFutureCallback(vehicle.getVin(),vehicle));


        //when
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);

        //then
        verify(vehicleEventsConsumerSpy, times(1)).onMessage(isA(ConsumerRecord.class));
        verify(vehicleEventServiceSpy, times(1)).processVehicleEvent(isA(ConsumerRecord.class));

        Optional<Vehicle> optionalVehicle = vehicleRepository.findById("1FT2F30528U1H0CA4");
        assertTrue(optionalVehicle.isPresent());

    }


    private ProducerRecord<String, Vehicle> buildProducerRecord(String key, Vehicle value, String topic) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "Rest Call".getBytes()));

        return new ProducerRecord<String, Vehicle>(topic, null, key, value, recordHeaders);
    }


    //@Test
    //Todo testFailed!
    public void metaDataAndLibraryTest() throws Exception {
        GeometryFactory gf = new GeometryFactory();

        List<MyPolygon> myPolygonList = polygonHelper.getPolygonList();
        Vehicle[] vehicleArray = vehiclesService.getVehicleInfo();
        boolean testFlag=false;

        for (Vehicle vehicle : vehicleArray) {
            boolean flag = false;
            for (MyPolygon myPolygon : myPolygonList) {

                Coordinate[] coordinateArr = myPolygon.getGeometry()
                        .getCoordinates().get(0)
                        .stream()
                        .map(e -> new Coordinate(e.get(1), e.get(0)))
                        //.skip(1)
                        .toArray(Coordinate[]::new);


                LinearRing jtsRing = gf.createLinearRing(coordinateArr);
                Polygon poly = gf.createPolygon(jtsRing, null);
                //Polygon poly = gf.createPolygon(coordinateArr);

                Coordinate coordinate = new Coordinate(
                        vehicle.getPosition().getLatitude(),
                        vehicle.getPosition().getLongitude());

                Point pt = gf.createPoint(coordinate);
                if (poly.contains(pt)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println("*** vehicle  id: " + vehicle.getId() + " vehicle  vin: " + vehicle.getVin() + "  not in any polygon: "
                        + vehicle.getPosition().getLatitude() + " , " + vehicle.getPosition().getLongitude());
                testFlag =true;
            }

        }
        Assertions.assertFalse(testFlag);

    }


}
  
