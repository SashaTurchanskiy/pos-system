package com.poSsystem.service.impl;

import com.poSsystem.configuration.JwtProvider;
import com.poSsystem.domain.UserRole;
import com.poSsystem.exceptions.UserException;
import com.poSsystem.mapper.UserMapper;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.UserDto;
import com.poSsystem.payload.response.AuthResponse;
import com.poSsystem.repository.UserRepository;
import com.poSsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customerUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) {

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()-> new UserException("User already registered with email: " + userDto.getEmail()));

        if (userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("Role not allowed");
        }
        User newUser = new User();
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User registered successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserDto userDto) {
        return null;
    }
}
