package com.lld.order_ms.Dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private CategoryDTO category;
    private int availableQuantity;
}
