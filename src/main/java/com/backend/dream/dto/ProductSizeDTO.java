package com.backend.dream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSizeDTO {
    private Long id;

    private Long id_product;

    private Long id_size;

    private String name_size;

    private Double price;

    private String image;

    private String name;

    private Double discount_percent;

}
