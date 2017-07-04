package com.hiddenite.repository;

import com.hiddenite.model.Transaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {

   List<Transaction> findAll();

   List<Transaction> findAllByHotelID(Long hotelID);

   List<Transaction> findAllByCurrencyAndHotelID(String currency, Long HotelID);

   Transaction findByTransactionIDAndHotelID(Long id, Long HotelID);

}
