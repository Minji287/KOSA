package com.example.unipet.boards.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class CommentDTO {
    private int comment_id;
    private String user_id;
    private int board_no;
    private String content;
    private String comment_date;
    private String petPicUrl;
}
