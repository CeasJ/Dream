package com.backend.dream.dto;

import com.backend.dream.entity.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    private Long id;

    private String name;

    private Double price;

    private String image;

    private String describe;


    private Boolean active;

    private String category;
    private Long id_category;
    private Date createDate = new Date();
    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("#,###₫");
        return df.format(price);
    }

    private List<SizeDTO> availableSizes;

    private Size selectedSize;

}
