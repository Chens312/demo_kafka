package com.synpulse.steven.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.common.serialization.Serdes.String;
import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value(value = "localhost:9092")
    private String bootstrapAddress;

    @Bean
    public KafkaStreamsConfiguration kStreamConfig() {
        Map<String,Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "demo");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, String().getClass().getName());
        return new KafkaStreamsConfiguration(props);
    }
}
