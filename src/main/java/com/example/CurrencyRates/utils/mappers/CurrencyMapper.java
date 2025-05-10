package com.example.CurrencyRates.utils.mappers;

import com.example.CurrencyRates.dto.entities.CurrencyDTO;
import com.example.CurrencyRates.entities.Currency;
import com.example.CurrencyRates.entities.CurrencyRate;
import com.example.CurrencyRates.utils.mappers.interfaces.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CurrencyMapper implements Mapper<Currency, CurrencyDTO> {

    @Override
    public CurrencyDTO fromEntityToDTO(Currency from) {
        if (from == null) return null;
        CurrencyDTO to = new CurrencyDTO();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setCode(from.getCode());
        if (from.getCurrencyRate() != null) to.setCurrencyRateId(from.getCurrencyRate().getId());
        return to;
    }

    @Override
    public Currency fromDTOtoEntity(CurrencyDTO from) {
        if (from == null) return null;
        Currency to = new Currency();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setCode(from.getCode());
        if (from.getCurrencyRateId() != null) {
            to.setCurrencyRate(new CurrencyRate());
            to.getCurrencyRate().setId(from.getCurrencyRateId());
        }
        return to;
    }
}
