package com.backend.dream.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    private String name;

    private Double price;

    private String image;

    private String describe;

    Date createDate = new Date();

    private Boolean active;

    private String category;
}
