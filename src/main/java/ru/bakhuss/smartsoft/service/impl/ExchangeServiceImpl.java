package ru.bakhuss.smartsoft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bakhuss.smartsoft.dao.ExchangeDao;
import ru.bakhuss.smartsoft.model.Currency;
import ru.bakhuss.smartsoft.model.Exchange;
import ru.bakhuss.smartsoft.model.ExchangeRate;
import ru.bakhuss.smartsoft.model.User;
import ru.bakhuss.smartsoft.service.CurrencyService;
import ru.bakhuss.smartsoft.service.ExchangeRateService;
import ru.bakhuss.smartsoft.service.ExchangeService;
import ru.bakhuss.smartsoft.service.xmlservice.XmlParser;
import ru.bakhuss.smartsoft.web.view.CurrencyListFromCBRView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private final Logger log = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    @Value("${base.currency}")
    private Integer baseCurrency;
    private final XmlParser parser;
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeDao exchangeDao;

    @Autowired
    public ExchangeServiceImpl(XmlParser parser
            , CurrencyService currencyService
            , ExchangeRateService exchangeRateService
            , ExchangeDao exchangeDao) {
        this.parser = parser;
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
        this.exchangeDao = exchangeDao;
    }

    @Override
    @Transactional
    public Exchange exchange(Exchange exchange) {
        ExchangeRate rate = new ExchangeRate();
        rate.setDate(exchange.getRateDate());
        rate.setCurrencyFrom(exchange.getCurrencyFrom());
        rate.setCurrencyTo(exchange.getCurrencyTo());

        double result;
        ExchangeRate fromDB = exchangeRateService.getExchangeRate(rate);
        if (fromDB == null) {
            updateExchangeRate();
            fromDB = exchangeRateService.getExchangeRate(rate);
        }
        if (fromDB.getCurrencyFrom().equals(exchange.getCurrencyFrom()))
            result = fromDB.rateOneCurrencyFrom() * exchange.getAmount();
        else result = fromDB.rateOneCurrencyTo() * exchange.getAmount();

        exchange.setResult(result);
        log.info(exchange.toString());
        return exchangeDao.save(exchange);
    }

    @Override
    public void updateExchangeRate() {
        CurrencyListFromCBRView view = parser.parseXmlFromCBRToView();
        log.info(view.toString());
        List<Currency> collect = view.getViews().stream()
                .map(c -> {
                    Currency currency = new Currency();
                    currency.setNumCode(Integer.parseInt(c.getNumCode()));
                    currency.setCharCode(c.getCharCode());
                    currency.setName(c.getName());
                    return currency;
                }).collect(Collectors.toList());
        collect.forEach(currencyService::addCurrency);
        Currency rur = new Currency();
        rur.setName("Российский рубль");
        rur.setCharCode("RUR");
        rur.setNumCode(643);
        currencyService.addCurrency(rur);

        List<ExchangeRate> exchangeRateList = view.getViews().stream()
                .map(c -> {
                    ExchangeRate rate = new ExchangeRate();
                    rate.setCurrencyFrom(currencyService.getCurrencyByNumCode(Integer.parseInt(c.getNumCode())));
                    rate.setCurrencyTo(currencyService.getCurrencyByNumCode(baseCurrency));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    formatter.parse(view.getDate());
                    rate.setDate(LocalDate.from(formatter.parse(view.getDate())));

                    rate.setNominal(Integer.parseInt(c.getNominal()));

                    DecimalFormat df = new DecimalFormat();
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setDecimalSeparator(',');
                    df.setDecimalFormatSymbols(dfs);
                    try {
                        rate.setValue(df.parse(c.getValue()).doubleValue());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return rate;
                }).collect(Collectors.toList());

        exchangeRateList.forEach(exchangeRateService::addExchangeRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exchange> getExchangesByUserAndExchangeDate(User user, Date exchangeDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(exchangeDate);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date startDate = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = c.getTime();

        return exchangeDao.findAllByUserAndExchangeDateGreaterThanEqualAndExchangeDateLessThan(user, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exchange> getExchangesByUserAndRateDate(User user, LocalDate rateDate) {
        return exchangeDao.findByUserAndRateDate(user, rateDate);
    }
}