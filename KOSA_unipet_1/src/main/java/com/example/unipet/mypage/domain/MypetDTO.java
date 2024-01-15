package com.example.unipet.mypage.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MypetDTO {
    private int pet_id;
    private String user_id;
    private String pet_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pet_birth;
    private String pet_gender; // f or m
    private String pet_kind;
    private String pet_neuter; // y or n
    private String pet_pic;
    private String pet_color;
    private String pet_weight;
    private String pet_trait;

    private MultipartFile attachFile;
}
