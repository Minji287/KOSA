package com.example.unipet.list.service;

import com.example.unipet.list.domain.AnimalListDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalDataParser {

    public List<AnimalListDTO> parseJsonResponse(String jsonResponse, LocalDate currentDate) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<AnimalListDTO> animalList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemNode = rootNode.path("response").path("body").path("items").path("item");

            for (JsonNode item : itemNode) {
                AnimalListDTO dto = objectMapper.treeToValue(item, AnimalListDTO.class);
                LocalDate itemDate = LocalDate.parse(dto.getNoticeEdt(), DateTimeFormatter.ofPattern("yyyyMMdd"));

                if ("보호중".equals(dto.getProcessState()) && itemDate.isAfter(currentDate)) {
                    animalList.add(dto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return animalList;
    }
}