package com.example.unipet.mypage.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypetDTO {
    private int petId;
    private String userId;
    private String petName;
    private LocalDate petBirth;
    private String petGender; // f or m
    private String petKind;
    private String petNeuter; // y or n
    private String petPic;
    private String petColor;
    private Double petWeight;
    private String petTrait;

    private MultipartFile attachFile;


}
