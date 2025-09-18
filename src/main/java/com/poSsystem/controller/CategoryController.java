package com.poSsystem.controller;

import com.poSsystem.payload.dto.CategoryDto;
import com.poSsystem.payload.response.ApiResponse;
import com.poSsystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) throws Exception {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.ok(categoryService.getCategoriesByStoreId(storeId));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id,
                                                      @RequestBody CategoryDto categoryDto) throws Exception {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id,
                                                      @RequestBody CategoryDto categoryDto) throws Exception {
        categoryService.deleteCategory(categoryDto.getId());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }
}
