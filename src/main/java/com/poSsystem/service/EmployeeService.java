package com.poSsystem.service;

import com.poSsystem.domain.UserRole;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.UserDto;

import java.util.List;

public interface EmployeeService {
    UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception;
    UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception;
    User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception;
    void deleteEmployee(Long employeeId) throws Exception;
    List<User> findStoreEmployees(Long storeId, UserRole role) throws Exception;
    List<User> findBranchEmployees(Long branchId, UserRole role) throws Exception;
    

}
