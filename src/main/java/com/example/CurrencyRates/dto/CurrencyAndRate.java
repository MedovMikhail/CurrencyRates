package com.example.CurrencyRates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyAndRate {
    private String code;
    private BigDecimal exchangeRate;
}
