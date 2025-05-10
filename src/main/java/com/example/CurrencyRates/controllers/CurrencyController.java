package com.example.CurrencyRates.controllers;

import com.example.CurrencyRates.dto.entities.CurrencyDTO;
import com.example.CurrencyRates.services.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @Operation(summary = "Запросить список всех валют",
            description = "В ответе возвращается список Currency.")
    @GetMapping
    public List<CurrencyDTO> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @Operation(summary = "Запросить валюту по её id",
            description = "В ответе возвращается Currency, если такая существует.")
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> getCurrencyById(@PathVariable Long id) {
        CurrencyDTO currencyDTO = currencyService.getCurrency(id);
        return currencyDTO == null ? ResponseEntity.notFound().build(): ResponseEntity.ok(currencyDTO);
    }

    @Operation(summary = "Запросить валюту по её коду",
            description = "В ответе возвращается Currency, если такая существует.")
    @GetMapping("/code")
    public ResponseEntity<CurrencyDTO> getCurrencyByCode(@RequestParam String code) {
        CurrencyDTO currencyDTO = currencyService.getCurrency(code);
        return currencyDTO == null ? ResponseEntity.notFound().build(): ResponseEntity.ok(currencyDTO);
    }

    @Operation(summary = "Добавить Currency",
            description = "В ответе возвращается добавленную Currency.")
    @PostMapping
    public ResponseEntity<CurrencyDTO> postCurrency(@RequestBody CurrencyDTO currencyDTO) {
        currencyDTO = currencyService.addCurrency(currencyDTO);
        return currencyDTO == null ? ResponseEntity.badRequest().build(): ResponseEntity.ok(currencyDTO);
    }

    @Operation(summary = "Обновить данные о Currency",
            description = "В ответе возвращается обновленную Currency.")
    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDTO> putCurrency(@PathVariable Long id, @RequestBody CurrencyDTO currencyDTO) {
        currencyDTO = currencyService.updateCurrency(id, currencyDTO);
        return currencyDTO == null ? ResponseEntity.badRequest().build(): ResponseEntity.ok(currencyDTO);
    }

    @Operation(summary = "Удалить Currency по её id",
            description = "В ответе возвращается ничего.")
    @DeleteMapping("/{id}")
    public void deleteCurrencyById(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
    }

    @Operation(summary = "Удалить Currency по её коду",
            description = "В ответе возвращается ничего.")
    @DeleteMapping
    public void deleteCurrencyByCode(@RequestParam String code) {
        currencyService.deleteCurrency(code);
    }
}
