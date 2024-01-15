package com.example.unipet.boards.dao;

import com.example.unipet.boards.domain.BoardDTO;
import com.example.unipet.boards.domain.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("select * from boards where board_id = #{board_id} order by posting_date desc")
    List<BoardDTO> boardId(BoardDTO dto);

    @Select("select board_no, user_id, board_id, title, content, date_format(posting_date, '%Y-%m-%d') posting_date, views from boards")
    public List<BoardDTO> list();

    @Insert("insert into boards (user_id, board_id, title, posting_date, content) values (#{user_id}, #{board_id}, #{title}, now(), #{content})")
    public boolean insert(BoardDTO save);

    @Select("select board_no, user_id, board_id, title, date_format(posting_date, '%Y.%m.%d. %m:%s') posting_date, content, views, like_counting, img_path from boards where board_no = #{board_no}")
    public BoardDTO showContent(int board_no); //one //DTO 반환

    @Update("update boards set views = views + 1 where board_no = #{board_no}")
    void increaseViews(int board_no);

    @Select("select board_no, user_id, board_id, title, content, date_format(posting_date, '%Y-%m-%d') posting_date, views from boards where title like concat('%', #{title},'%')")
    public List<BoardDTO> search(String title);

    @Delete("delete from boards where board_no = #{board_no}")//세션 로그인 구현 전 임의 저장한 id 사용 중이라 나중에 user_id = #{user_id} 처리해야 함.
    public boolean delete(int board_no);

    @Update("update boards set content = #{content} where board_no = #{board_no}")
    public boolean update(BoardDTO board);

    @Select("select user_id, title, board_no, board_id, date_format(posting_date, '%Y.%m.%d. %m:%s') posting_date, content, views from boards where board_no = #{board_no}")
    public BoardDTO getBoard(int board_no);

    @Select("select * from comment where board_no = #{board_no}")
    public List<CommentDTO> getComments(int board_no);

    @Insert("insert into comment (board_no, user_id, comment_date, content) values (#{board_no}, #{user_id}, now(), #{content})")
    public boolean comment(CommentDTO save);


}

