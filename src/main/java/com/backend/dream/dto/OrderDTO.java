package com.backend.dream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private Long id;

    private String address;

    private String note;

    private Date createDate;

    private Long status;

    private int id_account;

    private String fullname;
}
