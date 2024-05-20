package com.todotic.bookstoreapi.controller;

import com.todotic.bookstoreapi.model.dto.PaypalCaptureResponse;
import com.todotic.bookstoreapi.model.dto.PaypalOrderResponse;
import com.todotic.bookstoreapi.service.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    @PostMapping("/paypal/create")
    public PaypalOrderResponse createPaypalOrder(
            @RequestParam Integer purchaseId,
            @RequestParam String returnUrl,
            @RequestParam String cancelUrl
    ) {
        return checkoutService.createPaypalPaymentUrl(purchaseId, returnUrl, cancelUrl);
    }

    @PostMapping("/paypal/capture")
    public PaypalCaptureResponse capturePaypalOrder(@RequestParam String orderId) {
        return checkoutService.capturePaypalPayment(orderId);
    }

}
