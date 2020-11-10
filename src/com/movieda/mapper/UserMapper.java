package com.movieda.mapper;

import org.apache.ibatis.annotations.Select;

import com.myweb.beans.UserBean;

public interface UserMapper {

	@Select("SELECT * "
			  + "FROM user "
			  + "WHERE email = #{email} and password = #{password}")
		UserBean getLoginUserInfo(UserBean userBean);
	
	@Select("SELECT * "
			  + "FROM user "
			  + "WHERE email = #{email}")
		UserBean getMyPageUserInfo(String token);
}
