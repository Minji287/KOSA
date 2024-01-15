package com.example.unipet.list.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "picture")
@Data
@ToString
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;
    private String happenDt;
    private long desertionNo;
    private String popfile;
    private String processState;
    private String specialMark;
    private String happenPlace;
    private String careNm;
    private String kindCd;
    private String noticeSdt;
    private String noticeEdt;
    private String sexCd;
    private String age;
    private String colorCd;
    private String weight;
    private String neuterYn;
    private String careTel;
    private String careAddr;
    private String noticeNo;
}
