package ru.bakhuss.smartsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.bakhuss.smartsoft.service.ExchangeService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    private final ExchangeService exchangeService;

    @Autowired
    public Application(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostConstruct
    public void init() {
        exchangeService.updateExchangeRate();
    }
}