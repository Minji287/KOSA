package com.example.unipet.list.converter;

import com.example.unipet.list.domain.AnimalListDTO;
import com.example.unipet.list.entity.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalConverter {

    public Animal convertToEntity(AnimalListDTO dto) {
        Animal animal = new Animal();
        animal.setHappenDt(dto.getHappenDt());
        animal.setImageId(dto.getImageId());
        animal.setDesertionNo(dto.getDesertionNo());
        animal.setPopfile(dto.getPopfile());
        animal.setProcessState(dto.getProcessState());
        animal.setSpecialMark(dto.getSpecialMark());
        animal.setHappenPlace(dto.getHappenPlace());
        animal.setCareNm(dto.getCareNm());
        animal.setKindCd(dto.getKindCd());
        animal.setNoticeSdt(dto.getNoticeSdt());
        animal.setNoticeEdt(dto.getNoticeEdt());
        animal.setSexCd(dto.getSexCd());
        animal.setAge(dto.getAge());
        animal.setColorCd(dto.getColorCd());
        animal.setWeight(dto.getWeight());
        animal.setNeuterYn(dto.getNeuterYn());
        animal.setCareTel(dto.getCareTel());
        animal.setCareAddr(dto.getCareAddr());
        animal.setNoticeNo(dto.getNoticeNo());
        return animal;
    }

    public AnimalListDTO convertToDTO(Animal animal) {
        AnimalListDTO dto = new AnimalListDTO();
        dto.setHappenDt(animal.getHappenDt());
        dto.setImageId(animal.getImageId());
        dto.setDesertionNo(animal.getDesertionNo());
        dto.setPopfile(animal.getPopfile());
        dto.setProcessState(animal.getProcessState());
        dto.setSpecialMark(animal.getSpecialMark());
        dto.setHappenPlace(animal.getHappenPlace());
        dto.setCareNm(animal.getCareNm());
        dto.setKindCd(animal.getKindCd());
        dto.setNoticeSdt(animal.getNoticeSdt());
        dto.setNoticeEdt(animal.getNoticeEdt());
        dto.setSexCd(animal.getSexCd());
        dto.setAge(animal.getAge());
        dto.setColorCd(animal.getColorCd());
        dto.setWeight(animal.getWeight());
        dto.setNeuterYn(animal.getNeuterYn());
        dto.setCareTel(animal.getCareTel());
        dto.setCareAddr(animal.getCareAddr());
        dto.setNoticeNo(animal.getNoticeNo());
        return dto;
    }

}