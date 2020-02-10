package ru.bakhuss.smartsoft.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    @Version
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Integer version;

    @ManyToOne
    Currency currencyFrom;
    @ManyToOne
    Currency currencyTo;
    @ManyToOne
    User user;

    Date exchangeDate;
    LocalDate rateDate;
    Integer amount;
    Double result;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{id:" + getId() +
                ";currencyFromId:" + getCurrencyFrom().getId() +
                ";currencyToId:" + getCurrencyTo().getId() +
                ";date:" + getRateDate() +
                ";currentDateTime:" + getExchangeDate() +
                ";amount:" + getAmount() +
                ";result:" + getResult() +
                "}";
    }
}
