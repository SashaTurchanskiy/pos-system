package com.poSsystem.controller;

import com.poSsystem.mapper.UserMapper;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.UserDto;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(){
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
