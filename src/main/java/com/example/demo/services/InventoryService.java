package com.example.demo.services;

import com.example.demo.data.models.Inventory;
import com.example.demo.data.models.Inventory;

import java.io.IOException;
import java.util.List;

public interface InventoryService {

    List<Inventory> getAllInventories();
    void deleteItem(String itemName);
    void writeToCsv() throws IOException;
}
