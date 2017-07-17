package com.hiddenite.controller;

import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.Transaction;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.repository.ChargeRequestRepository;
import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.repository.TransactionsRepository;
import com.hiddenite.service.ExchangeRateService;
import com.hiddenite.service.StripeService;
import com.hiddenite.service.TransactionService;
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
  private final StripeService paymentsService;
  private final ChargeRequestRepository chargeRequestRepository;
  private final CheckOutRepository checkOutRepository;
  private final TransactionsRepository transactionsRepository;
  private final TransactionService transactionService;

  @Autowired
  private ExchangeRateService exchangeRateService;

  @Autowired
  public ChargeController(StripeService paymentsService, ChargeRequestRepository chargeRequestRepository,
                          CheckOutRepository checkOutRepository, TransactionsRepository transactionsRepository,
                          ExchangeRateService exchangeRateService, TransactionService transactionService) {
    this.paymentsService = paymentsService;
    this.chargeRequestRepository = chargeRequestRepository;
    this.checkOutRepository = checkOutRepository;
    this.transactionsRepository = transactionsRepository;
    this.exchangeRateService = exchangeRateService;
    this.transactionService = transactionService;
  }

  @PostMapping("/charge")
  public String charge(ChargeRequest chargeRequest, Model model,
                       @RequestParam("currency") ChargeRequest.Currency currency,
                       @RequestParam(value = "checkout_id", required = false) Long checkoutId,
                       javax.servlet.http.HttpServletRequest request)
          throws StripeException {
    chargeRequest.setCurrency(currency);
    Charge charge = paymentsService.charge(chargeRequest);
    model.addAttribute("id", charge.getId());
    model.addAttribute("status", charge.getStatus());
    model.addAttribute("chargeId", charge.getId());
    model.addAttribute("balance_transaction", charge.getBalanceTransaction());
    checkOutRepository.findOne(checkoutId);
    try {
      Checkout checkout = checkOutRepository.findOne(checkoutId);
      checkout.getCheckoutData().getAttributes().setStatus("success");
      Transaction transaction = new Transaction(checkout.getCheckoutData().getId(),
              checkout.getCheckoutData().getAttributes().getCurrency().toString(),
              checkout.getCheckoutData().getAttributes().getAmount());
      transactionsRepository.save(transaction);
      checkOutRepository.save(checkOutRepository.findOne(checkoutId));
      transaction.setExchangeRates(exchangeRateService.getExchangeratesForGivenDates());
    } catch (Exception e) {
      e.printStackTrace();
    }
    chargeRequestRepository.save(chargeRequest);
    return "result";
  }

  @ExceptionHandler(StripeException.class)
  public String handleError(Model model, StripeException ex,
                            javax.servlet.http.HttpServletRequest request) {
    model.addAttribute("error", ex.getMessage());
    return "result";
  }
}