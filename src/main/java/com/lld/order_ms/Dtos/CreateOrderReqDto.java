package com.lld.order_ms.Dtos;

import com.lld.order_ms.Models.OrderProduct;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderReqDto {
    private long userId;
    private List<OrderProduct> orderProducts;
}
