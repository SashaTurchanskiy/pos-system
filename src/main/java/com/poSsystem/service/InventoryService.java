package com.poSsystem.service;

import com.poSsystem.model.Inventory;
import com.poSsystem.payload.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto) throws Exception;
    InventoryDto updateInventory(Long id, InventoryDto inventoryDto) throws Exception;
    void deleteInventory(Long id) throws Exception;
    InventoryDto getInventoryById(Long id) throws Exception;
    InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) throws Exception;
    List<InventoryDto> getAllInventoriesByBranchId(Long branchId) throws Exception;

}
