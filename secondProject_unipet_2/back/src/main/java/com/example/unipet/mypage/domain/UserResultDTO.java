package com.example.unipet.mypage.domain;

import lombok.*;

@Getter
@Builder
public class UserResultDTO {
    private String userId;
    private String email;
    private String name;
    private String petPic;
}
