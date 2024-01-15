package com.example.unipet.mypage.repository;

import com.example.unipet.mypage.domain.BoardDTO;
import com.example.unipet.mypage.entity.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyBoardRepository extends JpaRepository<Boards, Integer> {
    Page<Boards> findByUserIdAndBoardId(String userId, int boardId, Pageable pageable);

}
