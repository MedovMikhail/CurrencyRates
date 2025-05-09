package com.example.CurrencyRates.utils.mappers.interfaces;

public interface Mapper <K, T>{
    T fromEntityToDTO(K from);
    K fromDTOtoEntity(T from);
}
