package com.nimatullo.paymentservice.kafka;

import com.nimatullo.paymentservice.dto.PaymentResponse;
import com.nimatullo.paymentservice.dto.TransactionResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
        @Bean
        public ConsumerFactory<String, TransactionResponse> consumerFactory() {
            Map<String, Object> config = new HashMap<>();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
            config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_2");
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.nimatullo.*");
            return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(TransactionResponse.class, false));
        }

        @Bean
        public ProducerFactory<String, PaymentResponse> producerFactory() {
            Map<String, Object> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(config);
        }

        @Bean
        public KafkaTemplate<String, PaymentResponse> kafkaTemplate() {
            return new KafkaTemplate<String, PaymentResponse>(producerFactory());
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, TransactionResponse> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, TransactionResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }
}