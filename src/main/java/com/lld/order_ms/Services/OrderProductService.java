package com.lld.order_ms.Services;

import com.lld.order_ms.Models.OrderProduct;

import java.util.List;

public interface OrderProductService {
    List<OrderProduct> createOrder(List<OrderProduct> orderProductList);
}
