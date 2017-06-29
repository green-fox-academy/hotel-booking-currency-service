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

  List<CheckoutData> findAllByAttributes_BookingId(Long bookingId);
  List<CheckoutData> findAllByAttributes_UserId(Long userId);
  List<CheckoutData> findAllByAttributes_Currency(ChargeRequest.Currency currency);
  List<CheckoutData> findAllByAttributes_Status(String status);
  List<CheckoutData> findAllByAttributesBookingIdAndAttributes_UserId(Long bookingId, Long userId);
  List<CheckoutData> findAllByAttributes_BookingIdAndAttributes_UserIdAndAttributes_Currency(Long bookingId, Long
          userId, ChargeRequest.Currency currency);
  List<CheckoutData> findAllByAttributes_BookingIdAndAttributes_UserIdAndAttributes_CurrencyAndAttributes_Status(Long bookingId, Long userId, ChargeRequest.Currency currency, String status);
  List<CheckoutData> findAllByAttributes_BookingIdAndAttributes_CurrencyAndAttributes_Status(Long bookingId,ChargeRequest.Currency currency, String status);
  List<CheckoutData> findAllByAttributes_BookingIdAndAttributes_Currency(Long bookingId, ChargeRequest.Currency
          currency);
  List<CheckoutData> findAllByAttributes_BookingIdAndAttributes_Status(Long bookingID, String status);
  List<CheckoutData> findAllByAttributes_UserIdAndAttributes_CurrencyAndAttributes_Status(Long userId, ChargeRequest
          .Currency currency, String status);
  List<CheckoutData> findAllByAttributes_UserIdAndAttributes_Currency(Long userId, ChargeRequest.Currency currency);
  List<CheckoutData> findAllByAttributes_UserIdAndAttributes_Status(Long userId, String status);
  List<CheckoutData> findAllByAttributes_CurrencyAndAttributes_Status(ChargeRequest.Currency currency, String status);
}
