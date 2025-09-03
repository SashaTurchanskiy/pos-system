package com.poSsystem.service;

import com.poSsystem.model.User;

import java.util.List;

public interface UserService {
    User getUserFromToken(String token);
    User getUserByEmail(String email);
    User getCurrentUser();
    User getUserById(Long id);
    List<User> getAllUsers();
}
