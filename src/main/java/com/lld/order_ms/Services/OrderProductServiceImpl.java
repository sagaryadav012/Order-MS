package com.lld.order_ms.Services;

import com.lld.order_ms.Models.OrderProduct;
import com.lld.order_ms.Repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderProductServiceImpl implements OrderProductService{
    private OrderProductRepository orderProductRepository;

    @Autowired
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<OrderProduct> createOrder(List<OrderProduct> orderProductList) {
        return orderProductRepository.saveAll(orderProductList);
    }
}
