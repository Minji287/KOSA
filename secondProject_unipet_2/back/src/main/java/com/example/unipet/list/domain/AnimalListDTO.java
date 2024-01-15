package com.example.unipet.list.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnimalListDTO {
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
/*
noticeNo, kindCd, processState, careNm, happenPlace, specialMark
 */