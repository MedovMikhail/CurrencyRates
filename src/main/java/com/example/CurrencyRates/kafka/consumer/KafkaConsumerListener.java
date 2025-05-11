package com.example.CurrencyRates.kafka.consumer;

import com.example.CurrencyRates.dto.entities.CurrencyRateDTO;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class KafkaConsumerListener {

    @Autowired
    private CurrencyRateService currencyRateService;
    @Autowired
    private KafkaProducerSender kafkaSender;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "request-currency-scale", groupId = "group1")
    void listenerRequiredCurrencyScale(ConsumerRecord<String, String> record) {
        log.info("Received message [{}] in group1", record.value());

        try {
            CurrencyCodesMessageDTO codesMessage = objectMapper.readValue(record.value(), CurrencyCodesMessageDTO.class);
            kafkaSender.sendMessage(
                    currencyRateService.getCurrenciesScale(codesMessage).toString(),
                    "get-currency-scale",
                    record.key()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "request-currency-rate", groupId = "group1")
    void listenerRequiredCurrencyRate(ConsumerRecord<String, String> record) {
        log.info("Received message [{}] in group1", record.value());

        String currencyCode = record.value().replaceAll("\"", "");
        CurrencyRateDTO currencyRateDTO = currencyRateService.getCurrencyRate(currencyCode);
        if (currencyRateDTO == null) return;
        kafkaSender.sendMessage(
                currencyRateDTO.getExchangeRate().toString(),
                "get-currency-rate",
                record.key()
        );
    }

    @KafkaListener(topics = "currency-rates", groupId = "group1")
    void listenerCurrencyRates(ConsumerRecord<String, String> record) {
        log.info("Received message [{}] in group1", record.value());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String> currencyCodes = objectMapper.readValue(record.value(), ArrayList.class);
            HashMap<String, BigDecimal> currencyRates = currencyRateService.getCurrencyRatesByCurrencyCodes(currencyCodes);
            if (currencyRates == null) return;
            kafkaSender.sendMessage(
                    objectMapper.writeValueAsString(currencyRates),
                    "get-currency-rates",
                    record.key()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
