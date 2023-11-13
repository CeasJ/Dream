package com.backend.dream.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="feedback")
public class FeedBack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;

    private int rating;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;

    @Column(name = "createdate")
    private Date createDate;


    @Column(name = "createtime")
    private Time createTime;

}
