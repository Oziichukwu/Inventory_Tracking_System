package com.example.demo.services;

import com.example.demo.data.models.InventoryItem;
import com.example.demo.data.repositories.InventoryItemRepository;
import com.example.demo.data.repositories.InventoryRepository;
import com.example.demo.dtos.request.InventoryItemRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventoryServiceImplTest {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryItemService inventoryItemService;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    void setUp() {
        InventoryItemRequest inventoryItemRequest = new InventoryItemRequest();
        inventoryItemRequest.setPrice(new BigDecimal("200.00"));
        inventoryItemRequest.setDescription("Electronics");
        inventoryItemRequest.setQuantity(14L);
        inventoryItemRequest.setProductName("television set");

        InventoryItem inv1 = inventoryItemService.addItem(inventoryItemRequest);
        InventoryItem inv2 = inventoryItemService.addItem(inventoryItemRequest);

        InventoryItemRequest inventoryItemRequest2 = new InventoryItemRequest();
        inventoryItemRequest2.setPrice(new BigDecimal("2400.00"));
        inventoryItemRequest2.setDescription("Gadgets");
        inventoryItemRequest2.setQuantity(20L);
        inventoryItemRequest2.setProductName("Ipod");

        InventoryItem inv3 = inventoryItemService.addItem(inventoryItemRequest2);
    }

    @AfterEach
    void tearDown() {
        inventoryItemRepository.deleteAll();
        inventoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Test that Item can be deleted from the Inventory")
    void deleteItem() {
//        Given
        assertThat(inventoryRepository.findAll()).hasSize(2);
        assertThat(inventoryItemRepository.findAll()).hasSize(3);
//        When
        inventoryService.deleteItem("television set");
//        Assert
        assertThat(inventoryService.getAllInventories()).hasSize(1);
        assertThat(inventoryItemService.getAll()).hasSize(4);
    }

    @Test
    void writeToCsv() throws IOException {
        inventoryService.writeToCsv();
    }
}