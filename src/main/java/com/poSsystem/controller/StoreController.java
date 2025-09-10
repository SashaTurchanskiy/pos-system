package com.poSsystem.controller;

import com.poSsystem.domain.StoreStatus;
import com.poSsystem.mapper.StoreMapper;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.StoreDto;
import com.poSsystem.payload.response.ApiResponse;
import com.poSsystem.service.StoreService;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto,
                                                @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto, user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StoreDto>> getAllStore(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoreByAdmin(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoreByEmployee(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StoreDto> updateStore(@PathVariable Long id,
                                                @RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }
    @PutMapping("/moderate/{id}")
    public ResponseEntity<StoreDto> moderateStore(@PathVariable Long id,
                                                  @RequestParam StoreStatus status){
        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted store with id " + id);
        return ResponseEntity.ok(apiResponse);
    }


}
