package ru.bakhuss.smartsoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bakhuss.smartsoft.model.Currency;

import java.util.Optional;

@Repository
public interface CurrencyDao extends JpaRepository<Currency, Long> {
    Optional<Currency> findByNumCode(Integer numCode);
}
