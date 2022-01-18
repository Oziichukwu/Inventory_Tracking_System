package com.example.demo.services;

import com.example.demo.data.models.Inventory;
import com.example.demo.data.repositories.InventoryRepository;
import com.example.demo.dtos.request.InventoryItemRequest;
import com.example.demo.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final InventoryItemService inventoryItemService;

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Override
    public void deleteItem(String itemName) {
        String slug = itemName.toLowerCase().replace(" ", "_");
        Inventory inventoryToDelete = inventoryRepository.findBySlug(slug);
        if(inventoryToDelete == null) throw new ApplicationException("No such Item");
        InventoryItemRequest inventoryItemRequest = new InventoryItemRequest();
        inventoryItemRequest.setQuantity(inventoryToDelete.getCurrentQuantity());
        inventoryItemRequest.setProductName(itemName);
        inventoryItemService.removeItem(inventoryItemRequest);
        inventoryRepository.deleteBySlug(slug);
    }

    @Override
    public void writeToCsv() throws IOException {

        File csvOutputFile = new File("inventory.csv");
        if (!csvOutputFile.exists()){
            boolean newFile = csvOutputFile.createNewFile();
        }

        try (PrintWriter pw = new PrintWriter(csvOutputFile)){
            inventoryRepository.findAll().stream()
                    .map(Inventory::toCsvRow)
                    .forEach(pw::println);
        }
    }
}
