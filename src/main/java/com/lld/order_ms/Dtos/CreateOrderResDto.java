package com.lld.order_ms.Dtos;

import com.lld.order_ms.Models.Order;
import lombok.Data;

@Data
public class CreateOrderResDto {
    private Order order;
    private String message;
    private ResponseStatus responseStatus;
}
