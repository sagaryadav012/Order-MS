package com.lld.order_ms.Dtos;

import lombok.Data;

@Data
public class UpdateQuantityResDTO {
    private ProductDTO productDTO;
    private String message;
    private ResponseStatus responseStatus;
}
