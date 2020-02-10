package ru.bakhuss.smartsoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bakhuss.smartsoft.model.Exchange;
import ru.bakhuss.smartsoft.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeDao extends JpaRepository<Exchange, Long> {
    List<Exchange> findByUserAndRateDate(User user, LocalDate rateDate);

//    List<Exchange> findByUserAndExchangeDate(User user, Date exchangeDate);

    List<Exchange> findAllByUserAndExchangeDateGreaterThanEqualAndExchangeDateLessThan(User user, Date startExchangeDate, Date endExchangeDate);
}