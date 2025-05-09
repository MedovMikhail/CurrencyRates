package com.example.CurrencyRates.controllers;

import com.example.CurrencyRates.dto.CurrencyRateDTO;
import com.example.CurrencyRates.services.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/currency-rates")
public class CurrencyRateController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping
    public List<CurrencyRateDTO> getCurrencyRates() {
        return currencyRateService.getCurrencyRates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRateDTO> getCurrencyRateById(@PathVariable  Long id) {
        CurrencyRateDTO currencyRateDTO = currencyRateService.getCurrencyRate(id);
        return currencyRateDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @PostMapping("/{currencyId}")
    public ResponseEntity<CurrencyRateDTO> postCurrencyRateByCurrencyId(
            @PathVariable Long currencyId, @RequestBody CurrencyRateDTO currencyRateDTO
    ) {
        currencyRateDTO = currencyRateService.addCurrencyRate(currencyId, currencyRateDTO);
        return currencyRateDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @PostMapping("/code")
    public ResponseEntity<CurrencyRateDTO> postCurrencyRateByCode(
            @RequestParam String code, @RequestBody CurrencyRateDTO currencyRateDTO
    ) {
        currencyRateDTO = currencyRateService.addCurrencyRate(code, currencyRateDTO);
        return currencyRateDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyRateDTO> putCurrencyRate(
            @PathVariable Long id, @RequestParam BigDecimal exchangeRate
    ) {
        CurrencyRateDTO currencyRateDTO = currencyRateService.updateCurrencyRate(id, exchangeRate);
        return currencyRateDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCurrencyRate(@PathVariable Long id) {
        currencyRateService.deleteCurrencyRate(id);
    }

}
