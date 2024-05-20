package com.lld.order_ms.Repositories;

import com.lld.order_ms.Models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
