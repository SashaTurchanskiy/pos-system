package com.poSsystem.service.impl;

import com.poSsystem.domain.UserRole;
import com.poSsystem.mapper.UserMapper;
import com.poSsystem.model.Branch;
import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.UserDto;
import com.poSsystem.repository.BranchRepository;
import com.poSsystem.repository.StoreRepository;
import com.poSsystem.repository.UserRepository;
import com.poSsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found"));
        Branch branch = null;
        if (employee.getRole() == UserRole.ROLE_BRANCH_MANAGER){
            if (employee.getBranchId() == null){
                throw new Exception("Branch ID is required for Branch Manager");
            }
            branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                    ()-> new Exception("Branch not found"));
        }
        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);
        if (employee.getRole() == UserRole.ROLE_BRANCH_MANAGER && branch != null){
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) {
        return null;
    }

    @Override
    public User updateEmployee(Long employeeId, User employeeDetails) {
        return null;
    }

    @Override
    public void deleteEmployee(Long employeeId) {

    }

    @Override
    public List<User> findStoreEmployees(Long storeId, UserRole role) {
        return List.of();
    }

    @Override
    public List<User> findBranchEmployees(Long branchId, UserRole role) {
        return List.of();
    }
}
