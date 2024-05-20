package com.lld.order_ms.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class RequestProductIdsDTO {
    private List<Long> productIds;
}
