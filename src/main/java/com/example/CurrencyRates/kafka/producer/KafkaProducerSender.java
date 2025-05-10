package com.example.CurrencyRates.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topicName) {
        logMessage(message);
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessage(String message, String topicName, String key) {
        logMessage(message);
        kafkaTemplate.send(topicName, key, message);
    }

    private void logMessage(String message) {
        log.info("Sending : {}", message);
        log.info("--------------------------------");
    }
}
