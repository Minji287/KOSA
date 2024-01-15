package com.example.unipet.boards.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BoardDTO {
    private int boardNo;
//    private String userId; 서버에서 토큰 생성 후 엔터티 필드로 직접 전달
    private int boardId;
    private String title;
    private String content;
    private String postingDate;
    private int views;
    private int likeCounting;
    private String imgPath;
}
