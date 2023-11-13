package com.tech3camp.cdcreader.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ObjectMapper demoProjectMapper() {
        return new ObjectMapper();
    }


}
