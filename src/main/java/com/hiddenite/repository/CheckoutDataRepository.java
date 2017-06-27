package com.hiddenite.repository;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.checkout.CheckoutData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutDataRepository extends CrudRepository<CheckoutData, Long> {
  List<CheckoutData> findAll();
  Page<CheckoutData> findAll(Pageable pageable);
  List<CheckoutData> findAllByAttributes_BookingId(long bookingId);
  List<CheckoutData> findAllByAttributes_UserId(long userId);
  List<CheckoutData> findAllByAttributes_Currency(ChargeRequest.Currency currency);
  List<CheckoutData> findAllByAttributes_Status(String status);
}
