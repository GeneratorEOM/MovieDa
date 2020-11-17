package com.movieda.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.movieda.beans.ReviewBean;
import com.movieda.mapper.MovieMapper;

@Repository
@RestController
public class MovieDAO {

	@Autowired
	MovieMapper movieMapper;
	
	public List<ReviewBean> list(int review_movie_id) {
		return movieMapper.list(review_movie_id);
	}

	public void write(ReviewBean reviewBean) {
		movieMapper.write(reviewBean);
	}

	public void modify(ReviewBean reviewBean) {
		movieMapper.modify(reviewBean);
		
	}

	public void delete(int review_idx) {
		movieMapper.delete(review_idx);
		
	}
}
