package com.backend.dream.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FeedBackDTO {
    private Long id;

    private String note;

    private int rating;

    private Date createDate;

    private Long id_account;

    private Long id_product;
}
