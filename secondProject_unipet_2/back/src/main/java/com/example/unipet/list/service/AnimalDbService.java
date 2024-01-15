package com.example.unipet.list.service;

import com.example.unipet.list.converter.AnimalConverter;
import com.example.unipet.list.domain.AnimalListDTO;
import com.example.unipet.list.entity.Animal;
import com.example.unipet.list.repository.AnimalListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AnimalDbService {

    @Autowired
    private AnimalListRepository animalListRepository;

    @Autowired
    private AnimalConverter animalConverter;
    public void deleteExpiredAnimals(String endDate) {
        animalListRepository.deleteAllByNoticeEdtBefore(endDate);
    }

    public void insertNewAnimals(List<Animal> newAnimals) {
        if (newAnimals != null && !newAnimals.isEmpty()) {
            for (Animal animal : newAnimals) {
                // 이미 존재하는지 확인
                if (!animalListRepository.existsByNoticeNo(animal.getNoticeNo())) {
                    animalListRepository.save(animal);
                }
        }
    }
    }
    public AnimalListDTO findByImageId(int imageId) {
        Animal animal = animalListRepository.findByImageId(imageId);
        if (animal != null) {
            return animalConverter.convertToDTO(animal);
        }
        return null;
    }

    public List<Animal> getAnimalsAfterDate(LocalDate startDate) {
        return animalListRepository.findByHappenDtGreaterThanEqual(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
    public Animal findById(int id) {
        return animalListRepository.findById(id).orElse(null);
    }

    public void insertNewAnimal(Animal animal) {
        animalListRepository.save(animal);
    }

    public boolean isAnimalExist(long desertionNo) {
        return animalListRepository.findByDesertionNo(desertionNo) != null;
    }

    public Page<Animal> getFilteredAnimals(String kind, LocalDate startDate, Pageable pageable) {
        return animalListRepository.findByKindCdContainingAndHappenDtGreaterThanEqual(kind, startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")), pageable);


    }
}