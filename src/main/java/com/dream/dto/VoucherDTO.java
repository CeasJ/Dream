package com.dream.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VoucherDTO {
    private Long id;

    private String name;

    private Date createDate;

    private Date expiredDate;

    private Double percent;

    private Double condition;

    // will update later
    private Long id_account;

    private String status;
}
