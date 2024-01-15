package com.example.unipet.mypage.controller;

import com.example.unipet.login.config.auth.MyUserDetails;
import com.example.unipet.mypage.domain.EmailDTO;
import com.example.unipet.mypage.domain.UserResultDTO;
import com.example.unipet.mypage.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    // 회원 정보
    @GetMapping(value = "/myprofile")
    public ResponseEntity<Map<String, Object>> myprofile(@AuthenticationPrincipal MyUserDetails userDetails) {
        String userId = userDetails.getUser().getUserId();
        Optional<UserResultDTO> optionalUser = myService.getUserResultDTO(userId);
        Map<String, Object> response = new HashMap<>();

        if(optionalUser.isPresent()) {
            response.put("user", optionalUser.get());
            return ResponseEntity.ok()
                    .body(response);
        } else {
            response.put("error", "사용자 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }


    // 로그아웃 - Vue 에서 처리(토큰)
//    @PostMapping(value = "/logout")
//    public void logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        session.invalidate();
//    }

    // 이메일 변경
    @PostMapping(value = "/changeemail")
    public ResponseEntity<String> changeemail(@AuthenticationPrincipal MyUserDetails userDetails,
                                              @RequestParam("email") String email,
                                              @RequestParam("domain") String domain) {
        String userId = userDetails.getUser().getUserId();
        EmailDTO emailDTO = EmailDTO.builder()
                .userId(userId)
                .email(email)
                .domain(domain)
                .build();
        boolean emailChanged = myService.saveEmail(emailDTO);

        if (emailChanged) {
            return ResponseEntity.ok()
                    .body("이메일이 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("사용자 정보를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 확인
    @PostMapping(value = "/passcheck")
    public ResponseEntity<String> passcheck(@AuthenticationPrincipal MyUserDetails userDetails,
                                            @RequestParam("inputPass") String inputPass,HttpServletRequest request) {
        String userId = userDetails.getUser().getUserId();
        String password = userDetails.getPassword();
        System.out.println("비밀번호 : " + password);
        boolean checked = myService.getPass(userId, inputPass);
        if(checked) {
            return ResponseEntity.ok()
                    .body("비밀번호 확인 완료");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("비밀번호가 일치하지 않습니다.");
        }
    }

    // 비밀번호 변경
    @PostMapping(value = "/passchange")
    public ResponseEntity<String> passchange(@AuthenticationPrincipal MyUserDetails userDetails,
                                             @RequestParam("changedPass") String changedPass,
                                             @RequestParam("changedPassCheck") String changedPassCheck) {
        String userId = userDetails.getUser().getUserId();

        if(changedPass.equals(changedPassCheck)) {
            boolean pwChanged = myService.changeMyPass(userId, changedPass);
            if (pwChanged) {
                return ResponseEntity.ok()
                        .body("비밀번호 변경이 성공적으로 변경되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("오류가 발생하였습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("비밀번호가 일치하지 않습니다.");
        }
    }

    @PostMapping(value = "/delaccount")
    public ResponseEntity<String> deleteAcc(@AuthenticationPrincipal MyUserDetails userDetails) {
        String userId = userDetails.getUser().getUserId();
        boolean success = myService.saveRolesOut(userId);
        if (success) {
            return ResponseEntity.ok("탈퇴되었습니다. 이용해 주셔서 감사합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("회원 탈퇴 실패"); // 실패 시 400 Bad Request 반환
        }
    }
}
