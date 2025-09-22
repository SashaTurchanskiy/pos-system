package com.poSsystem.mapper;

import com.poSsystem.model.Category;
import com.poSsystem.model.Product;
import com.poSsystem.model.Store;
import com.poSsystem.payload.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product product){
       return ProductDto.builder()
               .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .image(product.getImage())
                .category(CategoryMapper.toDto(product.getCategory()))
                .storeId(product.getStore() != null ? product.getStore().getId() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
               .build();
    }

    public static Product toEntity(ProductDto productDto, Store store, Category category){
        return Product.builder()
                .name(productDto.getName())
                .store(store)
                .category(category)
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .brand(productDto.getBrand())
                .build();
    }
}
