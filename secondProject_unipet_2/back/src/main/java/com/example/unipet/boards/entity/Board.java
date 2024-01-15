package com.example.unipet.boards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "boards")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNo;

    private String userId;

    // 게시판 유형
    private int boardId;

    private String title;
    private String content;


    @Column( nullable = false)
    private ZonedDateTime postingDate;

    @PrePersist
    public void prePersist() {
        this.postingDate = ZonedDateTime.now();
    }

    private int views;

    private int likeCounting;

    private String imgPath;

    //Board 엔터티가 삭제될 때 연결된 Comment 엔터티도 함께 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
