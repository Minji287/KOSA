package com.example.unipet.mypage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class EmailDTO {
    private String userId;
    private String email;
    private String domain;

}
