package com.example.CurrencyRates.utils.mappers;

import com.example.CurrencyRates.dto.CurrencyRateDTO;
import com.example.CurrencyRates.entities.Currency;
import com.example.CurrencyRates.entities.CurrencyRate;
import com.example.CurrencyRates.utils.mappers.interfaces.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRateMapper implements Mapper<CurrencyRate, CurrencyRateDTO> {

    @Override
    public CurrencyRateDTO fromEntityToDTO(CurrencyRate from) {
        if (from == null) return null;
        CurrencyRateDTO to = new CurrencyRateDTO();
        to.setId(from.getId());
        to.setExchangeRate(from.getExchangeRate());
        to.setDate(from.getDate());
        to.setCurrencyId(from.getCurrency().getId());
        return to;
    }

    @Override
    public CurrencyRate fromDTOtoEntity(CurrencyRateDTO from) {
        if (from == null) return null;
        CurrencyRate to = new CurrencyRate();
        to.setId(from.getId());
        to.setExchangeRate(from.getExchangeRate());
        to.setDate(from.getDate());
        to.setCurrency(new Currency());
        to.getCurrency().setId(from.getCurrencyId());
        return to;
    }
}
