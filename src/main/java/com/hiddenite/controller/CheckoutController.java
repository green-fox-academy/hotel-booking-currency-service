package com.hiddenite.controller;

import com.hiddenite.model.ChargeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckoutController {

  @Value("${STRIPE_PUBLIC_KEY}")
  private String stripePublicKey;

  @RequestMapping("/checkout")
  public String checkout(Model model) {
    model.addAttribute("amount", 50);
    model.addAttribute("stripePublicKey", stripePublicKey);
    model.addAttribute("currency", ChargeRequest.Currency.EUR);
    return "checkout";
  }

  @RequestMapping("/index")
  public String forTest() {
    return "index";
  }

}
