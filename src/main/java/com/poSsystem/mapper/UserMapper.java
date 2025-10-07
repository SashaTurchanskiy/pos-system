package com.poSsystem.mapper;

import com.poSsystem.model.User;
import com.poSsystem.payload.dto.UserDto;

public class UserMapper {


    public static UserDto toDTO(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
       // userDto.setPassword(savedUser.getPassword());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPhone(savedUser.getPhone());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        return userDto;
    }
    public static User toEntity(UserDto userDto) {
        User createdUser = new User();
        //user.setId(userDto.getId());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setPassword(userDto.getPassword());
        createdUser.setEmail(userDto.getEmail());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(userDto.getCreatedAt());
        createdUser.setUpdatedAt(userDto.getUpdatedAt());
        createdUser.setLastLogin(userDto.getLastLogin());
        return createdUser;
    }
}
