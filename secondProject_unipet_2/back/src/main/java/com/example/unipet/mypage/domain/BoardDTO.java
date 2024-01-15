package com.example.unipet.mypage.domain;

import com.example.unipet.mypage.entity.Boards;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDTO {
    private int boardNo;
    private int boardId;
    private String title;
    private String content;
    private LocalDate postingDate;
    private String imgPath;

    public static BoardDTO toBoardDTO(Boards boards) {
        return new BoardDTOBuilder()
                .boardNo(boards.getBoardNo())
                .boardId(boards.getBoardId())
                .title(boards.getTitle())
                .content(boards.getContent())
                .postingDate(boards.getPostingDate())
                .imgPath(boards.getImgPath())
                .build();
    }
}
