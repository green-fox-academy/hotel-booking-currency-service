package com.hiddenite.repository;

import com.hiddenite.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {

   List<Transaction> findAll();

   List<Transaction> findAllByHotelID(Long hotelID);

   List<Transaction> findAllByCurrencyAndHotelIDAndCreatedAtBetween(String currency, Long HotelID, Timestamp
           createdAtFrom, Timestamp createdAtTo);
   List<Transaction> findAllByHotelIDAndCreatedAtBetween(Long HotelID, Timestamp
           createdAtFrom, Timestamp createdAtTo);
   List<Transaction> findAllByCurrencyAndHotelID(String currency, Long HotelID);
   Transaction findByTransactionIDAndHotelID(Long id, Long HotelID);


}
