package com.movieda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieda.beans.ReviewBean;
import com.movieda.dao.MovieDAO;

@Service
public class MovieService {

	@Autowired
	private MovieDAO movieDAO;
	
	public List<ReviewBean> list(int review_movie_id) {
		return movieDAO.list(review_movie_id);
	}

	public void write(ReviewBean reviewBean) {
		movieDAO.write(reviewBean);
		
	}

	public void modify(ReviewBean reviewBean) {
		movieDAO.modify(reviewBean);
		
	}

	public void delete(int review_idx) {
		movieDAO.delete(review_idx);
		
	}
}
