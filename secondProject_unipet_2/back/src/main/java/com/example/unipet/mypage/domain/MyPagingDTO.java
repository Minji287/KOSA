package com.example.unipet.mypage.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPagingDTO {
    private String userId;
    private int page;
    private int size;
    private int boardId;

}
