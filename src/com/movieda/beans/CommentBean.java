package com.movieda.beans;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentBean {

	private int comment_idx;
	private String comment_content;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp comment_date;
	private int comment_board_idx;
	private int comment_user_idx;
	
	
	private String comment_writer;
	
	
	public int getComment_user_idx() {
		return comment_user_idx;
	}
	public void setComment_user_idx(int comment_user_idx) {
		this.comment_user_idx = comment_user_idx;
	}
	public int getComment_idx() {
		return comment_idx;
	}
	public void setComment_idx(int comment_idx) {
		this.comment_idx = comment_idx;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public Timestamp getComment_date() {
		return comment_date;
	}
	public void setComment_date(Timestamp comment_date) {
		this.comment_date = comment_date;
	}
	public int getComment_board_idx() {
		return comment_board_idx;
	}
	public void setComment_board_idx(int comment_board_idx) {
		this.comment_board_idx = comment_board_idx;
	}
	public String getComment_writer() {
		return comment_writer;
	}
	public void setComment_writer(String comment_writer) {
		this.comment_writer = comment_writer;
	}
	
	
}
