package com.backend.dream.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Voucher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String number;

    @Column(name = "createdate")
    @Temporal(TemporalType.DATE)
    private Date createDate = new Date();

    @Column(name = "expireddate")
    @Temporal(TemporalType.DATE)
    private Date expiredDate;

    private Double percent;

    private Double condition;

    private String icon;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    @JsonIgnore
    private Account account;

    @ManyToOne
    @JoinColumn(name = "idvoucherstatus")
    private VoucherStatus status;
}
