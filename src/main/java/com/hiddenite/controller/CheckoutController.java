package com.hiddenite.controller;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.repository.CheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

  @Autowired
  CheckOutRepository checkOutRepository;

  @Value("${STRIPE_PUBLIC_KEY}")
  private String stripePublicKey;
  private int amount;
  private ChargeRequest.Currency currency;

  @GetMapping("/checkout")
  public String checkout(Model model, @RequestParam(name = "checkout_id", required = false) Long checkoutId, javax.servlet.http.HttpServletRequest request) {
    try {
      Checkout currentCheckout = checkOutRepository.findOne(checkoutId);
      amount = currentCheckout.getCheckoutData().getAttributes().getAmount();
      currency = currentCheckout.getCheckoutData().getAttributes().getCurrency();
      model.addAttribute("checkout_id", checkoutId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addAttribute("amount", amount);
    model.addAttribute("stripePublicKey", stripePublicKey);
    model.addAttribute("currency", currency);
    return "checkout";
  }
}
