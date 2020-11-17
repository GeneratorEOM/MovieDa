package com.movieda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.movieda.beans.ReviewBean;

public interface MovieMapper {

	@Select("SELECT *, u.user_name AS review_writer "
			+ "FROM review r JOIN user u "
			+ "ON r.review_user_idx = u.user_idx "
			+ "WHERE r.review_movie_id = #{review_movie_id} "
			+ "ORDER BY r.review_idx DESC")
	List<ReviewBean> list(int review_movie_id);

	@Insert("INSERT INTO review "
			+ "VALUES(null, #{review_content}, now(), #{review_movie_id}, "
			+ "#{review_user_idx}, #{review_rating})")
	void write(ReviewBean reviewBean);

	@Update("UPDATE review SET review_content = #{review_content}, review_rating = #{review_rating} "
			+ "WHERE review_idx = #{review_idx}")
	void modify(ReviewBean reviewBean);

	@Delete("DELETE "
			+ "FROM review "
			+ "WHERE review_idx = #{review_idx}")
	void delete(int review_idx);
	
}
