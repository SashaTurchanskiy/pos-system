package com.poSsystem.controller;

import com.poSsystem.payload.dto.InventoryDto;
import com.poSsystem.payload.response.ApiResponse;
import com.poSsystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) throws Exception {
        inventoryService.createInventory(inventoryDto);
        return ok(inventoryDto);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<InventoryDto> updateInventory(@RequestBody InventoryDto inventoryDto,
                                                        @PathVariable Long id) throws Exception {
        return ok(inventoryService.updateInventory(id, inventoryDto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Inventory deleted successfully...");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDto>> getInventoryByBranch(@PathVariable Long branchId) throws Exception {
        List<InventoryDto> inventoryDtos = inventoryService.getAllInventoriesByBranchId(branchId);
        return ResponseEntity.ok(inventoryDtos);
    }
    @GetMapping("/product/{productId}/branch/{branchId}")
    public ResponseEntity<InventoryDto> getInventoryByProduct(@PathVariable Long productId,
                                                              @PathVariable Long branchId) throws Exception {
        inventoryService.getInventoryByProductIdAndBranchId(productId, branchId);
        return ResponseEntity.ok().build();
    }

}
