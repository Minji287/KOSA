package com.example.unipet.boards.controller;

import com.example.unipet.boards.domain.BoardDTO;
import com.example.unipet.boards.repository.BoardRepository;
import com.example.unipet.boards.repository.CommentRepository;
import com.example.unipet.boards.domain.CommentDTO;
import com.example.unipet.boards.entity.Board;
import com.example.unipet.boards.entity.Comment;
import com.example.unipet.login.config.auth.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    //게시판 유형에 맞는 저장된 게시글 리스트 가져오기
    @GetMapping("/list/{boardId}")
    public List<Board> getBoardsByBoardId(@PathVariable int boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    //작성된 게시물 리스트 가져오기
//    @GetMapping("/list/{boardNo}")
//    //응답 테스트 완료
//    public Board getBoards(@PathVariable int boardNo) {
//        return boardRepository.findByBoardNo(boardNo);
//    }

    //게시글 작성하기
    @PostMapping("/save")
    //응답 테스트 완료
    public ResponseEntity<String> saveBoard(@RequestBody BoardDTO dto, @AuthenticationPrincipal MyUserDetails userDetails) {
        System.out.println(dto.toString()); //데이터 찍어보기
        System.out.println("Access Token ID 찍어보기: " + userDetails.getUser().getUserId());
        Board writeBoardEntity = Board.builder()
                .boardNo(dto.getBoardNo())
                .userId(userDetails.getUser().getUserId())
                .boardId(dto.getBoardId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .postingDate(ZonedDateTime.parse(dto.getPostingDate()))
                .views(dto.getViews())
                .likeCounting(dto.getLikeCounting())
                .imgPath(dto.getImgPath())
                .build();
        boardRepository.save(writeBoardEntity);

        return ResponseEntity.ok("susccess");
    }

    //게시글 삭제하기
    @DeleteMapping("/delete/{boardNo}")
    //응답 테스트 완료
    public void deleteBoard(@PathVariable int boardNo) {
        boardRepository.deleteByBoardNo(boardNo);
    }

    //게시글 수정하기
    @PutMapping("/update")
    public Board updateBoard(@RequestBody Board board) {
        Board existingBoard = boardRepository.findByBoardNo(board.getBoardNo());
        if (existingBoard == null) {
            throw new IllegalArgumentException("Board not found with boardNo " + board.getBoardNo());
        }

        // 게시글의 내용만 수정
        existingBoard.setContent(board.getContent());

        // 수정된 existingBoard를 저장
        return boardRepository.save(existingBoard);
    }



    //게시글 제목으로 검색하기
    @GetMapping("/search/{title}")
    public List<Board> searchBoards(@PathVariable String title) {
        return boardRepository.findByTitleContaining(title);
    }

    //해당 게시글 내용 + 댓글 가져오기
    @GetMapping("/content/{boardNo}")
    //응답 테스트 완료
    public Board getBoard(@PathVariable int boardNo) {
        Board board = boardRepository.findByBoardNo(boardNo);
        if (board == null) {
            throw new IllegalArgumentException("Board not found with boardNo " + boardNo);
        } else {
            board.getComments().size();  // 댓글 데이터를 불러옴

            // 조회수 증가 로직 추가
            board.setViews(board.getViews() + 1);
            boardRepository.save(board);  // 조회수가 증가된 Board를 저장

            return board;
        }
    }

    // ======= 댓글 관련 메서드 선언 =======

    //댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<String> saveComment(@RequestBody CommentDTO dto, @AuthenticationPrincipal MyUserDetails userDetails) {
        System.out.println(dto.toString()); //데이터 찍어보기
        System.out.println("Access Token ID 찍어보기: " + userDetails.getUser().getUserId());

        Board board = boardRepository.findById(dto.getBoardNo()).orElseThrow(() -> new RuntimeException("Board not found with boardNo: " + dto.getBoardNo()));

        Comment writeCommentEntity = Comment.builder()
                .commentId(dto.getCommentId())
                .userId(userDetails.getUser().getUserId())
                .board(board)
                .content(dto.getContent())
                .commentDate(dto.getCommentDate())
                .build();

        commentRepository.save(writeCommentEntity);

        return ResponseEntity.ok("success");
    }

    //댓글 삭제
    @DeleteMapping("/comment/delete/{commentId}")
    //응답 테스트 완료
    public void deleteComment(@PathVariable int commentId) {
        commentRepository.deleteById(commentId);
    }

    //댓글 조회
    @GetMapping("/comments/{boardNo}")
    public List<Comment> getComments(@PathVariable int boardNo) {
        return commentRepository.findByBoard_BoardNo(boardNo);
    }

    //댓글 수정
    @PutMapping("/comment/update")
    //응답 테스트 완료
    public Comment updateComment(@RequestBody CommentDTO commentDTO) {
        //기존의 데이터를 가져와서 일부 필드만 수정한 후 다시 저장하는 방식
        Comment existingComment = commentRepository.findById(commentDTO.getCommentId()).orElseThrow(() -> new IllegalArgumentException("Comment not found with id " + commentDTO.getCommentId()));

        // 수정이 필요한 필드만 업데이트
        existingComment.setContent(commentDTO.getContent());
        return commentRepository.save(existingComment);
    }
}
