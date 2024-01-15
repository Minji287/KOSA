package com.example.unipet.mypage.service;

import com.example.unipet.mypage.domain.BoardDTO;
import com.example.unipet.mypage.domain.MyPagingDTO;
import com.example.unipet.mypage.entity.Boards;
import com.example.unipet.mypage.repository.MyBoardRepository;
import com.example.unipet.mypage.repository.MyPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardsService {
    private final MyBoardRepository myBoardRepository;
    private final MyPetRepository myPetRepository;
    public Page<BoardDTO> getMyWritings(MyPagingDTO myPagingDTO) {

        Pageable pageable = PageRequest.of(myPagingDTO.getPage(), myPagingDTO.getSize(),
                                            Sort.by(Sort.Order.desc("boardNo")));
        // 가비지 컬렉터가 나머지 찾지 않는 값 가져감(메모리 누수) 수정필
        return myBoardRepository.findByUserIdAndBoardId(myPagingDTO.getUserId(),
                myPagingDTO.getBoardId(), pageable)
                .map(BoardDTO::toBoardDTO);
    }

    public String getPetPic(String userId) {
        return myPetRepository.findPetPicByUserId(userId);
    }
}
