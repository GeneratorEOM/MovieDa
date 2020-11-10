package com.movieda.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movieda.mapper.UserMapper;
import com.myweb.beans.UserBean;

@Repository
public class UserDAO {

	@Autowired
	UserMapper userMapper;
	
	public UserBean getLoginUserInfo(UserBean userBean) {
		return userMapper.getLoginUserInfo(userBean);
	}
	public UserBean getMyPageUserInfo(String token) {
		return userMapper.getMyPageUserInfo(token);
	}

}
