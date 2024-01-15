package com.example.unipet.list.controller;

import com.example.unipet.list.domain.AnimalListDTO;
import com.example.unipet.list.service.AnimalDbService;
import com.example.unipet.list.service.AnimalListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AnimalListWebController {
    @Value("${MAPI_KEY}")
    private String API_KEY;

    @Autowired
    private AnimalDbService animalDbService;

    @Autowired
    private AnimalListService animalListService; // AnimalListService 인스턴스 추가

    @GetMapping("/animalList")
    public String getAnimalList(
            @RequestParam(required = false, defaultValue = "") String kind,
            Model model,
            Pageable pageable) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDateString = startDate.format(formatter);

        Page<AnimalListDTO> animalList = animalListService.getFilteredAnimalList(kind, startDateString, pageable);
        model.addAttribute("animalList", animalList);
        return "animalList";
    }





    @GetMapping("/animalDetail/{imageId}")
    public String animalDetail(@PathVariable int imageId, Model model) {
        if (imageId <= 0) {
            model.addAttribute("errorMessage", "유효하지 않은 동물 ID입니다.");
            return "errorPage";
        }

        AnimalListDTO animal = animalDbService.findByImageId(imageId);
        if (animal == null) {
            model.addAttribute("errorMessage", "동물 정보를 찾을 수 없습니다.");
            return "errorPage";
        }
        model.addAttribute("API_KEY", API_KEY);
        model.addAttribute("animal", animal);
        return "animalDetail";
    }

}

