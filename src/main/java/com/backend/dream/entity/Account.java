package com.backend.dream.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    private List<Authority> authority;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Voucher> voucher;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private  List<Order> order;

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Token> token;

    @JsonIgnore
    @OneToMany(mappedBy= "account")
    private List<FeedBack> feedback;


}
