package com.example.demo.data.repositories;

import com.example.demo.data.models.Inventory;
import com.example.demo.data.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
    Inventory findBySlug(String slug);
    void deleteBySlug(String slug);
}
