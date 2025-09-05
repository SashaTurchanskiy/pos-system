package com.poSsystem.service.impl;

import com.poSsystem.configuration.JwtProvider;
import com.poSsystem.exceptions.UserException;
import com.poSsystem.model.User;
import com.poSsystem.repository.UserRepository;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserFromToken(String token) {
        String email = jwtProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User with email " + email + " not found"));;
            log.info("User from token: {}", user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserException("User with email " + email + " not found"));
        log.info("User found by email: {}", user);
        return user;
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserException("User with email " + email + " not found"));
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserException("User with id " + id + " not found"));
        log.info("User found by id: {}", user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found");
        }
        log.info("All users: {}", users);
        return users;
    }
}
