package com.example.unipet.signup.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class SignupDTO {
    private String userId;
    private String password;
    private String rePassword;
    private String email;
    private String name;
    private String roles;

    private String havePet;
    private String petName;
    private LocalDate petBirth;
    private String petGender;
    private String petKind;
    private String petNeuter;
    private String petColor;
    private double petWeight;
    private String petTrait;
}
