package com.movieda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieda.dao.UserDAO;
import com.myweb.beans.UserBean;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDAO;
	
	public UserBean getLoginUserInfo(UserBean userBean) {				
		return userDAO.getLoginUserInfo(userBean);
	}
	public UserBean getMyPageUserInfo(String token) {
		return userDAO.getMyPageUserInfo(token);
	}
}
