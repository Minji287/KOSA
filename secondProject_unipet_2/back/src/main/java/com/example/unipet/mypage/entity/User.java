package com.example.unipet.mypage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    @Id
    private String userId;
    private String password;
    private String email;
    private String name;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String roles;

}
