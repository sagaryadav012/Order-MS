package com.lld.order_ms.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "orders")
public class Order extends BaseModel{
    private long userId;
    private Date orderedDate;
    private double amount;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany
    private List<OrderProduct> orderProducts;
}
