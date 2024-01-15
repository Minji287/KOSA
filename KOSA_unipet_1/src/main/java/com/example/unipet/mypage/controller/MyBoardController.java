package com.example.unipet.mypage.controller;

import com.example.unipet.mypage.dao.MyBoardMapper;
import com.example.unipet.mypage.dao.MyMapper;
import com.example.unipet.mypage.domain.BoardVO;
import com.example.unipet.mypage.domain.MyWritingVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"userId", "myname"})
public class MyBoardController {

    @Autowired
    MyBoardMapper dao;

    @RequestMapping(value = "/myshare")
    public String myshare(@ModelAttribute("userId") String userId, Model model) {
        model.addAttribute("share", dao.getMyShare(userId));
        return "mypage/myshare";
    }

    @RequestMapping(value = "/mysharelikes")
    public String mysharelikes(@ModelAttribute("userId") String userId, Model model) {
        model.addAttribute("likes", dao.getShareLikes(userId));
        return "mypage/mysharelikes";
    }

    // 나의 게시글 페이지
    @RequestMapping(value = "/mywriting")
    public String mywriting() {
        return "mypage/mywriting";
    }

    @RequestMapping("/items")
    @ResponseBody
    public Map<String, Object> paging(@RequestParam("limit") int limit,
                                      @RequestParam("page") int page,
                                      @ModelAttribute("userId") String userId) {
        int offset = (page - 1) * limit;
        int count = dao.countWritings(userId);
        List<MyWritingVO> items = dao.getPagingItems(limit, offset, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("writings", items);

        return response;
    }

}
