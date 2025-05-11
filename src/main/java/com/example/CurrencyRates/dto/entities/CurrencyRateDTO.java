package com.example.CurrencyRates.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDTO {

    private Long id;
    private BigDecimal exchangeRate;
    private Date date;
    private Long currencyId;
}
