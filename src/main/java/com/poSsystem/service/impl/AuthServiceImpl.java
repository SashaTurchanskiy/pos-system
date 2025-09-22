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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

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

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserException("User already registered with email: " + userDto.getEmail());
        }

        boolean wantsAdmin = userDto.getRole() == UserRole.ROLE_ADMIN;

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        boolean currentAuthValid = currentAuth != null
                && currentAuth.isAuthenticated()
                && !(currentAuth instanceof AnonymousAuthenticationToken);

        boolean currentIsAdmin = currentAuthValid && currentAuth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.ROLE_ADMIN.name()) || a.getAuthority().equals("ROLE_ADMIN"));

        boolean noUsers = userRepository.count() == 0; // allow first user to be admin

//        if (wantsAdmin && !(currentIsAdmin || noUsers)) {
//            throw new UserException("Role not allowed");
//        }

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


        UserDetails userDetails = customerUserImplementation.loadUserByUsername(savedUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
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
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with email: " + email));
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);
        log.info("User logged in: {}, Role: {}", email, role);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User logged in successfully");
        authResponse.setUser(UserMapper.toDTO(user));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserImplementation.loadUserByUsername(email);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid email or password");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }
}