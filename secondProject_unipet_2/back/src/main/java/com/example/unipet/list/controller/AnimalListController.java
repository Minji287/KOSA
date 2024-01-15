package com.example.unipet.list.controller;

import com.example.unipet.list.domain.AnimalListDTO;
import com.example.unipet.list.service.AnimalListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/animals")
    public class AnimalListController {
    @Autowired
    private AnimalListService animalService;

    @GetMapping("/list")
    public ResponseEntity<Page<AnimalListDTO>> getRecentAnimalList(
            @RequestParam(required = false, defaultValue = "") String kind,
            Pageable pageable) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDateString = startDate.format(formatter);

        Page<AnimalListDTO> animalList = animalService.getFilteredAnimalList(kind, startDateString, pageable);
        return ResponseEntity.ok(animalList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnimalListDTO> getAnimalById(@PathVariable int id) {
        AnimalListDTO animalDetail = animalService.getAnimalById(id);
        if (animalDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalDetail);
    }
}
