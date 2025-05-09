package com.example.CurrencyRates.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    @Id
    @SequenceGenerator(name = "currency_rate_generator", sequenceName = "currency_rate_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_rate_generator")
    private long id;

    @Column(nullable = false)
    private BigDecimal exchangeRate;
    @Column(nullable = false)
    private Date date;

    @ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
    private Currency currency;
}
