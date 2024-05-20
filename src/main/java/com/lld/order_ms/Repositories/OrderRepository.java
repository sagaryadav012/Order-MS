package com.lld.order_ms.Repositories;

import com.lld.order_ms.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
   // String query = "select from order o join orders_order_products oop on o.id == oop.orders_id";

}
