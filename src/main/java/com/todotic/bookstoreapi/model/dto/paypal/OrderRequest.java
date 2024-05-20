package com.todotic.bookstoreapi.model.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String intent;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

//    @JsonProperty("payment_source")
//    private PaymentSource paymentSource;

    @JsonProperty("application_context")
    private ApplicationContext applicationContext;
}
