package com.sn.challenge;

import com.sn.challenge.model.Vehicle;
import com.sn.challenge.scheduler.FetchVehiclesPosition;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"vehicle-position-events"}, partitions = 1)
@TestPropertySource(properties = { "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
class CarPositionProducerApplicationTests {

    @Autowired
    FetchVehiclesPosition fetchVehiclesPosition;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, Vehicle> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        JsonDeserializer valueDeserializer = new JsonDeserializer<>();
        valueDeserializer.addTrustedPackages("com.sn.challenge.model");

        consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), valueDeserializer).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    //@Test
    //@Timeout(15)
    //Todo
    void postVehicleEvent() throws InterruptedException {

        //when
        Vehicle[] vehicles = fetchVehiclesPosition.fetchCarData();
        Thread.sleep(10000);

        //then
        ConsumerRecords<String, Vehicle> consumerRecords  = KafkaTestUtils.getRecords(consumer);

        for (ConsumerRecord<String, Vehicle> r : consumerRecords){
            Optional<Vehicle> optionalVehicle = Arrays.stream(vehicles).filter(v -> v.getVin().equals(r.value().getVin())).findFirst();
            if (optionalVehicle.isPresent()) {
                return;
            }
        }
        assertTrue(false);

    }


}
