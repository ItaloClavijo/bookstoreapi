package com.todotic.bookstoreapi.controller;

import com.todotic.bookstoreapi.model.entity.Purchase;
import com.todotic.bookstoreapi.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/purchases")
@RestController
public class PurchaseController {

    private PurchaseService purchaseService;

    @PostMapping
    public Purchase create(@RequestBody List<Integer> bookIds) {
        return purchaseService.create(bookIds);
    }

    @GetMapping("/{id}")
    public Purchase get(@PathVariable Integer id) {
        return purchaseService.findById(id);
    }

    @GetMapping("/{purchaseId}/items/{itemId}/book/file")
    Resource downloadBookFromPurchaseItem(
            @PathVariable Integer purchaseId,
            @PathVariable Integer itemId
    ) {
        return purchaseService.getItemResource(purchaseId, itemId);
    }

}
