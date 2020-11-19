package com.movieda.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movieda.beans.BoardBean;
import com.movieda.beans.CommentBean;
import com.movieda.mapper.BoardMapper;

@Repository
public class BoardDAO {
	
	@Autowired
	BoardMapper boardMapper;

	public List<BoardBean> getBoardList(RowBounds rowBounds) {
		return boardMapper.getBoardList(rowBounds);
	}

	public int writeBoard(BoardBean boardBean) {
		return boardMapper.writeBoard(boardBean);
	}

	public BoardBean detailBoard(int board_idx) {
		return boardMapper.detailBoard(board_idx);
	}
	
	public void modifyBoard(BoardBean boardBean) {
		boardMapper.modifyBoard(boardBean);
	}
	public void deleteBoard(int board_idx) {
		boardMapper.deleteBoard(board_idx);
	}
	public int getContentCnt() {
		return boardMapper.getContentCnt();
	}

	public List<CommentBean> getCommentList(int board_idx) {
		return boardMapper.getCommentList(board_idx);
	}

	public void writeComment(CommentBean commentBean) {
		boardMapper.writeComment(commentBean);
		
	}

	public void modifyComment(CommentBean commentBean) {
		boardMapper.modifyComment(commentBean);
		
	}

	public void deleteComment(int comment_idx) {
		boardMapper.deleteComment(comment_idx);
		
	}
	
}
