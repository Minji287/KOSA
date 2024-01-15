package com.example.unipet.list.service;

import com.example.unipet.list.dao.AnimalMapper;
import com.example.unipet.list.domain.AnimalListDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AnimalListService {

   @Value("${LIST_API_KEY}")
   private String LIST_API_KEY;
// ----------------------------------------------

    // insert() api 호출해서 db에 저장하는거 -(1)
    // update() db 업데이트 하기 (최신화 &       수정사항 적용)-(2)
    // delete() 종료일이 지난 api 삭제하기 -(3)
    //  ----------------------------- AnimalLIstService

    // read db에 저장되어 있는 api 정보 가져오기
    public List<AnimalListDTO> getRecentAnimalList(String startDate) {
        // 종료일이 지난 동물 정보 삭제
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String endDateString = today.format(formatter);
        deleteExpiredAnimals(endDateString);

        // 기존 DB에 있는 동물 데이터 가져오기
        List<AnimalListDTO> existingAnimalsInDB = animalMapper.selectAllAnimals(startDate);

        // API에서 데이터를 가져오는 로직을 구현
        String jsonResponse = allowBasic();

        LocalDate currentDate = LocalDate.now();
        List<AnimalListDTO> allRecentAnimals = parseJsonResponse(jsonResponse, currentDate);

        // 사이즈로 동물 정보로 갯수 제한
        if (allRecentAnimals.size() > 20) {
            allRecentAnimals = allRecentAnimals.subList(0, 20);
        }

        // 중복 데이터 확인 및 최근 데이터만 선택
        List<AnimalListDTO> newAnimals = new ArrayList<>();

        for (AnimalListDTO animal : allRecentAnimals) {
            boolean isDuplicate = false;
            for (AnimalListDTO existingAnimal : existingAnimalsInDB) {
                if (animal.getDesertionNo() == existingAnimal.getDesertionNo()) {
                    isDuplicate = true;
                }
            }
            if (!isDuplicate) {
                newAnimals.add(animal);
            }
        }

        // 중복 데이터가 없는 경우에만 insertNewAnimals 메서드를 호출하여 새로운 동물 데이터를 DB에 삽입
        if (!newAnimals.isEmpty()) {
            insertNewAnimals(newAnimals);
        }

        // 기존 데이터와 새로운 데이터를 합침
        existingAnimalsInDB.addAll(newAnimals);
        System.out.println(existingAnimalsInDB);
        return existingAnimalsInDB;
    }

    @Autowired
    private AnimalMapper animalMapper;

    public void insertNewAnimals(List<AnimalListDTO> newAnimals) {
        if (newAnimals != null && !newAnimals.isEmpty()) {


            for (AnimalListDTO animal : newAnimals) {
                if (!isAnimalExist(String.valueOf(animal.getDesertionNo()))) {
                    animalMapper.insertAnimals(Collections.singletonList(animal));

                }
            }
        }
    }

    public void deleteExpiredAnimals(String endDate) {
        animalMapper.deleteExpiredAnimals(endDate);
    }

    public AnimalListDTO findByImage_Id(int image_id) {
        // ID를 기반으로 동물 정보를 데이터베이스에서 조회
        return animalMapper.selectByImageNo(Integer.parseInt(String.valueOf(image_id)));
    }

    // `notice_no`가 DB에 존재하는지 확인
    private boolean isAnimalExist(String noticeNo) {
        try {
            int noticeNoInt = Integer.parseInt(noticeNo);
            AnimalListDTO existingAnimal = animalMapper.selectByImageNo(noticeNoInt);
            return existingAnimal != null;
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 경우 처리
            return false;
        }
    }

    public String allowBasic() {
        StringBuilder result = new StringBuilder();
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic");
            urlBuilder.append("?" + URLEncoder.encode("bgnde", "UTF-8") + "=" + URLEncoder.encode("20231001", "UTF-8")); // 시작일
            urlBuilder.append("&" + URLEncoder.encode("endde", "UTF-8") + "=" + URLEncoder.encode("20231030", "UTF-8")); // 종료일
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 페이지 번호
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8")); // 페이지당 보여줄 갯수
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + LIST_API_KEY);

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public List<AnimalListDTO> parseJsonResponse(String jsonResponse, LocalDate currentDate) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            // JSON 데이터 "item" 배열 가져옴.
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemNode = rootNode.path("response").path("body").path("items").path("item");

            List<AnimalListDTO> animalList = new ArrayList<>();

            for (JsonNode item : itemNode) {
                AnimalListDTO dto = objectMapper.treeToValue(item, AnimalListDTO.class);
                // "보호중"인 동물만 데이터베이스에 저장

                LocalDate itemDate = LocalDate.parse(dto.getNoticeEdt(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                if ("보호중".equals(dto.getProcessState()) && itemDate.isAfter(currentDate)) {
                    animalList.add(dto);
                }
            }
            System.out.println(animalList);
            return animalList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
