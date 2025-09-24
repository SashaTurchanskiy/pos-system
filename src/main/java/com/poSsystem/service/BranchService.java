package com.poSsystem.service;

import com.poSsystem.model.User;
import com.poSsystem.payload.dto.BranchDto;

import java.util.List;

public interface BranchService {
    // Define service methods for Branch entity
    BranchDto createBranch(BranchDto branchDto, User user);
    BranchDto updateBranch(Long id, BranchDto branchDto, User user);
    BranchDto getBranchById(Long id, User user);
    void deleteBranch(Long id);
    List<BranchDto> getAllBranchesByStoreId(Long storeId);
}
