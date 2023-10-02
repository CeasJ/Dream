package com.backend.dream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private Long id;

    private String avatar;

    private String firstname;

    private String lastname;

    private String fullname;

    private String phone;

    private Long id_account;
}
