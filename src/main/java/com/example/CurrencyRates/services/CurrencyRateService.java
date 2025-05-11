package com.example.CurrencyRates.services;

import com.example.CurrencyRates.dto.CurrencyAndRate;
import com.example.CurrencyRates.dto.entities.CurrencyDTO;
import com.example.CurrencyRates.dto.entities.CurrencyRateDTO;
import com.example.CurrencyRates.dto.kafka.CurrencyCodesMessageDTO;
import com.example.CurrencyRates.entities.CurrencyRate;
import com.example.CurrencyRates.repositories.CurrencyRateRepository;
import com.example.CurrencyRates.utils.mappers.CurrencyRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CurrencyRateService {

    @Autowired
    private CurrencyRateMapper currencyRateMapper;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;
    @Autowired
    private CurrencyService currencyService;

    public List<CurrencyRateDTO> getCurrencyRates() {
        return currencyRateRepository.findAll()
                .stream()
                .map(currencyRateMapper::fromEntityToDTO)
                .toList();
    }

    public CurrencyRateDTO getCurrencyRate(Long id) {
        return currencyRateMapper.fromEntityToDTO(
                currencyRateRepository.findById(id).orElse(null)
        );
    }

    public CurrencyRateDTO getCurrencyRate(String code) {
        return currencyRateMapper.fromEntityToDTO(
                currencyRateRepository.findByCurrencyCode(code).orElse(null)
        );
    }

    public BigDecimal getCurrenciesScale(CurrencyCodesMessageDTO codesMessage) {
        BigDecimal scale = currencyRateRepository.getCurrencyRateScale(
                codesMessage.getBaseCurrencyCode().toUpperCase(),
                codesMessage.getTargetCurrencyCode().toUpperCase()
        );
        if (scale != null) scale = scale.setScale(8, RoundingMode.HALF_UP);
        return scale;
    }

    public HashMap<String, BigDecimal> getCurrencyRatesByCurrencyCodes(List<String> currencyCodes) {
        List<CurrencyAndRate> currencyRates = currencyRateRepository.findByCurrencyCodeIn(currencyCodes);
        HashMap<String, BigDecimal> currencyRatesMap = new HashMap<>();
        currencyRates
                .forEach(x -> {
                    currencyRatesMap.put(x.getCode(), x.getExchangeRate());
                });
        return currencyRatesMap;
    }

    public CurrencyRateDTO addCurrencyRate(Long currencyId, CurrencyRateDTO currencyRateDTO) {
        if (currencyRateRepository.findByCurrencyId(currencyId).isPresent()) return null;
        currencyRateDTO.setCurrencyId(currencyId);
        currencyRateDTO.setDate(new Date());
        CurrencyRate currencyRate = currencyRateMapper.fromDTOtoEntity(currencyRateDTO);
        try {
            currencyRateDTO = currencyRateMapper.fromEntityToDTO(
                    currencyRateRepository.save(currencyRate)
            );
        } catch (RuntimeException e) {
            return null;
        }
        return currencyRateDTO;
    }

    public CurrencyRateDTO addCurrencyRate(String code, CurrencyRateDTO currencyRateDTO) {
        if (currencyRateRepository.findByCurrencyCode(code).isPresent()) return null;

        CurrencyDTO currencyDTO = currencyService.getCurrency(code.toUpperCase());

        if (currencyDTO == null) return null;
        currencyRateDTO.setExchangeRate(
                currencyRateDTO.getExchangeRate().setScale(8, RoundingMode.HALF_UP)
        );
        currencyRateDTO.setCurrencyId(currencyDTO.getId());
        currencyRateDTO.setDate(new Date());
        CurrencyRate currencyRate = currencyRateMapper.fromDTOtoEntity(currencyRateDTO);
        try {
            currencyRateDTO = currencyRateMapper.fromEntityToDTO(
                    currencyRateRepository.save(currencyRate)
            );
        } catch (RuntimeException e) {
            return null;
        }
        return currencyRateDTO;
    }

    public CurrencyRateDTO updateCurrencyRate(Long id, BigDecimal exchangeRate) {
        CurrencyRate currencyRate = currencyRateRepository.findById(id).orElse(null);
        if (currencyRate == null) return null;

        currencyRate.setExchangeRate(
                exchangeRate.setScale(8, RoundingMode.HALF_UP)
        );
        currencyRate.setDate(new Date());
        try {
            currencyRate = currencyRateRepository.save(currencyRate);
        } catch (RuntimeException e) {
            return null;
        }
        return currencyRateMapper.fromEntityToDTO(currencyRate);
    }
    
    public void deleteCurrencyRate(Long id) {
        currencyRateRepository.deleteById(id);
    }
}
