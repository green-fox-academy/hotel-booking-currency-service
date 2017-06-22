package com.hiddenite.controller;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.repository.ChargeRequestRepository;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChargeController {

  @Autowired
  private StripeService paymentsService;
  @Autowired
  private ChargeRequestRepository chargeRequestRepository;
  @Autowired
  private CheckOutRepository checkOutRepository;

  @PostMapping("/charge")
  public String charge(ChargeRequest chargeRequest, Model model, @RequestParam("currency") ChargeRequest.Currency currency, @RequestParam(value = "checkout_id", required = false) Long checkoutId)
          throws StripeException {
//    chargeRequest.setDescription("Example charge");
    chargeRequest.setCurrency(currency);
    Charge charge = paymentsService.charge(chargeRequest);
    model.addAttribute("id", charge.getId());
    model.addAttribute("status", charge.getStatus());
    model.addAttribute("chargeId", charge.getId());
    model.addAttribute("balance_transaction", charge.getBalanceTransaction());
    checkOutRepository.findOne(checkoutId);
    try {
      checkOutRepository.findOne(checkoutId).getCheckoutData().getAttributes().setStatus("success");
      checkOutRepository.save(checkOutRepository.findOne(checkoutId));
    } catch (Exception e) {
      e.printStackTrace();
    }
    chargeRequestRepository.save(chargeRequest);
    return "result";
  }

  @ExceptionHandler(StripeException.class)
  public String handleError(Model model, StripeException ex) {
    model.addAttribute("error", ex.getMessage());
    return "result";
  }
}