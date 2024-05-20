package com.lld.order_ms.Dtos;

import lombok.Data;

@Data
public class UpdateQuantityReqDTO {
    private long id;
    private int quantity;
}