package com.todotic.bookstoreapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaypalOrderResponse {
    private String paypalUrl;
}
