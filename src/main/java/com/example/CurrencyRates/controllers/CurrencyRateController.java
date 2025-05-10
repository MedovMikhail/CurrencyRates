package com.example.CurrencyRates.controllers;

import com.example.CurrencyRates.dto.CurrencyRateDTO;
import com.example.CurrencyRates.services.CurrencyRateService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Запросить список всех курсов валют",
            description = "В ответе возвращается список CurrencyRate.")
    @GetMapping
    public List<CurrencyRateDTO> getCurrencyRates() {
        return currencyRateService.getCurrencyRates();
    }

    @Operation(summary = "Запросить курс валюты по id",
            description = "В ответе возвращается CurrencyRate.")
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRateDTO> getCurrencyRateById(@PathVariable Long id) {
        CurrencyRateDTO currencyRateDTO = currencyRateService.getCurrencyRate(id);
        return currencyRateDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @Operation(summary = "Запросить курс валюты по коду",
            description = "В ответе возвращается CurrencyRate.")
    @GetMapping("/code")
    public ResponseEntity<CurrencyRateDTO> getCurrencyRateByCode(@RequestParam String code) {
        CurrencyRateDTO currencyRateDTO = currencyRateService.getCurrencyRate(code);
        return currencyRateDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @Operation(summary = "Добавить курс валюты по id волюты",
            description = "В ответе возвращается CurrencyRate.")
    @PostMapping("/{currencyId}")
    public ResponseEntity<CurrencyRateDTO> postCurrencyRateByCurrencyId(
            @PathVariable Long currencyId, @RequestBody CurrencyRateDTO currencyRateDTO
    ) {
        currencyRateDTO = currencyRateService.addCurrencyRate(currencyId, currencyRateDTO);
        return currencyRateDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @Operation(summary = "Добавить курс валюты по коду валюты",
            description = "В ответе возвращается CurrencyRate.")
    @PostMapping("/code")
    public ResponseEntity<CurrencyRateDTO> postCurrencyRateByCode(
            @RequestParam String code, @RequestBody CurrencyRateDTO currencyRateDTO
    ) {
        currencyRateDTO = currencyRateService.addCurrencyRate(code, currencyRateDTO);
        return currencyRateDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @Operation(summary = "Обновить курс валюты по id валюты",
            description = "В ответе возвращается CurrencyRate.")
    @PutMapping("/{id}")
    public ResponseEntity<CurrencyRateDTO> putCurrencyRate(
            @PathVariable Long id, @RequestParam BigDecimal exchangeRate
    ) {
        CurrencyRateDTO currencyRateDTO = currencyRateService.updateCurrencyRate(id, exchangeRate);
        return currencyRateDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(currencyRateDTO);
    }

    @Operation(summary = "Удалить курс валюты по id валюты",
            description = "В ответе возвращается CurrencyRate.")
    @DeleteMapping("/{id}")
    public void deleteCurrencyRate(@PathVariable Long id) {
        currencyRateService.deleteCurrencyRate(id);
    }

}
