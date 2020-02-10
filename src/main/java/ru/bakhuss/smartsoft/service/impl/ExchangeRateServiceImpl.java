package ru.bakhuss.smartsoft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakhuss.smartsoft.dao.ExchangeRateDao;
import ru.bakhuss.smartsoft.model.ExchangeRate;
import ru.bakhuss.smartsoft.service.ExchangeRateService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    private final ExchangeRateDao exchangeRateDao;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    public void addExchangeRate(ExchangeRate exchangeRate) {
        Optional<ExchangeRate> checkExists = exchangeRateDao
                .findByDateAndCurrencyFromAndCurrencyTo(
                        exchangeRate.getDate(),
                        exchangeRate.getCurrencyFrom(),
                        exchangeRate.getCurrencyTo()
                );
        if (checkExists.isEmpty()) exchangeRateDao.save(exchangeRate);
    }

    @Override
    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRate(Long id) {
        return exchangeRateDao.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateDao.findByDateAndCurrencyFromAndCurrencyTo(
                exchangeRate.getDate()
                , exchangeRate.getCurrencyFrom()
                , exchangeRate.getCurrencyTo()
        ).or(() -> exchangeRateDao.findByDateAndCurrencyFromAndCurrencyTo(
                exchangeRate.getDate()
                , exchangeRate.getCurrencyTo()
                , exchangeRate.getCurrencyFrom()
        )).orElseThrow(() -> {
            log.error("not found exchange rate by this parameters: " + exchangeRate.toString() + "\n"
                    + exchangeRate.getCurrencyFrom() + "\n"
                    + exchangeRate.getCurrencyTo());
            return new RuntimeException("not found exchange rate by this parameters" + exchangeRate.toString());
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateDao.findAll();
    }
}