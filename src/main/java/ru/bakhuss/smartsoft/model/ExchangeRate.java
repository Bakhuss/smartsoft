package ru.bakhuss.smartsoft.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    Long id;
    @Version
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    Integer version;

    @ManyToOne(fetch = FetchType.EAGER)
    Currency currencyFrom;
    @ManyToOne(fetch = FetchType.EAGER)
    Currency currencyTo;
    LocalDate date;
    Integer nominal;
    Double value;

    public double rateOneCurrencyFrom() {
        return getValue() / getNominal();
    }

    public double rateOneCurrencyTo() {
        return getNominal() / getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{id:" + getId() +
                ";date:" + getDate() +
                ";nominal:" + getNominal() +
                ";value:" + getValue() +
                "}";
    }
}