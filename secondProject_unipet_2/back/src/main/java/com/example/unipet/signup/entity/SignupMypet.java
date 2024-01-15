package com.example.unipet.signup.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "mypet")
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupMypet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;

    @NotNull
    private String userId;

    @NotNull
    private String petName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate petBirth;

    @NotNull
    private String petGender; // f or m

    @NotNull
    private String petKind;

    @NotNull
    private String petNeuter; // y or n

    private String petColor;
    private Double petWeight;
    private String petTrait;
}
