package com.todotic.bookstoreapi.service;

import com.todotic.bookstoreapi.exception.BadRequestException;
import com.todotic.bookstoreapi.exception.ResourceNotFoundException;
import com.todotic.bookstoreapi.model.entity.Book;
import com.todotic.bookstoreapi.model.entity.Purchase;
import com.todotic.bookstoreapi.model.entity.PurchaseItem;
import com.todotic.bookstoreapi.repository.BookRepository;
import com.todotic.bookstoreapi.repository.PurchaseItemRepository;
import com.todotic.bookstoreapi.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseService {

    private PurchaseRepository purchaseRepository;
    private PurchaseItemRepository purchaseItemRepository;
    private BookRepository bookRepository;
    private StorageService storageService;

    public Purchase findById(Integer id) {
        return purchaseRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Purchase create(List<Integer> bookIds) {
        Purchase purchase = new Purchase();
        purchase.setCreatedAt(LocalDateTime.now());
        purchase.setPaymentStatus(Purchase.PaymentStatus.PENDING);
        purchase.setCustomer(null);

        float total = 0;
        List<PurchaseItem> items = new ArrayList<>();

        for (int bookId : bookIds) {
            Book book = bookRepository
                    .findById(bookId)
                    .orElseThrow(ResourceNotFoundException::new);

            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setPurchase(purchase);
            purchaseItem.setPrice(book.getPrice());
            purchaseItem.setBook(book);
            purchaseItem.setDownloadsAvailable(3);

            items.add(purchaseItem);
            total += book.getPrice();
        }
        purchase.setTotal(total);
        purchase.setItems(items);

        return purchaseRepository.save(purchase);
    }

    public Resource getItemResource(Integer purchaseId, Integer itemId) {
        PurchaseItem purchaseItem = purchaseItemRepository
                .findByIdAndPurchaseId(itemId, purchaseId)
                .orElseThrow(ResourceNotFoundException::new);

        Purchase purchase = purchaseItem.getPurchase();

        if (purchase.getPaymentStatus().equals(Purchase.PaymentStatus.PENDING)) {
            throw new BadRequestException("La compra no ha sido pagada aún.");
        }

        if (purchaseItem.getDownloadsAvailable() > 0) {
            purchaseItem.setDownloadsAvailable(
                    purchaseItem.getDownloadsAvailable() - 1
            );
            purchaseItemRepository.save(purchaseItem);
            return storageService.loadAsResource(purchaseItem.getBook().getFilePath());
        } else {
            throw new BadRequestException("No se puede descargar más este libro.");
        }
    }

    public Purchase updateToPaid(Integer purchaseId) {
        Purchase purchase = purchaseRepository
                .findById(purchaseId)
                .orElseThrow(ResourceNotFoundException::new);

        purchase.setPaymentStatus(Purchase.PaymentStatus.PAID);
        return purchaseRepository.save(purchase);
    }

}
