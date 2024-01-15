package com.example.unipet.mypage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
public class Boards {
    @Id
    private int boardNo;
    private String userId;
    private int boardId;
    private String title;
    private String content;
    private LocalDate postingDate;
    private int views;
    private int likeCounting;
    private String imgPath;
}
