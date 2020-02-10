package ru.bakhuss.smartsoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bakhuss.smartsoft.model.Currency;
import ru.bakhuss.smartsoft.model.ExchangeRate;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateDao extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByDateAndCurrencyFromAndCurrencyTo(LocalDate date, Currency currencyFrom, Currency currencyTo);
}