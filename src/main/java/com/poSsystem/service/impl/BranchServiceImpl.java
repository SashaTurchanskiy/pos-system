package com.poSsystem.service.impl;

import com.poSsystem.mapper.BranchMapper;
import com.poSsystem.model.Branch;
import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.BranchDto;
import com.poSsystem.repository.BranchRepository;
import com.poSsystem.repository.StoreRepository;
import com.poSsystem.repository.UserRepository;
import com.poSsystem.service.BranchService;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;


    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + branchDto.getId()));
        Branch branch = BranchMapper.toEntity(branchDto);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));

         existingBranch.setName(branchDto.getName());
            existingBranch.setAddress(branchDto.getAddress());
            existingBranch.setPhone(branchDto.getPhone());
            existingBranch.setEmail(branchDto.getEmail());
            existingBranch.setWorkingDays(branchDto.getWorkingDays());
            existingBranch.setOpenTime(branchDto.getOpenTime());
            existingBranch.setCloseTime(branchDto.getCloseTime());
            existingBranch.setUpdatedAt(java.time.LocalDateTime.now());

            Branch updatedBranch = branchRepository.save(existingBranch);

        return BranchMapper.toDTO(updatedBranch);
    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        return BranchMapper.toDTO(existingBranch);
    }

    @Override
    public void deleteBranch(Long id) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        branchRepository.delete(existingBranch);

    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findAllByStoreId(storeId);
        return branches.stream()
                .map(BranchMapper::toDTO)
                .toList();
    }
}
