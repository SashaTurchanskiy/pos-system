package com.poSsystem.controller;

import com.poSsystem.model.User;
import com.poSsystem.payload.dto.ProductDto;
import com.poSsystem.payload.response.ApiResponse;
import com.poSsystem.service.ProductService;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromToken(jwt);
        return ResponseEntity.ok(productService.createProduct(productDto, user));
    }
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDto>> getByStoreId(@PathVariable Long storeId,
                                                         @RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(productService.getProductsByStoreId(storeId));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @RequestBody ProductDto productDto,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromToken(jwt);
        return ResponseEntity.ok(productService.updateProduct(id, productDto, user));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromToken(jwt);
        productService.deleteProduct(id, user);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDto>> searchByKeyword(@PathVariable Long storeId,
                                                              @RequestParam String keyword,
                                                                @RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
    }
}
