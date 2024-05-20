package com.todotic.bookstoreapi.service;

import com.todotic.bookstoreapi.model.dto.PaypalCaptureResponse;
import com.todotic.bookstoreapi.model.dto.PaypalOrderResponse;
import com.todotic.bookstoreapi.model.dto.paypal.OrderCaptureResponse;
import com.todotic.bookstoreapi.model.dto.paypal.OrderResponse;
import com.todotic.bookstoreapi.model.entity.Purchase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckoutService {

    private PaypalService paypalService;
    private PurchaseService purchaseService;

    public PaypalOrderResponse createPaypalPaymentUrl(Integer purchaseId, String returnUrl, String cancelUrl) {
        OrderResponse orderResponse = paypalService.createOrder(purchaseId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaypalOrderResponse(paypalUrl);
    }

    public PaypalCaptureResponse capturePaypalPayment(String orderId) {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaypalCaptureResponse paypalCaptureResponse = new PaypalCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            Purchase purchase = purchaseService.updateToPaid(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setPurchaseId(purchase.getId());
        }
        return paypalCaptureResponse;
    }

}
