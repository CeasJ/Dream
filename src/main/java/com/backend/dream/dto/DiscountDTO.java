package com.backend.dream.dto;

import com.backend.dream.entity.Discount;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiscountDTO {
    private Long id;

    private String name;

    private String number;

    private Double percent;

    private Date activeDate=new Date();

    private Date expiredDate = new Date();

    private Long idcategory;

    private boolean active;


}
