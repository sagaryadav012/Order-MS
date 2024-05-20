package com.lld.order_ms.Controllers;

import com.lld.order_ms.Dtos.CreateOrderReqDto;
import com.lld.order_ms.Dtos.CreateOrderResDto;
import com.lld.order_ms.Dtos.ResponseStatus;
import com.lld.order_ms.Models.Order;
import com.lld.order_ms.Models.OrderProduct;
import com.lld.order_ms.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public CreateOrderResDto createOrder(@RequestBody CreateOrderReqDto reqDto){
        CreateOrderResDto orderResDTO = new CreateOrderResDto();
        long userId = reqDto.getUserId();
        List<OrderProduct> orderProducts = reqDto.getOrderProducts();
        try {
            Order order = this.orderService.createOrder(userId, orderProducts);
            orderResDTO.setOrder(order);
            orderResDTO.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            orderResDTO.setMessage(e.getMessage());
            orderResDTO.setResponseStatus(ResponseStatus.FAILED);
        }
        return orderResDTO;
    }
}
