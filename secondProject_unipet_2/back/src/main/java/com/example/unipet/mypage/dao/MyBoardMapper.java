package com.example.unipet.mypage.dao;

import com.example.unipet.mypage.domain.BoardVO;
import com.example.unipet.mypage.domain.MyWritingVO;
import com.example.unipet.mypage.domain.UserResultDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyBoardMapper {

    // 나의 나눔 가져오기
    @Select("SELECT board_no, board_id, title, content, posting_date, img_path " +
            "FROM boards " +
            "WHERE board_id = 1 AND user_id = #{userId} " +
            "ORDER BY board_no DESC")
    public List<BoardVO> getMyShare(@Param("userId") String userId);

    // 나눔 좋아요 가져오기
    @Select("SELECT b.board_no, b.board_id, b.title, b.content, b.posting_date, b.img_path " +
            "FROM boards b " +
            "LEFT JOIN uni_pet.like l " +
            "ON b.board_no = l.board_no " +
            "WHERE l.user_id = #{userId} " +
            "ORDER BY l.board_no DESC")
    public List<BoardVO> getShareLikes(@Param("userId") String userId);

    @Select("SELECT count(*) " +
            "FROM boards " +
            "WHERE user_id = #{userId}")
    public int countWritings(@Param("userId") String userId);

    @Select("SELECT board_no, board_id, title, posting_date, " +
            "ROW_NUMBER() OVER (ORDER BY board_no) as num " +
            "FROM boards " +
            "WHERE board_id = 2 AND user_id = #{userId} " +
            "ORDER BY board_no DESC " +
            "LIMIT #{limit} " +
            "OFFSET #{offset}")
    public List<MyWritingVO> getPagingItems(@Param("limit") int limit, @Param("offset") int offset,
                                            @Param("userId") String userId);
}

