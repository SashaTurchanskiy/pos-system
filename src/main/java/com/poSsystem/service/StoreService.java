package com.poSsystem.service;

import com.poSsystem.domain.StoreStatus;
import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.StoreDto;

import java.util.List;

public interface StoreService {

    StoreDto createStore(StoreDto storeDto, User user);
    StoreDto getStoreById(Long id);
    List<StoreDto> getAllStores();
    Store getStoreByAdmin();
    StoreDto updateStore(Long id, StoreDto storeDto);
    void deleteStore(Long id);
    StoreDto getStoreByEmployee();
    StoreDto moderateStore(Long id, StoreStatus status);
}
