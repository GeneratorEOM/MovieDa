package com.movieda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieda.beans.ReviewBean;
import com.movieda.service.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	MovieService movieService;
	
	@GetMapping("/review/list")
	public List<ReviewBean> list(@RequestParam(name = "review_movie_id") int review_movie_id) {
		return movieService.list(review_movie_id);
	}
	
	@PostMapping("/review/write")
	public void write(@RequestBody ReviewBean reviewBean) {
		movieService.write(reviewBean);
	}
	
	@PutMapping("/review/modify")
	public void modify(@RequestBody ReviewBean reviewBean) {
		movieService.modify(reviewBean);
	}
	
	@DeleteMapping("/review/delete") 
	public void delete(@RequestParam(value = "review_idx") int review_idx) {
		movieService.delete(review_idx);
	}
	
}
