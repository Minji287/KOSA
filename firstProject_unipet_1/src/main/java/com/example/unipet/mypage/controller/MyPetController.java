package com.example.unipet.mypage.controller;

import com.example.unipet.mypage.dao.MyPetMapper;
import com.example.unipet.mypage.domain.MypetDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"userId", "myname"})
public class MyPetController {
    @Autowired
    MyPetMapper dao;

    @Autowired
    private HttpSession session;

    // 펫 조회
    @RequestMapping(value = "/mypet")
    public String mypet(Model model, @ModelAttribute("userId") String userId) {
        MypetDTO pet = dao.showMyPet(userId);

        if(pet == null) {
            model.addAttribute("nopet", "등록된 펫이 없습니다.");
        } else {
            // 년, 개월수 계산해서 전달
            LocalDate now = LocalDate.now();
            LocalDate petBirth = pet.getPet_birth();

            long years = ChronoUnit.YEARS.between(petBirth, now);
            long months = ChronoUnit.MONTHS.between(petBirth.plusYears(years), now);

            String age = "(";
            if(years == 0) {
                age += months + "개월)";
            } else if (months == 0) {
                age += years + "년)";
            } else {
                age += years + "년 " + months + "개월)";
            }
            model.addAttribute("age", age);
            model.addAttribute("mypet", pet);
            String petpic = (String) session.getAttribute("petpic");
            if (petpic != null) {
                model.addAttribute("petpic", petpic);
            }
        }
        return "mypage/mypet";
    }

    // 펫 정보 수정 페이지 이동
    @RequestMapping(value = "/mypetchange")
    public String petChangepage(Model model, @ModelAttribute("userId") String userId) {
        model.addAttribute("mypet", dao.showMyPet(userId));
        return "mypage/petChange";
    }

    // 펫 정보 수정
    @RequestMapping(value = "/petchanged")
    public String petChange(Model model, @ModelAttribute("userId") String userId, @ModelAttribute MypetDTO dto,
                            @RequestParam("attachFile") MultipartFile file, @ModelAttribute("petpic") String petpic) {
        // 이미지 파일 업로드 처리
        if (file.isEmpty()) {
            // 파일이 선택되지 않은 경우, 기존 이미지 경로를 그대로 유지
            MypetDTO originalPet = dao.showMyPet(userId);
            dto.setPet_pic(originalPet.getPet_pic());
        } else {
            try {
                uploadImg(dto, file, session);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/mypetchange"; // 업로드 실패 시 리다이렉트
            }
        }
        dao.petInfoChange(dto, userId);

        return "redirect:/mypet";
    }

    // 펫 등록 페이지 이동
    @RequestMapping(value = "/myaddpet")
    public String addpet(Model model, @ModelAttribute("userId") String userId) {
        return "mypage/petAdd";
    }

    // 펫 등록
    @RequestMapping(value = "/insertpet")
    public String insertPet(Model model, @ModelAttribute("userId") String userId,
                            @ModelAttribute MypetDTO dto, @RequestParam("attachFile") MultipartFile file) {
        // 이미지 파일 업로드 처리
        if (!file.isEmpty()) {
            try {
                uploadImg(dto, file, session);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/myaddpet"; // 업로드 실패 시 리다이렉트
            }
        }
        dao.petInfoAdd(dto, userId);

        return "redirect:/mypet";
    }

    // 펫 삭제
    @RequestMapping(value = "/deletepet")
    public String delpet(@ModelAttribute("userId") String userId) {
        System.out.println(userId);
        dao.deletePet(userId);
        System.out.println(session.getAttribute("petpic") + " 세션 삭제");
        session.removeAttribute("petpic");
        return "redirect:/mypet";
    }

    // 이미지 업로드 메서드
    private void uploadImg(MypetDTO dto, MultipartFile file, HttpSession session) throws IOException {
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String correctedPath = currentDirectory.replace("\\", "/");
        UUID uuid = UUID.randomUUID();
        String path = correctedPath + "/src/main/resources/static/img/mypage/upload/" + uuid.toString() + fileName;
        System.out.println(path);
        File destinationFile = new File(path);
        file.transferTo(destinationFile);

        String filePath = "/img/mypage/upload/" + uuid.toString() + fileName;
        dto.setPet_pic(filePath);

        // 세션에 새로운 petpic 값을 저장
        session.setAttribute("petpic", filePath);
        System.out.println("저장 성공");
    }


}
