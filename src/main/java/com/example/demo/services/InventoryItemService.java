package com.example.demo.services;

import com.example.demo.data.models.InventoryItem;
import com.example.demo.dtos.request.InventoryItemRequest;

import java.util.List;

public interface InventoryItemService {

    InventoryItem addItem(InventoryItemRequest inventoryItemRequest);
    InventoryItem removeItem(InventoryItemRequest inventoryItemRequest);
    List<InventoryItem> getAll();
}
