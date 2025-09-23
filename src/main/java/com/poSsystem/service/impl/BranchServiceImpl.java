package com.poSsystem.service.impl;

import com.poSsystem.model.User;
import com.poSsystem.payload.dto.BranchDto;
import com.poSsystem.repository.BranchRepository;
import com.poSsystem.repository.UserRepository;
import com.poSsystem.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;


    @Override
    public BranchDto createBranch(BranchDto branchDto, User user) {
        return null;
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto, User user) {
        return null;
    }

    @Override
    public BranchDto getBranchById(Long id, User user) {
        return null;
    }

    @Override
    public BranchDto deleteBranch(Long id) {
        return null;
    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) {
        return List.of();
    }
}
