package com.lld.order_ms.Dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class CategoryDTO {
    private long id;
    private String name;
}
