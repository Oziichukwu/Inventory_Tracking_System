package com.example.demo.data.models;

import com.example.demo.entityListener.InventoryItemListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@EntityListeners(value = InventoryItemListener.class)
@Entity
public class InventoryItem {

    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    private BigDecimal price;
    private String slug;
    private Long quantity;
    private String description;
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private Inventory inventory;

    @Enumerated(EnumType.ORDINAL)
    private Operation operation;

    @PrePersist
    public void saveSlug(){
        this.slug = this.productName.toLowerCase().replace(" ", "_");
    }
}
