package com.example.unipet.list.service;

import com.example.unipet.list.converter.AnimalConverter;
import com.example.unipet.list.domain.AnimalListDTO;
import com.example.unipet.list.entity.Animal;
import com.example.unipet.list.util.JsonParsingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class AnimalListService {

    @Autowired
    private AnimalApiService animalApiService;

    @Autowired
    private AnimalDbService animalDatabaseService;

    @Autowired
    private JsonParsingUtil jsonParsingUtil;

    @Autowired
    private AnimalConverter animalConverter;

    @Scheduled(fixedDelay = 10000000) // 166분 마다
    public void scheduledAnimalUpdate() {
        LocalDate today = LocalDate.now();
        String startDateString = today.minusDays(30).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endDateString = today.format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 이 부분 추가

        animalDatabaseService.deleteExpiredAnimals(endDateString);

        String jsonResponse = animalApiService.callAnimalApi(LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyyMMdd")), today);
        List<AnimalListDTO> newAnimals = jsonParsingUtil.parseJsonResponse(jsonResponse, today);

        newAnimals.stream()
                .map(animalConverter::convertToEntity)
                .filter(animal -> !animalDatabaseService.isAnimalExist(animal.getDesertionNo()))
                .forEach(animalDatabaseService::insertNewAnimal);
    }


    //이부분 추가한것
    public AnimalListDTO getAnimalById(int id) {
        Animal animal = animalDatabaseService.findById(id); // 수정된 부분
        if (animal != null) {
            return animalConverter.convertToDTO(animal); // 수정된 부분
        }
        return null;
    }


    public Page<AnimalListDTO> getFilteredAnimalList(String kind, String startDateString, Pageable pageable) {
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return animalDatabaseService.getFilteredAnimals(kind, startDate, pageable)
                .map(animalConverter::convertToDTO);
    }


}