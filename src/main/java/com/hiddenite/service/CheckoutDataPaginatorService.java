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
  private CheckoutDataRepository checkoutDataRepository;
  private int totalPageNr;
  private final String BASIC_CHECKOUT_LINK = "https://your-hostname.com/api/checkouts";
  private final String CHECKOUT_WITH_QUERY_LINK = "https://your-hostname.com/api/checkouts?page=";
  private final int CHECKOUTS_PER_PAGE = 20;

  @Autowired
  public CheckoutDataPaginatorService(CheckoutDataRepository checkoutDataRepository) {
    this.checkoutDataRepository = checkoutDataRepository;
    totalPageNr = (int) checkoutDataRepository.count()/ CHECKOUTS_PER_PAGE + 1;
  }

  private void addLinks(Checkouts checkouts, int actualPageNr) {
    if (totalPageNr <= 2) {
      if (actualPageNr == 2) {
        checkouts.putLinksToMap("self", CHECKOUT_WITH_QUERY_LINK + (actualPageNr));
        checkouts.putLinksToMap("previous", BASIC_CHECKOUT_LINK);
      } else {
        checkouts.putLinksToMap("self", BASIC_CHECKOUT_LINK);
        checkouts.putLinksToMap("next", CHECKOUT_WITH_QUERY_LINK + (actualPageNr + 1));
      }
    } else if (totalPageNr > 2) {
      if (actualPageNr == 1) {
        checkouts.putLinksToMap("self", BASIC_CHECKOUT_LINK);
      } else {
        checkouts.putLinksToMap("self", CHECKOUT_WITH_QUERY_LINK + (actualPageNr));
        if (actualPageNr == 2) {
          checkouts.putLinksToMap("previous", BASIC_CHECKOUT_LINK);
        } else if (actualPageNr > 2) {
          checkouts.putLinksToMap("previous", CHECKOUT_WITH_QUERY_LINK + (actualPageNr - 1));
        }
      }
      if (actualPageNr != totalPageNr) {
        checkouts.putLinksToMap("next", CHECKOUT_WITH_QUERY_LINK + (actualPageNr + 1));
      }
      checkouts.putLinksToMap("last", CHECKOUT_WITH_QUERY_LINK + totalPageNr);
    }
  }

  private void setCheckOutData(Checkouts checkouts, int actualPageNr) {
    List<CheckoutData> checkoutDataList = checkoutDataRepository
            .findAll(new PageRequest(actualPageNr - 1,20, Sort.Direction.ASC, "id"))
            .getContent();
    checkouts.setData(checkoutDataList);
  }

  public void setCheckouts(Checkouts checkouts, int actualPageNr) {
    addLinks(checkouts, actualPageNr);
    setCheckOutData(checkouts, actualPageNr);
  }
}