package com.movieda.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movieda.beans.UserBean;
import com.movieda.mapper.UserMapper;

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
	public int addUserInfo(UserBean userBean) {
		return userMapper.addUserInfo(userBean);
	}
	public String checkUserEmailExist(String email) {
		return userMapper.checkUserEmailExist(email);
	}
	public int modifyUserInfo(UserBean userBean) {
		return userMapper.modifyUserInfo(userBean);
	}
	public int deleteUserInfo(int user_idx) {
		return userMapper.deleteUserInfo(user_idx);
	}

}
