package com.hiddenite.service;

import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.repository.CheckoutDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutDataPaginatorService {

  CheckoutDataRepository checkoutDataRepository;
  int totalPageNr;

  public CheckoutDataPaginatorService(CheckoutDataRepository checkoutDataRepository) {
    this.checkoutDataRepository = checkoutDataRepository;
    totalPageNr = (int) checkoutDataRepository.count()/20;
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

  public void setData(Checkouts checkouts, int pageNr) {
    List<CheckoutData> checkoutDataList = checkoutDataRepository.findAll();
    checkouts.setData(checkoutDataList.subList(pageNr * 20, pageNr + 19));
  }
}