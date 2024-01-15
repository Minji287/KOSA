package com.example.unipet.mypage.service;

import com.example.unipet.mypage.domain.EmailDTO;
import com.example.unipet.mypage.domain.UserResultDTO;
import com.example.unipet.mypage.entity.User;
import com.example.unipet.mypage.repository.MyPetRepository;
import com.example.unipet.mypage.repository.MyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyService {
    private final MyRepository myRepository;
    private final MyPetRepository myPetRepository;

    // 유저 기본정보 불러오기
    public Optional<UserResultDTO> getUserResultDTO(String userId) {
        Optional<User> optionalUser = Optional.ofNullable(myRepository.findUserByUserId(userId));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String petPic = myPetRepository.findPetPicByUserId(userId);
            UserResultDTO userResultDTO = UserResultDTO.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .petPic(petPic)
                    .build();
            return Optional.of(userResultDTO);
        } else {
            return Optional.empty();
        }
    }


    // 이메일 변경
    @Transactional
    public boolean saveEmail(EmailDTO emailDTO) {
        Optional<User> optionalUser = Optional.ofNullable(myRepository.findUserByUserId(emailDTO.getUserId()));

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String combinedEmail = emailDTO.getEmail() + "@" + emailDTO.getDomain();
            user.setEmail(combinedEmail);
            return true;
        } else {
            return false;
        }
    }

    // 비밀번호 확인
    public boolean getPass(String userId, String inputPass) {
        String pw = myRepository.findPasswordByUserId(userId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(inputPass, pw);
    }

    // 비밀번호 변경
    @Transactional
    public boolean changeMyPass(String userId, String newPw) {
        Optional<User> optionalUser = Optional.ofNullable(myRepository.findUserByUserId(userId));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String changedPw = bCryptPasswordEncoder.encode(newPw);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(changedPw);
            return true;
        } else {
            return false;
        }
    }

    // 탈퇴
    @Transactional
    public boolean saveRolesOut(String userId) {
        Optional<User> optionalUser = Optional.ofNullable(myRepository.findUserByUserId(userId));

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String role = "out";
            user.setRoles(role);
            return true;
        } else {
            return false;
        }
    }

}
