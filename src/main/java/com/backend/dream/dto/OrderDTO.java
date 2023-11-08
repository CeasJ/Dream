package com.backend.dream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private Long id;

    private String address;

    private String note;

    private Date createDate;

    private Time createTime;

    private Long status;

    private int id_account;

    private String fullname;

    private List<OrderDetailDTO> orderDetailsDTO;

    private Double totalAmount;
    public String getFormattedPrice() {
        if(totalAmount != null){
        DecimalFormat df = new DecimalFormat("#,###₫");
        return df.format(totalAmount);
        } else {
            return "";
        }
    }
}
