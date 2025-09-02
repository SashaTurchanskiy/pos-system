package com.poSsystem.payload.response;

import com.poSsystem.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDto user;

}
