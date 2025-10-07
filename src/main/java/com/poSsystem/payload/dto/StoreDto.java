package com.poSsystem.payload.dto;

import com.poSsystem.domain.StoreStatus;
import com.poSsystem.model.StoreContact;
import com.poSsystem.model.User;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class StoreDto {

    private Long id;
    private String brand;

    private UserDto storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    private String description;

    private String storeType;

    private StoreStatus status;

    private StoreContact contact = new StoreContact();
}
