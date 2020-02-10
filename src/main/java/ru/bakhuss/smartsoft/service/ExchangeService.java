package ru.bakhuss.smartsoft.service;

import ru.bakhuss.smartsoft.model.Exchange;
import ru.bakhuss.smartsoft.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ExchangeService {
    void updateExchangeRate();

    Exchange exchange(Exchange exchange);

    List<Exchange> getExchangesByUserAndExchangeDate(User user, Date exchangeDate);

    List<Exchange> getExchangesByUserAndRateDate(User user, LocalDate rateDate);
}
