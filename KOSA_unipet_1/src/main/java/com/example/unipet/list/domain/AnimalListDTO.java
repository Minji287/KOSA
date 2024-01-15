package com.example.unipet.list.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AnimalListDTO {
    private int image_id; // image_id 필드를 추가
    private String happenDt; // upload_at 게시일 (완)
    private long desertionNo;
    private String popfile; // 이미지파일url (완)
    private String processState; // 보호중 or 종료와 같은 상태표기(완)
    private String specialMark; // 특징(완)
    private String happenPlace; // 유기견 발견 장소 (완)
    private String careNm; // 보호소명 (완)
    private String kindCd; // 품종 (완)
    private String noticeSdt;  // 공고 시작 일자 (완)
    private String noticeEdt;  // 공고 시작 일자 (완)
    private String sexCd; // 공고 마감 일자 (완)
    private String age; // 공고 마감 일자 (완)
    private String colorCd; // 공고 마감 일자 (완)
    private String weight; // 공고 마감 일자 (완)
    private String neuterYn; // 공고 마감 일자 (완)
    private String careTel; // 공고 마감 일자 (완)
    private String careAddr; // 공고 마감 일자 (완)
    private String noticeNo;


}
