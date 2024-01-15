package com.example.unipet.main.controller;

import com.example.unipet.main.dao.MainMapper;
import com.example.unipet.signup.dao.SignupMapper;
import com.example.unipet.signup.model.SignupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String goMain() {
        return "main";
    }

}
