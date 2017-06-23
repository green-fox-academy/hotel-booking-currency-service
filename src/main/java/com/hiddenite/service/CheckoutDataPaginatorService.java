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

  public void setLinks(Checkouts checkouts, int actualPageNr) {
    if (totalPageNr < 1) {
      checkouts.putLinksMap("self", "https://your-hostname.com/api/checkouts");
    } else if (totalPageNr == 2) {
      if (actualPageNr == 1) {
        checkouts.putLinksMap("self", "https://your-hostname.com/api/checkouts?page=" + (actualPageNr + 1));
        checkouts.putLinksMap("previous", "https://your-hostname.com/api/checkouts");
      } else {
        checkouts.putLinksMap("self", "https://your-hostname.com/api/checkouts");
        checkouts.putLinksMap("next", "https://your-hostname.com/api/checkouts?page=" + (actualPageNr + 1));
      }
    } else if (totalPageNr > 2) {
      checkouts.putLinksMap("self", "https://your-hostname.com/api/checkouts?page=" + (actualPageNr + 1));
      checkouts.putLinksMap("next", "https://your-hostname.com/api/checkouts?page=" + (actualPageNr + 2));
      checkouts.putLinksMap("previous", "https://your-hostname.com/api/checkouts?page=" + actualPageNr);
      checkouts.putLinksMap("last", "https://your-hostname.com/api/checkouts?page=" + totalPageNr);
    }
  }

  public void setData(Checkouts checkouts, int actualPageNr) {
    List<CheckoutData> checkoutDataList = checkoutDataRepository.findAll(new PageRequest(actualPageNr,20, Sort
            .Direction.ASC, "id")).getContent();
    checkouts.setData(checkoutDataList);
  }
}