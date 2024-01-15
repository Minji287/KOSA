package com.example.unipet.mypage.controller;

import com.example.unipet.login.config.auth.MyUserDetails;
import com.example.unipet.mypage.domain.MypetDTO;
import com.example.unipet.mypage.entity.Mypet;
import com.example.unipet.mypage.repository.MyPetRepository;
import com.example.unipet.mypage.service.MypetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/mypet")
@RequiredArgsConstructor
public class MyPetController {

    //    @Autowired
//    private HttpSession session;
    private final MypetService mypetService;

    // 펫 조회
    @GetMapping
    public ResponseEntity<Map<String, Object>> getMyPet(@AuthenticationPrincipal MyUserDetails userDetails) {
        String userId = userDetails.getUser().getUserId();
        String userName = userDetails.getUsername();
        Map<String, Object> response = mypetService.getMyPet(userId);
        response.put("userName", userName);
        return ResponseEntity.ok()
                .body(response);
    }

    // 펫 등록 - 프론트단 파일 테스트필요
    @PutMapping("/add")
    public ResponseEntity<String> addPet(@RequestPart MypetDTO mypetDTO, @RequestPart(value = "attachFile", required = false) MultipartFile file) {
        mypetDTO.setAttachFile(file);
        boolean saved = mypetService.addMyPet(mypetDTO);
        if(saved) {
            return ResponseEntity.ok()
                    .body("펫 등록이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("펫 등록이 실패하였습니다.");
        }
    }

    // 펫 정보 수정
    @PutMapping("/change")
    public ResponseEntity<String> petChange(@RequestPart MypetDTO mypetDTO, @RequestPart(value = "attachFile", required = false) MultipartFile file) {
        mypetDTO.setAttachFile(file);
        boolean saved = mypetService.changePetInfo(mypetDTO);
        if(saved) {
            return ResponseEntity.ok()
                    .body("펫 수정이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("펫 수정이 실패하였습니다.");
        }
    }

    // 펫 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePet(@AuthenticationPrincipal MyUserDetails userDetails) {
        String userId = userDetails.getUser().getUserId();

        boolean deleted = mypetService.deletePet(userId);
        if(deleted) {
            return ResponseEntity.ok()
                    .body("펫이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("펫 삭제가 실패하였습니다.");
        }
    }

}
