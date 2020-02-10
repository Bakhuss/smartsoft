package ru.bakhuss.smartsoft.service;

import ru.bakhuss.smartsoft.model.ExchangeRate;

import java.util.List;

public interface ExchangeRateService {
    void addExchangeRate(ExchangeRate exchangeRate);

    ExchangeRate getExchangeRate(Long id);

    List<ExchangeRate> getAllExchangeRates();

    ExchangeRate getExchangeRate(ExchangeRate exchangeRate);
}
