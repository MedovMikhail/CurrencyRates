package com.example.CurrencyRates.repositories;

import com.example.CurrencyRates.entities.Currency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
    @Transactional
    void deleteByCode(String code);
}
