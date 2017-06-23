package com.hiddenite.service;

import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.repository.CheckoutDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CheckoutDataPaginatorService {
  CheckoutDataRepository checkoutDataRepository;
  long totalPageNr;

  @Autowired
  public CheckoutDataPaginatorService(CheckoutDataRepository checkoutDataRepository) {
    this.checkoutDataRepository = checkoutDataRepository;
    totalPageNr = checkoutDataRepository.count()/20;
  }

  public CheckoutDataPaginatorService() {
  }

  public void setLinks(Checkouts checkouts, Integer actualPageNr) {
    String basicCheckout = "https://your-hostname.com/api/checkouts";
    String checkoutWithQuery = "https://your-hostname.com/api/checkouts?page=";
    if (totalPageNr < 1) {
      checkouts.putLinksMap("self", basicCheckout);
    } else if (totalPageNr == 1) {
      if (actualPageNr == 2) {
        checkouts.putLinksMap("self", checkoutWithQuery + (actualPageNr));
        checkouts.putLinksMap("previous", basicCheckout);
      } else {
        checkouts.putLinksMap("self", basicCheckout);
        checkouts.putLinksMap("next", checkoutWithQuery + (actualPageNr));
      }
    } else if (totalPageNr > 1) {
      if (actualPageNr == 1) {
        checkouts.putLinksMap("self", basicCheckout);
      } else if (actualPageNr == 2) {
        checkouts.putLinksMap("previous", basicCheckout);
        checkouts.putLinksMap("self", checkoutWithQuery + (actualPageNr));
      } else if (actualPageNr > 2) {
        checkouts.putLinksMap("previous", checkoutWithQuery + (actualPageNr - 1));
        checkouts.putLinksMap("self", checkoutWithQuery + (actualPageNr));
      }
      checkouts.putLinksMap("last", checkoutWithQuery + (totalPageNr + 1));
      if (actualPageNr != totalPageNr + 1) {
        checkouts.putLinksMap("next", checkoutWithQuery + (actualPageNr + 1));
      }
    }
  }

  public void setData(Checkouts checkouts, int actualPageNr) {
    List<CheckoutData> checkoutDataList = checkoutDataRepository
            .findAll(new PageRequest(actualPageNr - 1,20, Sort.Direction.ASC, "id"))
            .getContent();
    checkouts.setData(checkoutDataList);
  }
}