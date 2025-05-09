package com.example.CurrencyRates.repositories;

import com.example.CurrencyRates.entities.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByCurrencyId(Long id);
    Optional<CurrencyRate> findByCurrencyCode(String code);
}
