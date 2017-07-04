package com.hiddenite.service;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.model.checkout.CheckoutLinks;
import com.hiddenite.model.error.NoIndexException;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.repository.CheckoutDataRepository;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutDataService {
  private CheckoutDataRepository checkoutDataRepository;
  private CheckOutRepository checkOutRepository;
  private int totalPageNr;
  private final String BASIC_CHECKOUT_LINK = "https://your-hostname.com/api/checkouts";
  private final String CHECKOUT_WITH_QUERY_LINK = "https://your-hostname.com/api/checkouts?page=";
  private final int CHECKOUTS_PER_PAGE = 20;

  @Autowired
  public CheckoutDataService(CheckoutDataRepository checkoutDataRepository, CheckOutRepository checkOutRepository) {
    this.checkoutDataRepository = checkoutDataRepository;
    this.checkOutRepository = checkOutRepository;
    totalPageNr = (int) checkoutDataRepository.count() / CHECKOUTS_PER_PAGE + 1;
  }

  public void addLinks(Checkouts checkouts, int actualPageNr) {
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

  public void setCheckOutData(Checkouts checkouts, Pageable pageable) {
    checkouts.setData(checkoutDataRepository.findAll(pageable).getContent());
  }

  public void setCheckoutFiltering(Checkouts checkouts, HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    boolean hasBookingIdQuery = filterNames.containsKey("booking_id");
    boolean hasUserIdQuery = filterNames.containsKey("user_id");
    boolean hasCurrencyQuery = filterNames.containsKey("currency");
    boolean hasStatusQuery = filterNames.containsKey("status");
    Long bookingId = null;
    Long userId = null;
    ChargeRequest.Currency currency = null;
    String status = null;

    if (hasBookingIdQuery) {
      bookingId = Long.valueOf(filterNames.get("booking_id")[0]);
    }
    if (hasUserIdQuery) {
      userId = Long.valueOf(filterNames.get("user_id")[0]);
    }
    if (hasCurrencyQuery) {
      currency = ChargeRequest.Currency.valueOf(filterNames.get("currency")[0]);
    }
    if (hasStatusQuery) {
      status = filterNames.get("status")[0];
    }

    List<CheckoutData> filteredDataList = new ArrayList<>();
    if (hasBookingIdQuery && hasUserIdQuery && hasCurrencyQuery && hasStatusQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_UserIdAndAttributes_CurrencyAndAttributes_Status
                      (bookingId, userId, currency, status);
    } else if (hasBookingIdQuery && hasUserIdQuery && hasCurrencyQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_UserIdAndAttributes_Currency(bookingId, userId, currency);
    } else if (hasBookingIdQuery && hasUserIdQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributesBookingIdAndAttributes_UserId(bookingId, userId);
    } else if (hasBookingIdQuery && hasCurrencyQuery && hasStatusQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_CurrencyAndAttributes_Status(bookingId, currency, status);
    } else if (hasBookingIdQuery && hasCurrencyQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_Currency(bookingId, currency);
    } else if (hasBookingIdQuery && hasStatusQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_Status(bookingId, status);
    } else if (hasUserIdQuery && hasCurrencyQuery && hasStatusQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributes_UserIdAndAttributes_CurrencyAndAttributes_Status
              (userId, currency, status);
    } else if (hasCurrencyQuery && hasStatusQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_CurrencyAndAttributes_Status(currency, status);
    } else if (hasUserIdQuery && hasStatusQuery) {
      filteredDataList = checkoutDataRepository
              .findAllByAttributes_UserIdAndAttributes_Status(userId, status);
    } else if (hasUserIdQuery && hasCurrencyQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributes_UserIdAndAttributes_Currency
              (userId, currency);
    } else if (hasBookingIdQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributes_BookingId(bookingId);
    } else if (hasUserIdQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributes_UserId(userId);
    } else if (hasCurrencyQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributes_Currency(currency);
    } else if (hasStatusQuery) {
      filteredDataList = checkoutDataRepository.findAllByAttributes_Status(status);
    }
    checkouts.putLinksToMap("self", request.getRequestURI() + "?" + request.getQueryString());
    checkouts.setData(filteredDataList);
  }

  public Object getCheckoutById(long id) throws NoIndexException {
    if (checkOutRepository.exists(id)) {
      return checkOutRepository.findOne(id);
    } else {
      throw new NoIndexException("NOT_FOUND", id);
    }
  }

  public Object deleteCheckoutById(Long id) throws NoIndexException {
    if (checkOutRepository.exists(id)) {
      CheckoutLinks tempLinks = checkOutRepository.findOne(id).getLinks();
      checkOutRepository.delete(id);
      return tempLinks;
    } else {
      throw new NoIndexException("NOT_FOUND", id);
    }
  }

  public Object updateCheckout(Checkout inputCheckout) throws NoIndexException, IllegalAccessException, InvocationTargetException {
    long id = inputCheckout.getCheckoutData().getId();
    if (checkOutRepository.exists(id)) {
      BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
      Checkout checkout = checkOutRepository.findOne(id);
      notNull.copyProperties(checkout.getCheckoutData().getAttributes(), inputCheckout.getCheckoutData().getAttributes());
      checkOutRepository.save(checkout);
      return checkout;
    } else {
      throw new NoIndexException("NOT_FOUND", id);
    }
  }
}