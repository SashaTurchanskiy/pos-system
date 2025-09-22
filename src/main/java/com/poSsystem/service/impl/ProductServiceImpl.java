package com.poSsystem.service.impl;

import com.poSsystem.mapper.ProductMapper;
import com.poSsystem.model.Category;
import com.poSsystem.model.Product;
import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.ProductDto;
import com.poSsystem.repository.CategoryRepository;
import com.poSsystem.repository.ProductRepository;
import com.poSsystem.repository.StoreRepository;
import com.poSsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto, User user) throws Exception {
        Store store = storeRepository.findById(productDto.getStoreId())
                .orElseThrow(()-> new Exception("Store not found with id: " + productDto.getId()));

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                ()-> new Exception("Category not found with id: " + productDto.getId())
        );

        Product product = ProductMapper.toEntity(productDto, store, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new Exception("Product not found with id: " + id));

        if (productDto.getCategoryId() !=null){
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                ()-> new Exception("Category not found with id: " + productDto.getId())
        );
        product.setCategory(category);
        }
        // Update fields
        product.setName(productDto.getName());
        product.setSku(productDto.getSku());
        product.setDescription(productDto.getDescription());
        product.setMrp(productDto.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setBrand(productDto.getBrand());
        product.setImage(productDto.getImage());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new Exception("Product not found with id: " + id)
        );
        productRepository.delete(product);

    }

    @Override
    public List<ProductDto> getProductsByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}
