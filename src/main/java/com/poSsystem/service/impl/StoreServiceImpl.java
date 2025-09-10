package com.poSsystem.service.impl;

import com.poSsystem.domain.StoreStatus;
import com.poSsystem.exceptions.UserException;
import com.poSsystem.mapper.StoreMapper;
import com.poSsystem.model.Store;
import com.poSsystem.model.StoreContact;
import com.poSsystem.model.User;
import com.poSsystem.payload.dto.StoreDto;
import com.poSsystem.repository.StoreRepository;
import com.poSsystem.service.StoreService;
import com.poSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {

        Store store = StoreMapper.toEntity(storeDto, user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store with id " + id + " not found"));
        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(StoreMapper::toDTO).toList();
    }

    @Override
    public Store getStoreByAdmin() {
        User admin = userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId())
                .orElseThrow(()-> new RuntimeException("Store for admin with id " + admin.getId() + " not found"));
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) {
        User currentUser = userService.getCurrentUser();

        Store existing = storeRepository.findByStoreAdminId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Store with id " + id + " not found"));

        existing.setBrand(storeDto.getBrand());
        existing.setDescription(storeDto.getDescription());

        if (storeDto.getStoreType() != null){
            existing.setStoreType(storeDto.getStoreType());
        }
        if (storeDto.getContact() != null){
            StoreContact storeContact  = StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();
            existing.setContact(storeContact);
        }
        Store updated = storeRepository.save(existing);
        return StoreMapper.toDTO(updated);
    }

    @Override
    public void deleteStore(Long id) {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDto getStoreByEmployee() {
        User employee = userService.getCurrentUser();
        if (employee == null){
            throw new UserException("U don`t have permission to do this operation");
        }
        return StoreMapper.toDTO(employee.getStore());
    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store with id " + id + " not found"));
        store.setStatus(status);
        Store updateStore = storeRepository.save(store);
        return StoreMapper.toDTO(updateStore);
    }
}
