package com.todotic.bookstoreapi.repository;

import com.todotic.bookstoreapi.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
}
