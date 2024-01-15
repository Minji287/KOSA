package com.example.unipet.login.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user")
@Setter
@Getter
@ToString
public class LoginUser {

    public LoginUser(){
        this.createdAt = LocalDate.now();
        this.modifiedAt = LocalDate.now();
    }

    @Id
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private String email;

    private LocalDate createdAt;
    private LocalDate modifiedAt;


    public List<String> getRoleList(){
        if(!this.roles.isEmpty()){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}