package com.movieda.beans;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;


public class BoardBean {

	private int board_idx;
	private String board_subject;
	private String board_content;
	private String board_file;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp board_date;
	private int board_user_idx;
	
	// 작성자를 저장할 변수
	private String board_writer;
	// 실제 파일을 저장할 변수
	private MultipartFile upload_file;
	
	
	

	public int getBoard_user_idx() {
		return board_user_idx;
	}
	public void setBoard_user_idx(int board_user_idx) {
		this.board_user_idx = board_user_idx;
	}
	public MultipartFile getUpload_file() {
		return upload_file;
	}
	public void setUpload_file(MultipartFile upload_file) {
		this.upload_file = upload_file;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getBoard_subject() {
		return board_subject;
	}
	public void setBoard_subject(String board_subject) {
		this.board_subject = board_subject;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_file() {
		return board_file;
	}
	public void setBoard_file(String board_file) {
		this.board_file = board_file;
	}
	public Timestamp getBoard_date() {
		return board_date;		
	}
	public void setBoard_date(Timestamp board_date) {
		this.board_date = board_date;
	}
	public String getBoard_writer() {
		return board_writer;
	}
	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}
	
	
	
}
