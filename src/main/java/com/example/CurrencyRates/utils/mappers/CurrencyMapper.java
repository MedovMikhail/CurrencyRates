package com.example.CurrencyRates.utils.mappers;

import com.example.CurrencyRates.dto.CurrencyDTO;
import com.example.CurrencyRates.entities.Currency;
import com.example.CurrencyRates.utils.mappers.interfaces.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CurrencyMapper implements Mapper<Currency, CurrencyDTO> {

    @Override
    public CurrencyDTO fromEntityToDTO(Currency from) {
        CurrencyDTO to = new CurrencyDTO();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setCode(from.getCode());
        return to;
    }

    @Override
    public Currency fromDTOtoEntity(CurrencyDTO from) {
        Currency to = new Currency();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setCode(from.getCode());
        return to;
    }
}
