package com.poSsystem.payload.dto;

import com.poSsystem.model.Branch;
import com.poSsystem.model.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {


    private Long id;

    private BranchDto branch;

    private Long branchId;
    private Long productId;

    private ProductDto product;

    private Integer quantity;
    private LocalDateTime lastUpdated;
}
