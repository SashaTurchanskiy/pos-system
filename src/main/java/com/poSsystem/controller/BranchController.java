package com.poSsystem.controller;

import com.poSsystem.payload.dto.BranchDto;
import com.poSsystem.payload.response.ApiResponse;
import com.poSsystem.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/create")
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto){
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return ResponseEntity.ok(createdBranch);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id){
        BranchDto branchDto = branchService.getBranchById(id);
        return ResponseEntity.ok(branchDto);
    }
    @GetMapping("/store/{storeId}")
    public ResponseEntity<BranchDto> getBranchByStoreId(@PathVariable Long storeId){
        BranchDto branchDto = branchService.getBranchById(storeId);
        return ResponseEntity.ok(branchDto);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto){
        BranchDto updatedBranch = branchService.updateBranch(id, branchDto);
        return ResponseEntity.ok(updatedBranch);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(@PathVariable Long id){
        branchService.deleteBranch(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Branch deleted successfully");
        return ResponseEntity.ok(response);
    }

}
