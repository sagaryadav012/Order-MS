package com.lld.order_ms.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderProduct extends BaseModel{
    private long product_id;
    private int quantity;
}
