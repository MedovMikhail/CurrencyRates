package com.example.CurrencyRates.repositories;

import com.example.CurrencyRates.entities.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByCurrencyId(Long id);
    Optional<CurrencyRate> findByCurrencyCode(String code);
    @Query(value = "select b.exchangeRate / t.exchangeRate " +
            "from CurrencyRate as b, CurrencyRate as t " +
            "where b.currency.code = :base and t.currency.code = :target")
    BigDecimal getCurrencyRateScale(@Param(value="base") String base, @Param(value="target") String target);
}
