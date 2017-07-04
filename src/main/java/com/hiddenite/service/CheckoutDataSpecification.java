package com.hiddenite.service;

import com.hiddenite.model.checkout.CheckoutAttribute;
import com.hiddenite.model.checkout.CheckoutData;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDataSpecification implements Specification<CheckoutData> {

  private CheckoutData filter;

  public CheckoutDataSpecification(CheckoutAttribute attr) {
    filter = new CheckoutData("checkout", attr);
  }

  @Override
  public Predicate toPredicate(Root<CheckoutData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();


    if (filter.getAttributes().getUserId() != null) {
      predicates.add(cb.equal(cb.lower(root.get("user_id")), filter.getAttributes()
              .getUserId() + "%"));
    }

    if (filter.getAttributes().getBookingId() != null) {
      predicates.add(cb.equal(cb.lower(root.get("booking_id")), filter.getAttributes()
              .getBookingId() + "%"));
    }

    return andTogether(predicates, cb);
  }

  private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
