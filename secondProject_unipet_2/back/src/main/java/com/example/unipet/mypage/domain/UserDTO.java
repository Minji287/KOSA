package com.example.unipet.mypage.domain;

import com.example.unipet.mypage.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private String userId;
    private String password;
    private String email;
    private String name;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String roles;
}
