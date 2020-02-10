package ru.bakhuss.smartsoft.web.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bakhuss.smartsoft.service.CurrencyService;
import ru.bakhuss.smartsoft.web.controller.CurrencyController;
import ru.bakhuss.smartsoft.web.view.CurrencyView;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/currency", produces = APPLICATION_JSON_VALUE)
public class CurrencyControllerImpl implements CurrencyController {
    private final Logger log = LoggerFactory.getLogger(CurrencyControllerImpl.class);

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyControllerImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/all")
    public List<CurrencyView> getAllCurrencies() {
        return currencyService.getAllCurrencies().stream()
                .map(c -> {
                    CurrencyView view = new CurrencyView();
                    view.setId(String.valueOf(c.getId()));
                    view.setNumCode(String.valueOf(c.getNumCode()));
                    view.setCharCode(c.getCharCode());
                    view.setName(c.getName());
                    return view;
                }).collect(Collectors.toList());
    }
}