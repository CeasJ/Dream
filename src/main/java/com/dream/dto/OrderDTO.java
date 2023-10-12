package com.dream.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDTO {
    private Long id;

    private String address;

    private String note;

    private Date createDate;

    // update later
    private String status;
}
