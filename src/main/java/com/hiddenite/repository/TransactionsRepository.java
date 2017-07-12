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

   List<Transaction> findAllByCurrencyAndHotelIDAndCreatedAtAfter(String currency, Long HotelID, Timestamp createdAt);

   List<Transaction> findAllByCurrencyAndHotelID(String currency, Long HotelID);
   Transaction findByTransactionIDAndHotelID(Long id, Long HotelID);


}
