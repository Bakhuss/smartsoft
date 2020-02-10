package ru.bakhuss.smartsoft.service;

import ru.bakhuss.smartsoft.model.Currency;

import java.util.List;

public interface CurrencyService {
    void addCurrency(Currency currency);

    Currency getCurrency(Long id);

    Currency getCurrencyByNumCode(Integer numCode);

    void updateCurrency(Currency currency);

    void deleteCurrency(Long id);

    List<Currency> getAllCurrencies();
}