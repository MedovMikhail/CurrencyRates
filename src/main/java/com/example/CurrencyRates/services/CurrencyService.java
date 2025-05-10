package com.example.CurrencyRates.services;

import com.example.CurrencyRates.dto.entities.CurrencyDTO;
import com.example.CurrencyRates.entities.Currency;
import com.example.CurrencyRates.repositories.CurrencyRepository;
import com.example.CurrencyRates.utils.mappers.CurrencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyMapper currencyMapper;
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<CurrencyDTO> getCurrencies() {
        return currencyRepository.findAll()
                .stream()
                .map(currencyMapper::fromEntityToDTO)
                .toList();
    }

    public CurrencyDTO getCurrency(Long id) {
        return currencyMapper.fromEntityToDTO(
                currencyRepository.findById(id).orElse(null)
        );
    }

    public CurrencyDTO getCurrency(String code) {
        return currencyMapper.fromEntityToDTO(
                currencyRepository.findByCode(code.toUpperCase()).orElse(null)
        );
    }

    public CurrencyDTO addCurrency(CurrencyDTO currencyDTO) {
        currencyDTO.setCode(currencyDTO.getCode().toUpperCase());
        Currency currency = currencyMapper.fromDTOtoEntity(currencyDTO);
        try {
            currencyDTO = currencyMapper.fromEntityToDTO(
                    currencyRepository.save(currency)
            );
        } catch (DataIntegrityViolationException e) {
            return null;
        }
        return currencyDTO;
    }

    public CurrencyDTO updateCurrency(Long id, CurrencyDTO currencyDTO) {
        if (!currencyRepository.existsById(id)) return null;
        currencyDTO.setId(id);
        currencyDTO.setCode(currencyDTO.getCode().toUpperCase());
        Currency currency = currencyMapper.fromDTOtoEntity(currencyDTO);
        try {
            currencyDTO = currencyMapper.fromEntityToDTO(
                    currencyRepository.save(currency)
            );
        } catch (DataIntegrityViolationException e) {
            return null;
        }
        return currencyDTO;
    }

    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }
    public void deleteCurrency(String code) {
        currencyRepository.deleteByCode(code.toUpperCase());
    }
}
