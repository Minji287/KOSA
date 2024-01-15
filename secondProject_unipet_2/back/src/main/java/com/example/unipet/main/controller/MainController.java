package com.example.unipet.main.controller;

import com.example.unipet.login.config.auth.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public void goMain(@AuthenticationPrincipal MyUserDetails userDetails) {
        System.out.println("이거맞아?"+ userDetails.getUser().getUserId());

    }

}
