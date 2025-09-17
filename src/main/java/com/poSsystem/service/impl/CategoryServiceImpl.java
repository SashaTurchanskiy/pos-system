package com.poSsystem.service.impl;

import com.poSsystem.domain.UserRole;
import com.poSsystem.mapper.CategoryMapper;
import com.poSsystem.model.Category;
import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.CategoryDto;
import com.poSsystem.repository.CategoryRepository;
import com.poSsystem.repository.StoreRepository;
import com.poSsystem.service.CategoryService;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) throws Exception {
        User user = userService.getCurrentUser();

        Store store = storeRepository.findById(categoryDto.getStoreId()).orElseThrow(
                ()-> new Exception("Store not found")
        );
        Category category = Category.builder()
                .store(store)
                .name(categoryDto.getName())
                .build();

        checkAuthority(user, category.getStore());

        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getCategoriesByStoreId(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream().map(CategoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
        );

        User user = userService.getCurrentUser();
        category.setName(categoryDto.getName());
        checkAuthority(user, category.getStore());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
        );
        User user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());
        categoryRepository.delete(category);
    }

    public void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if (!(isAdmin && isSameStore) && !isManager){
            throw new Exception("You don't have permission to perform this action");
        }

    }
}
