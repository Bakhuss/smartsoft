package ru.bakhuss.smartsoft.web.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class ExchangeView {
    String currencyFromId;
    String currencyToId;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @JsonProperty("date")
    LocalDate rateDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    Date exchangeDate;
    String amount;
    String result;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{currencyFromId:" + getCurrencyFromId() +
                ";currencyToId:" + getCurrencyToId() +
                ";rateDate:" + getRateDate() +
                ";exchangeDate:" + getExchangeDate() +
                ";amount:" + getAmount() +
                ";result:" + getResult() +
                "}";
    }
}