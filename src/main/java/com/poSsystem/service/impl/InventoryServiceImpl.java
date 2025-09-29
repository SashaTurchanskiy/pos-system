package com.poSsystem.service.impl;

import com.poSsystem.mapper.InventoryMapper;
import com.poSsystem.model.Branch;
import com.poSsystem.model.Inventory;
import com.poSsystem.model.Product;
import com.poSsystem.payload.dto.InventoryDto;
import com.poSsystem.repository.BranchRepository;
import com.poSsystem.repository.InventoryRepository;
import com.poSsystem.repository.ProductRepository;
import com.poSsystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {

        Branch branch = branchRepository.findById(inventoryDto.getId()).orElseThrow(
                ()-> new Exception("Branch not found")
        );
        Product product = productRepository.findById(inventoryDto.getProductId()).orElseThrow(
                ()-> new Exception("Product not found")
        );
        Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch, product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found...")
        );
        inventory.setQuantity(inventoryDto.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found..."));
        inventoryRepository.delete(inventory);

    }

    @Override
    public InventoryDto getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found...")
        );
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) throws Exception {
        Inventory inventory = inventoryRepository.findByBranchIdAndProductId(branchId, productId).orElseThrow(
                ()-> new Exception("Inventory not found...")
        );
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventoriesByBranchId(Long branchId) throws Exception {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
        if (inventories == null || inventories.isEmpty()) {
            throw new Exception("Inventories not found...");
        }
        return inventories.stream()
                .map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
