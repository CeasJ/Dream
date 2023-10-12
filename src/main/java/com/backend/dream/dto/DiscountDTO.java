package com.backend.dream.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiscountDTO {
    private Long id;

    private String name;

    private String number;

    private boolean active;

    private Double percent;

    private Date createDate;

    private Date expiredDate;

    private Long id_product;
}
