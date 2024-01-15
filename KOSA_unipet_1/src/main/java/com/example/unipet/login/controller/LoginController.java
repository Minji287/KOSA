package com.example.unipet.login.controller;


import com.example.unipet.login.dao.LoginMapper;
import com.example.unipet.login.model.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    LoginMapper dao;

    @GetMapping("/login")
    public String loginMain() {
        return "login";
    }

    @PostMapping("/login")
    public String loginReq(LoginDTO dto, HttpServletRequest httpServletRequest, Model model) {
        String id = dto.getId();
        String passWord = dto.getPassword();
        if (dao.checkId(id)) {
            if (dao.checkPassword(id, passWord)) {
                if(dao.checkRoles(id).equals("out")){
                    model.addAttribute("message", "탈퇴한 회원 입니다.");
                    return "login";
                }
                httpServletRequest.getSession().invalidate();
                HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성

                session.setAttribute("userId", id);
                session.setMaxInactiveInterval(1800);
                return "redirect:";
            } else {
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            }
        } else {
            model.addAttribute("message", "존재하지 않는 ID 입니다.");
        }
        return "login";
    }
    @GetMapping("/logout")
    public String logoutMain(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
