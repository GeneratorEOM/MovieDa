package com.movieda.beans;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReviewBean {

	private int review_idx;
	private String review_content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp review_date;
	private String review_movie_id;
	private int review_user_idx;
	private int review_rating;
	
	private String review_writer;

	public int getReview_user_idx() {
		return review_user_idx;
	}

	public void setReview_user_idx(int review_user_idx) {
		this.review_user_idx = review_user_idx;
	}

	public int getReview_idx() {
		return review_idx;
	}

	public void setReview_idx(int review_idx) {
		this.review_idx = review_idx;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}

	public Timestamp getReview_date() {
		return review_date;
	}

	public void setReview_date(Timestamp review_date) {
		this.review_date = review_date;
	}

	public String getReview_movie_id() {
		return review_movie_id;
	}

	public void setReview_movie_id(String review_movie_id) {
		this.review_movie_id = review_movie_id;
	}

	public String getReview_writer() {
		return review_writer;
	}

	public void setReview_writer(String review_writer) {
		this.review_writer = review_writer;
	}

	public int getReview_rating() {
		return review_rating;
	}

	public void setReview_rating(int review_rating) {
		this.review_rating = review_rating;
	}
	
	

}
