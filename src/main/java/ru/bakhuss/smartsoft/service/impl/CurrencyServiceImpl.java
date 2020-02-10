package ru.bakhuss.smartsoft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakhuss.smartsoft.dao.CurrencyDao;
import ru.bakhuss.smartsoft.model.Currency;
import ru.bakhuss.smartsoft.service.CurrencyService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {
    private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyDao currencyDao;

    @Autowired
    public CurrencyServiceImpl(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public void addCurrency(Currency currency) {
        Optional<Currency> checkCurrencyExists = currencyDao.findByNumCode(currency.getNumCode());
        if (checkCurrencyExists.isEmpty()) currencyDao.save(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public Currency getCurrency(Long id) {
        return currencyDao.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Currency getCurrencyByNumCode(Integer numCode) {
        return currencyDao.findByNumCode(numCode).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateCurrency(Currency currency) {
        currencyDao.save(currency);
    }

    @Override
    public void deleteCurrency(Long id) {
        currencyDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Currency> getAllCurrencies() {
        return currencyDao.findAll();
    }
}