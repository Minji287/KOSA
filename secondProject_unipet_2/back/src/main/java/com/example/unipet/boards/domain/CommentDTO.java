package com.example.unipet.boards.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@ToString
public class CommentDTO {
    private int commentId;
    private String userId;
    private int boardNo;
    private String content;
    private LocalDateTime commentDate;
}
