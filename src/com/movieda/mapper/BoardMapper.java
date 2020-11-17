package com.movieda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.movieda.beans.BoardBean;
import com.movieda.beans.CommentBean;

public interface BoardMapper {

	@Select("SELECT b.board_idx, b.board_subject, b.board_content, b.board_file, "
			+ "board_date, u.user_name AS board_writer "
			+ "FROM board b JOIN user u "
			+ "ON b.board_user_idx = u.user_idx "
			+ "ORDER BY b.board_idx DESC")
	List<BoardBean> getBoardList(RowBounds rowBounds);

	@Insert("INSERT INTO board(board_idx, board_subject, board_content, "
			+ "board_file, board_date, board_user_idx) "
			+ "VALUES(null, #{board_subject}, #{board_content}, #{board_file}, "
			+ "now(), #{board_user_idx})")
	int writeBoard(BoardBean boardBean);

	@Select("SELECT b.board_idx, b.board_subject, b.board_content, b.board_file, "
			+ "date_format(b.board_date, '%Y-%m-%d') AS board_date, b.board_user_idx, u.user_name AS board_writer " 
			+ "FROM board b JOIN user u "
			+ "ON b.board_user_idx = u.user_idx "
			+ "WHERE b.board_idx = #{board_idx}")
	BoardBean detailBoard(int board_idx);

	@Update("UPDATE board SET board_subject = #{board_subject}, "
			+ "board_content = #{board_content}, board_file = #{board_file} "
			+ "WHERE board_idx = #{board_idx}")
	void modifyBoard(BoardBean boardBean);

	@Delete("DELETE "
			+ "FROM board "
			+ "WHERE board_idx = #{board_idx}")
	void deleteBoard(int board_idx);
	
	@Select("SELECT COUNT(*) "
			+ "FROM board")
	int getContentCnt();

	@Select("SELECT *, u.user_name AS comment_writer "
			+ "FROM comment c JOIN user u "
			+ "ON c.comment_user_idx = u.user_idx "
			+ "WHERE c.comment_board_idx = #{board_idx} "
			+ "ORDER BY c.comment_idx DESC")
	List<CommentBean> getCommentList(int board_idx);

	@Insert("INSERT INTO comment(comment_idx, comment_content, comment_date, "
			+ "comment_board_idx, comment_user_idx) "
			+ "VALUES(null, #{comment_content}, now(), #{comment_board_idx}, #{comment_user_idx})")
	void writeComment(CommentBean commentBean);

	@Update("UPDATE comment SET comment_content = #{comment_content} "
			+ "WHERE comment_idx = #{comment_idx}")
	void modifyComment(CommentBean commentBean);

	@Delete("DELETE "
			+ "FROM comment "
			+ "WHERE comment_idx = #{comment_idx}")
	void deleteComment(int comment_idx);
	

}
