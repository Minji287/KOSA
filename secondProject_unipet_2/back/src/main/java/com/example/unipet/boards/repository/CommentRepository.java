package com.example.unipet.boards.repository;

import com.example.unipet.boards.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBoard_BoardNo(int boardNo);
    //쿼리 메서드 작성법
    //findBy는 쿼리 메소드의 시작을 나타낸다.
    //BoardNo는 Comment 객체의 boardNo 필드를 의미한다.
    //기본 내장 쿼리에는 findAll(), findById(ID id), save(T entity), delete(T entity) 등이 있지만,
    // 특정 필드에 대한 조건을 가진 쿼리는 기본 내장 쿼리에 포함되어 있지 않다.
    // 이런 경우에는 쿼리 메소드를 작성하여 사용해야한다.
}
