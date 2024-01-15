package com.example.unipet.boards.repository;

import com.example.unipet.boards.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Board findByBoardNo(int boardNo);
    List<Board> findByTitleContaining(String title);
    void deleteByBoardNo(int boardNo);
    List<Board> findByBoardId(int boardId);

}
