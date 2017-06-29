package com.hiddenite.service;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.Checkouts;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.model.checkout.CheckoutLinks;
import com.hiddenite.model.error.NoIndexException;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.repository.CheckoutDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
  private Checkout checkout;

  @Autowired
  public CheckoutDataService(CheckoutDataRepository checkoutDataRepository, CheckOutRepository checkOutRepository) {
    this.checkoutDataRepository = checkoutDataRepository;
    this.checkOutRepository = checkOutRepository;
    totalPageNr = (int) checkoutDataRepository.count() / CHECKOUTS_PER_PAGE + 1;
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
            .findAll(new PageRequest(actualPageNr - 1, 20, Sort.Direction.ASC, "id"))
            .getContent();
    checkouts.setData(checkoutDataList);
  }

  public void listCheckoutsByPages(Checkouts checkouts, int actualPageNr) {
    addLinks(checkouts, actualPageNr);
    setCheckOutData(checkouts, actualPageNr);
  }

  public void setCheckoutSingleFiltering(Checkouts checkouts, HttpServletRequest request) {
    String filterName = request.getParameterNames().nextElement();
    checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + filterName + "=" + request.getParameter
            (filterName));

    if (filterName.equals("booking_id")) {
      checkouts.setData(getCheckoutListByBookingId(Long.valueOf(request.getParameter(filterName))));
    } else if (filterName.equals("user_id")) {
      checkouts.setData(getCheckoutListByUserId(Long.valueOf(request.getParameter
              (filterName))));
    } else if (filterName.equals("currency")) {
      checkouts.setData(getCheckoutListByCurrency(ChargeRequest.Currency.valueOf(request.getParameter
              (filterName))));
    } else if (filterName.equals("status")) {
      checkouts.setData(getCheckoutListByStatus(request.getParameter(filterName)));
    }
  }


  public void setCheckoutMultiFiltering(Checkouts checkouts, HttpServletRequest request) {
    Long bookingId = getBookingId(request);
    Long userId = getUserId(request);
    ChargeRequest.Currency currency = getCurrency(request);
    String status = getStatus(request);

    List<CheckoutData> checkoutDataListByMultiFilters;

    if(hasBookingIdQuery(request) && (hasUserIdQuery(request)) && (hasCurrencyQuery(request))&& (hasStatusQuery
            (request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_UserIdAndAttributes_CurrencyAndAttributes_Status
                      (bookingId, userId, currency, status);
      checkouts.setData(checkoutDataListByMultiFilters);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + "booking_Id=" + bookingId +
              "&user_Id=" + userId + "&currency=" + currency + "&status=" + status);
    } else if(hasBookingIdQuery(request) && (hasUserIdQuery(request)) && (hasCurrencyQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_UserIdAndAttributes_Currency(bookingId, userId, currency);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + "booking_Id=" + bookingId +
              "&user_Id=" + userId + "&currency=" + currency);
      checkouts.setData(checkoutDataListByMultiFilters);
    } else if(hasBookingIdQuery(request) && (hasUserIdQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository.findAllByAttributes_BookingIdAndAttributes_UserId
              (bookingId, userId);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + "booking_Id=" + bookingId +
              "&user_Id=" + userId);
      checkouts.setData(checkoutDataListByMultiFilters);
    } else if(hasBookingIdQuery(request) && (hasCurrencyQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_Currency(bookingId, currency);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + "booking_Id=" + bookingId + "&currency=" + currency);
      checkouts.setData(checkoutDataListByMultiFilters);
    } else if(hasBookingIdQuery(request) && (hasCurrencyQuery(request)) && (hasStatusQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository
              .findAllByAttributes_BookingIdAndAttributes_CurrencyAndAttributes_Status(bookingId, currency, status);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" + "booking_Id=" + bookingId +
              "&currency=" + currency + "&status=" + status);
      checkouts.setData(checkoutDataListByMultiFilters);
    } else if((hasCurrencyQuery(request)) && (hasStatusQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository
              .findAllByAttributes_CurrencyAndAttributes_Status(currency, status);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" +
              "&currency=" + currency + "&status=" + status);
      checkouts.setData(checkoutDataListByMultiFilters);
    } else if(hasUserIdQuery(request) && (hasStatusQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository
              .findAllByAttributes_UserIdAndAttributes_Status(userId, status);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" +
              "&user_id=" + userId + "&status=" + status);
      checkouts.setData(checkoutDataListByMultiFilters);
    } else if(hasUserIdQuery(request) && (hasCurrencyQuery(request))) {
      checkoutDataListByMultiFilters = checkoutDataRepository.findAllByAttributes_UserIdAndAttributes_Currency
              (userId, currency);
      checkouts.putLinksToMap("self", "https://your-hostname.com/checkouts?" +
              "&user_id=" + userId + "&currency=" + currency);
      checkouts.setData(checkoutDataListByMultiFilters);
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

//  public Object updateCheckout(Checkout inputCheckout) throws NoIndexException, IllegalAccessException, InvocationTargetException {
//    if (checkOutRepository.exists(inputCheckout.getCheckoutData().getId())) {
//      BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
//      Checkout checkout = checkOutRepository.findOne(inputCheckout.getCheckoutData().getId());
//      notNull.copyProperties(checkout.getCheckoutData().getAttributes(), inputCheckout.getCheckoutData().getAttributes());
//      checkOutRepository.save(checkout);
//      return checkout;
//    } else {
//      throw new NoIndexException("NOT_FOUND", inputCheckout.getCheckoutData().getId());
//    }
//  }

  private boolean hasBookingIdQuery(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    return filterNames.containsKey("booking_id");
  }

  private boolean hasUserIdQuery(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    return filterNames.containsKey("user_id");
  }


  private boolean hasCurrencyQuery(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    return filterNames.containsKey("currency");
  }

  private boolean hasStatusQuery(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    return filterNames.containsKey("status");
  }

  private Long getBookingId(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    if(hasBookingIdQuery(request)) {
      return Long.valueOf(filterNames.get("booking_id")[0]);
    } else {
      return null;
    }
  }

  private Long getUserId(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    if(hasUserIdQuery(request)) {
      return Long.valueOf(filterNames.get("user_id")[0]);
    } else {
      return null;
    }
  }

  private ChargeRequest.Currency getCurrency(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    if(hasCurrencyQuery(request)) {
      return ChargeRequest.Currency.valueOf(filterNames.get("currency")[0]);
    } else {
      return null;
    }
  }

  private String getStatus(HttpServletRequest request) {
    Map<String, String[]> filterNames = request.getParameterMap();
    if(hasStatusQuery(request)) {
      return filterNames.get("status")[0];
    } else {
      return null;
    }
  }
}