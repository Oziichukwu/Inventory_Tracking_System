package com.example.demo.data.repositories;

import com.example.demo.data.models.InventoryItem;
import com.example.logistictrackingapp.data.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findBySlug(String slug);

    @Query("select max(inv.quantity) from InventoryItem inv where inv.slug = ?1")
    int getMax(String slug);
    @Query("select min(inv.quantity) from InventoryItem inv where inv.slug = ?1")
    int getMin(String slug);
    @Query("select avg(inv.quantity) from InventoryItem inv where inv.slug = ?1")
    int getAvg(String slug);
    @Query("select sum(inv.quantity) from InventoryItem inv where inv.slug = ?1")
    int getSum(String slug);

}
