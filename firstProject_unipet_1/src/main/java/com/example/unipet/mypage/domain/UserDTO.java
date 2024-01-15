package com.example.unipet.mypage.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String user_id;
    private String password;
    private String email;
    private String name;
    private LocalDate created_at;
    private LocalDate modified_at;
    private String roles;
}
