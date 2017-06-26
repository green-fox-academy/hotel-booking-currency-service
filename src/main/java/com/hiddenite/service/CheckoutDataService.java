package com.hiddenite.service;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.repository.CheckoutDataRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutDataService {
  private CheckoutDataRepository checkoutDataRepository;
  private int totalPageNr;
  private final String BASIC_CHECKOUT_LINK = "https://your-hostname.com/api/checkouts";
  private final String CHECKOUT_WITH_QUERY_LINK = "https://your-hostname.com/api/checkouts?page=";
  private final int CHECKOUTS_PER_PAGE = 20;

  @Autowired
  public CheckoutDataService(CheckoutDataRepository checkoutDataRepository) {
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

  public void listCheckoutsByPages(Checkouts checkouts, int actualPageNr) {
    addLinks(checkouts, actualPageNr);
    setCheckOutData(checkouts, actualPageNr);
  }

  public void setCheckoutFiltering(Checkouts checkouts, HttpServletRequest request) {
    String filterName = request.getParameterNames().nextElement();
    checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + filterName + "=" + request.getParameter
            (filterName));

    if(filterName.equals("booking_id")) {
      checkouts.setData(getCheckoutListByBookingId(Long.valueOf(request.getParameter(filterName))));
    } else if(filterName.equals("user_id")) {
      checkouts.setData(getCheckoutListByUserId(Long.valueOf(request.getParameter
              (filterName))));
    } else if(filterName.equals("currency")) {
      checkouts.setData(getCheckoutListByCurrency(ChargeRequest.Currency.valueOf(request.getParameter
              (filterName))));
    } else if(filterName.equals("status")) {
      checkouts.setData(getCheckoutListByStatus(request.getParameter(filterName)));
    }
  }

  private List<CheckoutData> getCheckoutListByBookingId(long filterparam) {
    return checkoutDataRepository.findAllByAttributes_BookingId(filterparam);
  }

  private List<CheckoutData> getCheckoutListByUserId(long filterparam) {
    return checkoutDataRepository.findAllByAttributes_UserId(filterparam);
  }

  private List<CheckoutData> getCheckoutListByStatus(String status) {
    return checkoutDataRepository.findAllByAttributes_Status(status);
  }

  private List<CheckoutData> getCheckoutListByCurrency(ChargeRequest.Currency currency) {
    return checkoutDataRepository.findAllByAttributes_Currency(currency);
  }
}