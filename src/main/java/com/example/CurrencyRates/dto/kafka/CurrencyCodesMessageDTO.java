package com.example.CurrencyRates.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyCodesMessageDTO {

    private String baseCurrencyCode;
    private String targetCurrencyCode;
}
