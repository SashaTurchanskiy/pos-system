package com.poSsystem.service;

import com.poSsystem.domain.UserRole;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.UserDto;

import java.util.List;

public interface EmployeeService {
    UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception;
    UserDto createBranchEmployee(UserDto employee, Long branchId);
    User updateEmployee(Long employeeId, User employeeDetails);
    void deleteEmployee(Long employeeId);
    List<User> findStoreEmployees(Long storeId, UserRole role);
    List<User> findBranchEmployees(Long branchId, UserRole role);
    

}
