package com.backend.dream.entity;

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
public class Discount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discountname")
    private String name;

    @Column(name = "discountnumber")
    private String number;

    @Column(name = "discountpercent")
    private Double percent;

    @Column(name = "activedate")
    private Date activeDate;

    @Column(name = "expireddate")
    private Date expiredDate;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;

}
