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

    private boolean active;

    @Column(name = "activedate")
    @Temporal(TemporalType.DATE)
    private Date activeDate=new Date();

    @Column(name = "expireddate")
    @Temporal(TemporalType.DATE)
    private Date expiredDate=new Date();

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "idproduct")
//    private Product product;

}
