package com.backend.dream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherDTO {
    private Long id;

    private String name;

    private String number;

    private Date createDate;

    private Date expiredDate;

    private Double percent;

    private Double condition;

    private String icon;

    // will update later
    private Long id_account;

    private Long status;
}
