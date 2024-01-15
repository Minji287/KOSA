package com.example.unipet.mypage.domain;

import lombok.Data;

@Data
public class UserResultDTO {
    private String user_id;
    private String password;
    private String email;
    private String name;
}
