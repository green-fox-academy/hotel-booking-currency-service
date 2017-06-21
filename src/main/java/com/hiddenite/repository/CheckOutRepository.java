package com.hiddenite.repository;

import com.hiddenite.model.checkout.Checkout;
import org.springframework.data.repository.CrudRepository;


public interface CheckOutRepository extends CrudRepository<Checkout, Long> {

}
