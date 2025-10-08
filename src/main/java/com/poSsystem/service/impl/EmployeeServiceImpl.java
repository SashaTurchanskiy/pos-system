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
import java.util.stream.Collectors;

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
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {

        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()-> new Exception("Branch not found")
        );
        if (employee.getRole() == UserRole.ROLE_BRANCH_CASHIER ||
            employee.getRole() == UserRole.ROLE_BRANCH_MANAGER){

            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
        throw new Exception("Invalid role for branch employee");
    }

    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {

        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee not found"));
        Branch branch = branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(
                ()-> new Exception("Branch not found"));

        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullName(employeeDetails.getFullName());
        existingEmployee.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setBranch(branch);
        return userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User user = userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee not found")
        );
        userRepository.delete(user);

    }
    @Override
    public List<User> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found"));

        return userRepository.findByStore(store)
                .stream().filter(
                        user -> role == null || user.getRole() == role)
                .toList();
    }
    @Override
    public List<User> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()-> new Exception("Branch not found")
        );

        return userRepository.findByBranchId(branchId)
               .stream().filter(
                       user -> role == null || user.getRole() == role
               ).toList();
    }
}
