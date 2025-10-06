package com.poSsystem.mapper;

import com.poSsystem.model.Branch;
import com.poSsystem.model.Inventory;
import com.poSsystem.model.Product;
import com.poSsystem.payload.dto.InventoryDto;

public class InventoryMapper {
    public static InventoryDto toDto(Inventory inventory){
        return InventoryDto.builder()
                .id(inventory.getId())
                .branchId(inventory.getBranch().getId())
                .branch(BranchMapper.toDTO(inventory.getBranch())) // додано
                .productId(inventory.getProduct().getId())
                .product(ProductMapper.toDto(inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .lastUpdated(inventory.getLastUpdated()) // додано
                .build();
    }
    public static Inventory toEntity(InventoryDto inventoryDto, Branch branch, Product product){
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(inventoryDto.getQuantity())
                .build();
    }
}
