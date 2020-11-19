package com.movieda.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import com.movieda.beans.UserBean;

public interface UserMapper {

	
	@Select("SELECT * "
			  + "FROM user "
			  + "WHERE user_email = #{user_email} and user_password = #{user_password}")
		UserBean getLoginUserInfo(UserBean userBean);
	
	@Select("SELECT * "
			  + "FROM user "
			  + "WHERE user_email = #{user_email}")
		UserBean getMyPageUserInfo(String token);

	@Insert("INSERT INTO user(user_email, user_password, user_name, user_gender, user_date) "
			  + "values(#{user_email}, #{user_password}, #{user_name}, #{user_gender}, now())")
		int addUserInfo(UserBean userBean);
	
	@Select("SELECT user_email "
			+ "FROM user "
			+ "WHERE user_email = #{user_email}")
		String checkUserEmailExist(String email);
	
	@Update("UPDATE user "
			+ "SET user_password = #{user_password}, user_name = #{user_name}, user_gender = #{user_gender} "
			+ "WHERE user_email = #{user_email}")
	int modifyUserInfo(UserBean userBean);

	@Delete("DELETE "
			+ "FROM user "
			+ "WHERE user_idx = #{user_idx}")
	int deleteUserInfo(int user_idx);
		
}
