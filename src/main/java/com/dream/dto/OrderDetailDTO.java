package com.dream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private Long id;

    private int quantity;

    private Long id_order;

    private Long id_product;
}
