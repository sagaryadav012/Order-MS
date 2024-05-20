package com.lld.order_ms.Services;

import com.lld.order_ms.Exceptions.ProductNotFoundException;
import com.lld.order_ms.Models.Order;
import com.lld.order_ms.Models.OrderProduct;

import java.util.List;

public interface OrderService {
    Order createOrder(long userId, List<OrderProduct> orderProductList) throws ProductNotFoundException;
}
