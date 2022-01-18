package com.example.demo.services;

import com.example.demo.data.models.Inventory;
import com.example.demo.data.models.InventoryItem;
import com.example.demo.data.models.Operation;
import com.example.demo.data.repositories.InventoryItemRepository;
import com.example.demo.data.repositories.InventoryRepository;
import com.example.demo.dtos.request.InventoryItemRequest;
import com.example.demo.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventoryItemServiceImpl implements InventoryItemService {


    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryItem addItem(InventoryItemRequest inventoryItemRequest) {
        String slug = inventoryItemRequest.getProductName().replace(" ", "_").toLowerCase();

        Inventory inventory = inventoryRepository.findBySlug(slug);
        if(inventory == null){
            inventory = new Inventory();
            inventory.setSlug(slug);
            inventory.setCurrentQuantity(0L);
        }
        inventory.setCurrentQuantity(inventory.getCurrentQuantity() + inventoryItemRequest.getQuantity());
        inventory.setTimeOfEntry(LocalDateTime.now());
        inventoryRepository.save(inventory);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setProductName(inventoryItemRequest.getProductName());
        inventoryItem.setPrice(inventoryItemRequest.getPrice());
        inventoryItem.setDescription(inventoryItemRequest.getDescription());
        inventoryItem.setQuantity(inventoryItemRequest.getQuantity());
        inventoryItem.setOperation(Operation.ADD);
        inventoryItemRepository.save(inventoryItem);
        return inventoryItem;
    }

    @Override
    public InventoryItem removeItem(InventoryItemRequest inventoryItemRequest) {
        String slug = inventoryItemRequest.getProductName().replace(" ", "_").toLowerCase();
        Inventory inventory = inventoryRepository.findBySlug(slug);
        if(inventory == null){
            throw new ApplicationException("Item does not exist");
        }
        if(inventory.getCurrentQuantity() < inventoryItemRequest.getQuantity()){
            throw new ApplicationException("The Item is low in stock only " + inventory.getCurrentQuantity() + " is remaining");
        }

        inventory.setCurrentQuantity(inventory.getCurrentQuantity() - inventoryItemRequest.getQuantity());
        inventoryRepository.save(inventory);

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setProductName(inventoryItemRequest.getProductName());
        inventoryItem.setPrice(inventoryItemRequest.getPrice());
        inventoryItem.setDescription(inventoryItemRequest.getDescription());
        inventoryItem.setQuantity(inventoryItemRequest.getQuantity());
        inventoryItem.setOperation(Operation.REMOVE);
        inventoryItemRepository.save(inventoryItem);
        return inventoryItem;
    }

    @Override
    public List<InventoryItem> getAll() {
        return inventoryItemRepository.findAll();
    }
}
