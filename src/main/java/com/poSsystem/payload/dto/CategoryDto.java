package com.poSsystem.payload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Long id;
    private String name;
   // private StoreDto store;
    private Long storeId;
}
