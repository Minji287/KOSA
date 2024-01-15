package com.example.unipet.boards.controller;

import com.example.unipet.boards.dao.BoardMapper;
import com.example.unipet.boards.domain.BoardDTO;
import com.example.unipet.boards.domain.CommentDTO;
import com.example.unipet.mypage.dao.MyPetMapper;
import com.example.unipet.mypage.domain.MyWritingVO;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("board")
public class BoardController {
    @Autowired
    BoardMapper dao;
    @Autowired
    MyPetMapper pet_dao;

    @RequestMapping("list{board_id}")
    public ModelAndView BoardList(@PathVariable("board_id") int board_id) {
        BoardDTO dto = new BoardDTO();
        dto.setBoard_id(board_id);

        List<BoardDTO> list = dao.boardId(dto);
        ModelAndView mav = new ModelAndView();
        mav.addObject("boardId", board_id);

        if (list.size() != 0) {
            mav.addObject("list", list);
//            String userId = (String) request.getSession().getAttribute("userId");
//            boolean showWriteButton = "admin".equals(userId);
//            mav.addObject("showWriteButton", showWriteButton);
        } else {
            mav.addObject("msg", "작성된 게시물이 존재하지 않습니다.");
        }
        mav.setViewName("board");
        return mav;
    }

    @RequestMapping("save")
    public ModelAndView insert(BoardDTO save) {
        System.out.println(save);
        //초기화 된 게시판에 첫 게시글 작성할 때
        //board_id를 Integer 타입으로 변경하면 쉽게 해결 가능하지만 int타입을 유지해야 함.
 /*       if(save.getBoard_id() == -1) {
            int newBoardId = dao.makeBoardId(save);
            save.setBoard_id(newBoardId);
        }*/
        boolean result = dao.insert(save);
        ModelAndView mav = new ModelAndView();
        if (result) {
            mav.setViewName("redirect:/board/list" + save.getBoard_id());
        } else {
            mav.addObject("msg", "글 등록에 실패했습니다.");
        }
        return mav;
    }

/*    @RequestMapping("save")
    public ModelAndView insert(BoardDTO save) {
        System.out.println(save);
        ModelAndView mav = new ModelAndView();

        if (save.getBoard_id() < 0) {
            int newBoardId = dao.makeBoardId(save);
            save.setBoard_id(newBoardId);
        }

        boolean result = dao.insert(save);

        if (result) {
            mav.setViewName("redirect:/board/list" + save.getBoard_id());
        } else {
            mav.addObject("msg", "글 등록에 실패했습니다.");
        }

        return mav;
    }*/


    @RequestMapping("delete")
    public ModelAndView delete(int board_no) {
        BoardDTO board = dao.getBoard(board_no);
        boolean result = dao.delete(board_no);
        ModelAndView mav = new ModelAndView();
        if (result) {
            mav.addObject("list", dao.list());
        } else {
            mav.addObject("msg", "글 삭제에 실패했습니다.");
        }
        mav.setViewName("redirect:/board/list" + board.getBoard_id());

        return mav;
    }

    @RequestMapping(value = "/sbupdate",
            method = RequestMethod.POST)
    public ModelAndView update(BoardDTO dto) {
        boolean result = dao.update(dto);
        ModelAndView mav = new ModelAndView();

        if (result) {
            BoardDTO updatedBoard = dao.getBoard(dto.getBoard_no());
            mav.addObject("board", updatedBoard); // 모델에 추가
            mav.setViewName("redirect:/board/content?board_no=" + dto.getBoard_no()); // 수정한 글 보기 페이지로 리다이렉트
        } else {
            mav.addObject("msg", "글 수정에 실패했습니다.");
            mav.setViewName("error"); // 에러 페이지로 리다이렉트
        }
        return mav;
    }

    @RequestMapping("/sbsearch")
    public ModelAndView search(String title) {
        List<BoardDTO> list = dao.search(title);
        //System.out.println(list.size());
        ModelAndView mav = new ModelAndView();
        if (list.size() != 0) {
            mav.addObject("list", list);
        } else {
            mav.addObject("msg", "작성된 게시물이 존재하지 않습니다.");
        }
        mav.setViewName("board");
        return mav;
    }

    @RequestMapping("posting")
    public ModelAndView writePost(@RequestParam("board_id") int boardId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("boardId", boardId);
        mav.setViewName("posting");
        return mav;
    }

    @RequestMapping(value = "/content", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ModelAndView showContent(@RequestParam("board_no") int board_no, HttpSession session) {
        dao.increaseViews(board_no);
        BoardDTO board = dao.showContent(board_no); //DTO 반환
        System.out.println("테스트="+board);
        String pet_pic = pet_dao.getPetPicUrl(board.getUser_id());

        List<CommentDTO> comments = dao.getComments(board_no); //Comment 리스트 가져오기

        for(CommentDTO comment : comments) {
            String commentUserId = comment.getUser_id();
            String pet_pic_for_comment_user = pet_dao.getPetPicUrl(commentUserId);
            comment.setPetPicUrl(pet_pic_for_comment_user);
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("board", board);
        mav.addObject("boardId", board.getBoard_id());
        mav.addObject("comments", comments);
        mav.addObject("pet_pic", pet_pic);



        String currentUserId = (String) session.getAttribute("userId");
        System.out.println("로그인 중인 아이디:" + currentUserId);

        if (currentUserId != null && (currentUserId.equals(board.getUser_id()) || currentUserId.equals("admin"))) {
            mav.addObject("canEdit", true);
        } else {
            mav.addObject("canEdit", false);
        }

        mav.setViewName("content");
        return mav;
    }

    //json 받는법
    @RequestMapping(value = "/parsejson", produces = "application/json; charset=utf-8")
    @ResponseBody
    public BoardDTO parseJson(int board_no) {
        return dao.getBoard(board_no);
    }

    @RequestMapping("comment")
    public ModelAndView comment(CommentDTO content) {
        System.out.println(content);

        boolean result = dao.comment(content);
        ModelAndView mav = new ModelAndView();
        System.out.println(content);
        if (result) {
            mav.setViewName("redirect:/board/content?board_no=" + content.getBoard_no());
        } else {
            mav.addObject("msg", "글 등록에 실패했습니다.");
        }
        return mav;
    }

}
