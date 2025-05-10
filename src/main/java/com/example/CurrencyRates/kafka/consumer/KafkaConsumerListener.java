package com.example.CurrencyRates.kafka.consumer;

import com.example.CurrencyRates.dto.kafka.CurrencyCodesMessageDTO;
import com.example.CurrencyRates.kafka.producer.KafkaProducerSender;
import com.example.CurrencyRates.services.CurrencyRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerListener {

    @Autowired
    private CurrencyRateService currencyRateService;
    @Autowired
    private KafkaProducerSender kafkaSender;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "request-currency-rate", groupId = "group2")
    void listenerRequiredCurrencyRate(ConsumerRecord<String, String> record) {
        log.info("Received message [{}] in group1", record.value());

        try {
            CurrencyCodesMessageDTO codesMessage = objectMapper.readValue(record.value(), CurrencyCodesMessageDTO.class);
            kafkaSender.sendMessage(
                    currencyRateService.getCurrenciesScale(codesMessage).toString(),
                    "get-currency-rate",
                    record.key());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
