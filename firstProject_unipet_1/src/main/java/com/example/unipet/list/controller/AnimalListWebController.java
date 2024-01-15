package com.example.unipet.list.controller;

import com.example.unipet.list.domain.AnimalListDTO;
import com.example.unipet.list.service.AnimalListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AnimalListWebController {
    @Value("${MAPI_KEY}")
    private String API_KEY;

    @Autowired
    private AnimalListService animalService;

    @GetMapping("/animalList")
    public String getAnimalList(Model model) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30); // 최근 30일 이내의 데이터를 조회
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDateString = startDate.format(formatter);

        List<AnimalListDTO> animalList = animalService.getRecentAnimalList(startDateString);
        model.addAttribute("animalList", animalList);
        System.out.println(animalList);
        return "animalList";
    }


    @GetMapping("/animalDetail/{image_id}")
    public String animalDetail(@PathVariable int image_id, Model model) {
        if (image_id <= 0) {
            model.addAttribute("errorMessage", "유효하지 않은 동물 ID입니다.");
            return "errorPage"; // 오류 페이지로 리디렉션
        }

        AnimalListDTO animal = animalService.findByImage_Id(image_id);

        if (animal == null) {
            model.addAttribute("errorMessage", "동물 정보를 찾을 수 없습니다.");
            return "errorPage"; // 오류 페이지로 리디렉션
        }
        model.addAttribute("API_KEY", API_KEY);
        model.addAttribute("animal", animal);
        System.out.println(animal);
        return "animalDetail";
    }

}

