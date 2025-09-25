package com.poSsystem.repository;

import com.poSsystem.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByBranchIdAndProductId(Long branchId, Long productId);
    Optional<Inventory> findByBranchId(Long branchId);
}
