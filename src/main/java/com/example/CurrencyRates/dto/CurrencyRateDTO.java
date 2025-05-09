package com.example.CurrencyRates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDTO {

    private long id;
    private BigDecimal exchangeRate;
    private Date date;
    private long currencyId;
}
