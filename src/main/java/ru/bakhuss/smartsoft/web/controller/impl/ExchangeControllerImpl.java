package ru.bakhuss.smartsoft.web.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bakhuss.smartsoft.model.Currency;
import ru.bakhuss.smartsoft.model.Exchange;
import ru.bakhuss.smartsoft.model.User;
import ru.bakhuss.smartsoft.service.CurrencyService;
import ru.bakhuss.smartsoft.service.ExchangeService;
import ru.bakhuss.smartsoft.service.UserService;
import ru.bakhuss.smartsoft.web.controller.ExchangeController;
import ru.bakhuss.smartsoft.web.view.ExchangeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/exchange", produces = APPLICATION_JSON_VALUE)
public class ExchangeControllerImpl implements ExchangeController {
    private final Logger log = LoggerFactory.getLogger(ExchangeControllerImpl.class);

    private final String username = "user1";

    private final ExchangeService exchangeService;
    private final CurrencyService currencyService;
    private final UserService userService;

    @Autowired
    public ExchangeControllerImpl(ExchangeService exchangeService
            , CurrencyService currencyService
            , UserService userService) {
        this.exchangeService = exchangeService;
        this.currencyService = currencyService;
        this.userService = userService;
    }

    @PostMapping(value = "/")
    public ExchangeView exchange(@RequestBody ExchangeView exchangeView) {
        Exchange exchange = new Exchange();
        Currency currencyFrom = currencyService.getCurrency(Long.parseLong(exchangeView.getCurrencyFromId()));
        Currency currencyTo = currencyService.getCurrency(Long.parseLong(exchangeView.getCurrencyToId()));

        exchange.setCurrencyFrom(currencyFrom);
        exchange.setCurrencyTo(currencyTo);
        exchange.setRateDate(exchangeView.getRateDate());
        exchange.setAmount(Integer.parseInt(exchangeView.getAmount()));
        exchange.setUser(userService.findByUsername(username));
        exchange.setExchangeDate(new Date());

        Exchange result = exchangeService.exchange(exchange);
        exchangeView.setResult(result.getResult().toString());
        return exchangeView;
    }

    @GetMapping(value = "/{username}")
    public List<ExchangeView> exchangesByUserAndExchageDate(@PathVariable String username
            , @RequestParam(name = "exchange_date") String exchangeDate
            , @RequestParam(name = "rate_date", required = false) String rateDate) {
        User user = userService.findByUsername(username);
        Date exchDate = null;
        try {
            exchDate = new SimpleDateFormat("dd.MM.yyyy").parse(exchangeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return exchangeService.getExchangesByUserAndExchangeDate(user, exchDate).stream()
                .map(e -> {
                    ExchangeView view = new ExchangeView();
                    view.setExchangeDate(e.getExchangeDate());
                    view.setCurrencyFromId(e.getCurrencyFrom().getId().toString());
                    view.setCurrencyToId(e.getCurrencyTo().getId().toString());
                    view.setAmount(e.getAmount().toString());
                    view.setResult(e.getResult().toString());
                    return view;
                }).collect(Collectors.toList());
    }
}
