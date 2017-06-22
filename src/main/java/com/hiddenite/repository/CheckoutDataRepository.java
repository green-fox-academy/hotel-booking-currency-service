package com.hiddenite.repository;

import com.hiddenite.model.checkout.CheckoutData;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface CheckoutDataRepository extends CrudRepository<CheckoutData, Long> {
  public List<CheckoutData> findAll();
}
