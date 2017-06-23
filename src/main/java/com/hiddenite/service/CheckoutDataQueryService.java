//package com.hiddenite.service;
//
//import com.hiddenite.repository.CheckoutDataRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//
//public class CheckoutDataQueryService {
//
//  @Autowired
//  CheckoutDataRepository checkoutDataRepository;
//
//  Query queryForIds = entityManager.createQuery(checkoutDataRepository);
//  List<Integer> fooIds = queryForIds.getResultList();
//  Query query = entityManager.createQuery(
//          "Select f from Foo e where f.id in :ids");
//query.setParameter("ids", fooIds.subList(0,10));
//  List<Foo> fooList = query.getResultList();
//}
