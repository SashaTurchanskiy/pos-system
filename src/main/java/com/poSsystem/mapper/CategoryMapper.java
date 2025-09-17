package com.poSsystem.mapper;

import com.poSsystem.model.Category;
import com.poSsystem.payload.dto.CategoryDto;

public class CategoryMapper {

    public static CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}
