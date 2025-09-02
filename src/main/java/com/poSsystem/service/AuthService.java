package com.poSsystem.service;

import com.poSsystem.payload.dto.UserDto;
import com.poSsystem.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto);
    AuthResponse login(UserDto userDto);

}
