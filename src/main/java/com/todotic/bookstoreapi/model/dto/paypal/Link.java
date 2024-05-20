package com.todotic.bookstoreapi.model.dto.paypal;

import lombok.Data;

@Data
public class Link {
    private String href;
    private String rel;
    private String method;
}
