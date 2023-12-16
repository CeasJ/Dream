package com.backend.dream.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Long id;

    private String name;

    private Long id_discount;

    private String name_discount;

    private Double percent_discount;
}
