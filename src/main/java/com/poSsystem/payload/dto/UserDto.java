package com.poSsystem.payload.dto;

import com.poSsystem.domain.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private UserRole role;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

}
