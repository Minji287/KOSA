package com.example.unipet.boards.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BoardDTO {
    int board_no;
    String user_id;
    int board_id;
    String title;
    String content;
    String posting_date;
    int views;
    int like_counting;
    String img_path;
}
