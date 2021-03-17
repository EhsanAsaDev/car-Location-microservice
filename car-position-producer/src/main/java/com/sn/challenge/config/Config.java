package com.sn.challenge.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ehsan Sh
 */


@Configuration
public class Config {

    //Todo not recommend creat here
    @Bean
    public NewTopic vehicleEvents(){
        return TopicBuilder.name("vehicle-position-events")
                .partitions(3)
                .replicas(3)
                .build();
    }


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}


