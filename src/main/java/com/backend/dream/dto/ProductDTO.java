package com.backend.dream.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    private String name;

    private Double price;

    private String image;

    private String describe;

    private Boolean active;

    private String category;

    public String getFormattedPrice() {
        // Định dạng giá trị và thêm dấu ₫
        DecimalFormat df = new DecimalFormat("#,###₫");
        return df.format(price);
    }
}
