package com.example.unipet.mypage.controller;

import com.example.unipet.login.config.auth.MyUserDetails;
import com.example.unipet.mypage.domain.BoardDTO;
import com.example.unipet.mypage.domain.MyPagingDTO;
import com.example.unipet.mypage.service.BoardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MyBoardController {

    private final BoardsService boardsService;

    @GetMapping("/mywritings")
    public ResponseEntity<Map<String, Object>> mywritings(@AuthenticationPrincipal MyUserDetails userDetails,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam("boardId") int boardId,
                                                          @RequestParam(defaultValue = "10") int size) {
        String userId = userDetails.getUser().getUserId();
        String userName = userDetails.getUsername();
        String petPic = boardsService.getPetPic(userId);

        MyPagingDTO myPagingDTO = MyPagingDTO.builder()
                .userId(userId)
                .page(page)
                .boardId(boardId)
                .size(size)
                .build();
        Page<BoardDTO> list = boardsService.getMyWritings(myPagingDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("userName", userName);
        response.put("petPic", petPic);

        if(!list.isEmpty()) {
            response.put("list", list);
            return ResponseEntity.ok()
                    .body(response);
        } else {
            response.put("noWritings", "작성된 글이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

}
