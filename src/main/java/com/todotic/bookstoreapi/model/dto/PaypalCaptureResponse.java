package com.todotic.bookstoreapi.model.dto;

import lombok.Data;

@Data
public class PaypalCaptureResponse {
    private boolean completed;
    private Integer purchaseId;
}
