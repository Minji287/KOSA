package com.example.unipet.petinfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PetInfoController {

    @GetMapping(value = "/hospinfo")
    public ModelAndView getHospinfo() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title","동물 병원 리스트");
        mav.setViewName("petInfo");
        return mav;
    }

    @GetMapping(value = "/trailInfo")
    public ModelAndView getTrailinfo() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title","산책로 리스트");
        mav.setViewName("petInfo");
        return mav;
    }
}