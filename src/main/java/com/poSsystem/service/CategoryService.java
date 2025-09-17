package com.poSsystem.service;

import com.poSsystem.payload.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto) throws Exception;
    List<CategoryDto> getCategoriesByStoreId(Long storeId);
    CategoryDto updateCategory(Long id,CategoryDto categoryDto) throws Exception;
    void deleteCategory(Long id) throws Exception;
}
