package com.poSsystem.mapper;

import com.poSsystem.model.Store;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.StoreDto;

public class StoreMapper {
    // Implement mapping methods between Store and StoreDto here
    public static StoreDto toDTO(Store store){
        StoreDto storeDto = new StoreDto();
        storeDto.setId(store.getId());
        storeDto.setBrand(store.getBrand());
        storeDto.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
        storeDto.setCreatedAt(store.getCreatedAt());
        storeDto.setUpdatedAt(store.getUpdatedAt());
        storeDto.setDescription(store.getDescription());
        storeDto.setStoreType(store.getStoreType());
        storeDto.setStatus(store.getStatus());
        storeDto.setContact(store.getContact());

        return storeDto;
    }
    public static Store toEntity(StoreDto storeDto, User storeAdmin){
         Store store = new Store();
         store.setId(storeDto.getId());
         store.setBrand(storeDto.getBrand());
         store.setStoreAdmin(storeAdmin);
         store.setDescription(storeDto.getDescription());
         store.setStoreType(storeDto.getStoreType());
         store.setContact(storeDto.getContact());
         store.setCreatedAt(storeDto.getCreatedAt());
         store.setUpdatedAt(storeDto.getUpdatedAt());

         return store;
    }
}
