package com.patient_medication_management.api.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.annotation.EnableKafka;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import org.springframework.kafka.core.ConsumerFactory;

import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {

        // Consumer configuration properties

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker address

        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "hospital_group"); // Consumer group ID

        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Key deserializer

        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Value deserializer

        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Start reading from earliest message

        return new DefaultKafkaConsumerFactory<>(configProps);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

        // Factory for Kafka listeners

        ConcurrentKafkaListenerContainerFactory<String, String> factory =

                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;

    }

}
