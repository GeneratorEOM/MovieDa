package com.movieda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieda.beans.BoardBean;
import com.movieda.beans.UserBean;
import com.movieda.dao.UserDAO;

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
	public int addUserInfo(UserBean userBean) {
		// 아이디 중복 체크
		boolean isEmailExist = checkUserEmailExist(userBean.getUser_email());
		// 아이디 중복이므로 가입실패 - false
		if(isEmailExist == true) return -1;
		
		// 중복 아니므로 가입성공 - true
		int isAddSuccess = userDAO.addUserInfo(userBean);
		if(isAddSuccess > 0) return 1;
		else return 0;
	
	}
	public boolean modifyUserInfo(UserBean userBean) {
		int isUpdateSuccess = userDAO.modifyUserInfo(userBean);
		if(isUpdateSuccess > 0) return true;
		else return false;
	}
	public boolean deleteUserInfo(int user_idx) {
		int isDeleteSuccess = userDAO.deleteUserInfo(user_idx);
		if(isDeleteSuccess > 0) return true;
		else return false;
	}
	
	
	
	// 중복되는 이메일이 있는지 체크
	public boolean checkUserEmailExist(String email) {
		// 아이디가 존재하면 값이 넘어온다.
		String checkEmail = userDAO.checkUserEmailExist(email);
		// 값이 있으니까 중복 true
		if(checkEmail != null) return true;
		// 값이 없으니까 중복 아님 false
		else return false;
	}

}
