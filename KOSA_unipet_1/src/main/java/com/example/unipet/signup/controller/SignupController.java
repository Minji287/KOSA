package com.example.unipet.signup.controller;

import com.example.unipet.login.dao.LoginMapper;
import com.example.unipet.signup.dao.SignupMapper;
import com.example.unipet.signup.model.SignupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class SignupController {
    @Autowired
    SignupMapper dao;
    @Autowired
    LoginMapper loginDao;

    @GetMapping("/signup")
    public String signupMain() {
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView signupReq(SignupDTO dto) {
        ModelAndView mav = new ModelAndView();
        if(!validationDto(dto, mav)){
            mav.setViewName("signup");
            return mav;
        }
        if(dao.insertUserInfo(dto)){
            if(dto.getHavePet().equals("true")){
                if(dao.insertPetInfo(dto)){
                    mav.setViewName("login");
                }else{
                    mav.addObject("message", "펫 정보 저장에 실패하였습니다.");
                    mav.setViewName("signup");
                }
                return mav;
            }
            mav.setViewName("login");
        }else{
            mav.addObject("message", "회원 등록에 실패하였습니다.");
            mav.setViewName("signup");
        }
        return mav;
    }

    public boolean validationDto(SignupDTO dto, ModelAndView mav) {
        if(dto.getUser_id().isEmpty()){
            mav.addObject("message", "아이디를 입력해주세요.");
            return false;
        }
        if(loginDao.checkId(dto.getUser_id())){
            mav.addObject("message", "아이디가 중복됩니다.");
            return false;
        }
        if (dto.getPassword().isEmpty()) {
            mav.addObject("message", "패스워드를 입력해주세요.");
            return false;
        }
        if (dto.getRePassword().isEmpty()) {
            mav.addObject("message", "패스워드 확인을 입력해주세요.");
            return false;
        }
        if(!dto.getPassword().equals(dto.getRePassword())){
            mav.addObject("message", "패스워드가 일치하지 않습니다.");
            return false;
        }
        if (dto.getEmail().isEmpty())  {
            mav.addObject("message", "이메일을 입력해주세요.");
            return false;
        }
        if (dto.getName().isEmpty()) {
            mav.addObject("message", "이름을 입력해주세요.");
            return false;
        }
        if (dto.getHavePet().equals("true")) {
            if (dto.getPetName().isEmpty()) {
                mav.addObject("message", "펫 이름을 입력해주세요.");
                return false;
            }
            if (dto.getPetBirthday().isEmpty()) {
                mav.addObject("message", "펫 생일을 입력해주세요.");
                return false;
            }
            if (dto.getPetGender().isEmpty()) {
                mav.addObject("message", "펫 성별을 입력해주세요.");
                return false;
            }
            if (dto.getPetType().isEmpty()) {
                mav.addObject("message", "펫 종류을 입력해주세요.");
                return false;
            }
            if (dto.getDoNeutering() == null) {
                mav.addObject("message", "펫 중성화여부를 입력해주세요.");
                return false;
            }
            if (dto.getPetColor().isEmpty()) {
                mav.addObject("message", "펫 색상을 입력해주세요.");
                return false;
            }
            if (dto.getPetChar().isEmpty()) {
                mav.addObject("message", "펫 특징을 입력해주세요.");
                return false;
            }
        }
        return true;
    }
}
