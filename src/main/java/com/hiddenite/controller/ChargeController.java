package com.hiddenite.controller;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.repository.ChargeRequestRepository;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.service.CheckoutDataService;
import com.hiddenite.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChargeController {
  private final StripeService paymentsService;
  private final ChargeRequestRepository chargeRequestRepository;
  private final CheckOutRepository checkOutRepository;
  private CheckoutDataService checkoutDataService;

  @Autowired
  public ChargeController(StripeService paymentsService, ChargeRequestRepository chargeRequestRepository,
                          CheckOutRepository checkOutRepository, CheckoutDataService checkoutDataService) {
    this.paymentsService = paymentsService;
    this.chargeRequestRepository = chargeRequestRepository;
    this.checkOutRepository = checkOutRepository;
    this.checkoutDataService = checkoutDataService;
  }

  @PostMapping("/charge")
  public String charge(ChargeRequest chargeRequest, Model model,
                       @RequestParam("currency") ChargeRequest.Currency currency,
                       @RequestParam(value = "checkout_id", required = false) Long checkoutId,
                       javax.servlet.http.HttpServletRequest request)
          throws StripeException {
    chargeRequest.setCurrency(currency);
    addAttributes(model, paymentsService.charge(chargeRequest));
    try {
      checkoutDataService.proceedCheckout(checkOutRepository.findOne(checkoutId));
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    chargeRequestRepository.save(chargeRequest);
    return "result";
  }

  @ModelAttribute
  public void addAttributes(Model model, Charge charge) {
    model.addAttribute("id", charge.getId());
    model.addAttribute("status", charge.getStatus());
    model.addAttribute("chargeId", charge.getId());
    model.addAttribute("balance_transaction", charge.getBalanceTransaction());
  }

  @ExceptionHandler(StripeException.class)
  public String handleError(Model model, StripeException ex,
                            javax.servlet.http.HttpServletRequest request) {
    model.addAttribute("error", ex.getMessage());
    return "result";
  }
}