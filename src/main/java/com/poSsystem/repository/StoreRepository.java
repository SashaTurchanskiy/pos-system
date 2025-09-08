package com.poSsystem.repository;

import com.poSsystem.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreAdminId(Long storeAdminId);
}
