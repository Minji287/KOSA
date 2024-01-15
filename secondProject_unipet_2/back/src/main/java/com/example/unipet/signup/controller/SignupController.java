package com.example.unipet.signup.controller;

import com.example.unipet.signup.entity.SignupMypet;
import com.example.unipet.signup.entity.SignupUser;
import com.example.unipet.signup.model.SignupDTO;
import com.example.unipet.signup.repository.SignupPetRepository;
import com.example.unipet.signup.repository.SignupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SignupUserRepository signupUserRepository;
    private final SignupPetRepository signupPetRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signupReq(@RequestBody SignupDTO dto) {
        StringBuilder message = new StringBuilder();

        if (!validationDto(dto, message)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
        } else {
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            dto.setRoles("ROLE_USER");

            SignupUser signupUserEntity = SignupUser.builder()
                    .userId(dto.getUserId())
                    .password(dto.getPassword())
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .roles(dto.getRoles())
                    .build();
            signupUserRepository.save(signupUserEntity);

            if(dto.getHavePet().equals("true")){
                SignupMypet signupMypetEntity = SignupMypet.builder()
                        .userId(dto.getUserId())
                        .petName(dto.getPetName())
                        .petBirth(dto.getPetBirth())
                        .petGender(dto.getPetGender())
                        .petKind(dto.getPetKind())
                        .petNeuter(dto.getPetNeuter())
                        .petColor(dto.getPetColor())
                        .petWeight(dto.getPetWeight())
                        .petTrait(dto.getPetTrait()).build();

                signupPetRepository.save(signupMypetEntity);
            }
            return ResponseEntity.ok("successful");
        }
    }

    public boolean validationDto(SignupDTO dto, StringBuilder message) {
        if (dto.getUserId().isEmpty()) {
            message.append("아이디를 입력해주세요.");
            return false;
        }
        if (signupUserRepository.existsByuserId(dto.getUserId())) {
            message.append("아이디가 중복됩니다.");
            return false;
        }
        if (dto.getPassword().isEmpty()) {
            message.append("패스워드를 입력해주세요.");
            return false;
        }
        if (dto.getRePassword().isEmpty()) {
            message.append("패스워드 확인을 입력해주세요.");
            return false;
        }
        if (!dto.getPassword().equals(dto.getRePassword())) {
            message.append("패스워드가 일치하지 않습니다.");
            return false;
        }
        if (dto.getEmail().isEmpty()) {
            message.append("이메일을 입력해주세요.");
            return false;
        }
        if (dto.getName().isEmpty()) {
            message.append("이름을 입력해주세요.");
            return false;
        }
        if (dto.getHavePet().equals("true")) {
            if (dto.getPetName().isEmpty()) {
                message.append("펫 이름을 입력해주세요.");
                return false;
            }
            if (dto.getPetBirth() == null) {
                message.append("펫 생일을 입력해주세요.");
                return false;
            }
            if (dto.getPetGender().isEmpty()) {
                message.append("펫 성별을 입력해주세요.");
                return false;
            }
            if (dto.getPetKind().isEmpty()) {
                message.append("펫 종류을 입력해주세요.");
                return false;
            }
            if (dto.getPetNeuter() == null) {
                message.append("펫 중성화여부를 입력해주세요.");
                return false;
            }
            if (dto.getPetColor().isEmpty()) {
                message.append("펫 색상을 입력해주세요.");
                return false;
            }
            if (dto.getPetTrait().isEmpty()) {
                message.append("펫 특징을 입력해주세요.");
                return false;
            }
        }
        return true;
    }
}
