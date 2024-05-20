package com.todotic.bookstoreapi.repository;

import com.todotic.bookstoreapi.model.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Integer> {

    Optional<PurchaseItem> findByIdAndPurchaseId(Integer id, Integer purchaseId);

}
