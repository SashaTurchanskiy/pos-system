package com.poSsystem.repository;

import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStore(Store store);
    List<User> findByBranchId(Long branchId);
}
