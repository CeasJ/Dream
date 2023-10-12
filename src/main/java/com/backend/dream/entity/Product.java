package com.backend.dream.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String image;

    private String describe;

    Date createDate = new Date();

    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> detail;

    @JsonIgnore
    @OneToMany(mappedBy= "product")
    private List<ProductSize> size;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Discount> discount;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<FeedBack> feedback;

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;


}
