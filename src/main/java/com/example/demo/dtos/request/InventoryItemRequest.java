package com.example.demo.dtos.request;

import com.example.demo.data.models.Operation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventoryItemRequest {

    private String productName;

    private Long quantity;

    private BigDecimal price;

    private String description;

    private Operation operation;
}
