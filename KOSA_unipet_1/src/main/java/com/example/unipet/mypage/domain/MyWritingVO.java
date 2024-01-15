package com.example.unipet.mypage.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class MyWritingVO {
    private int board_no;
    private int board_id;
    private String title;
    private LocalDate posting_date;
    private int num;
}
