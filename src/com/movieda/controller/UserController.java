package com.movieda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieda.beans.UserBean;
import com.movieda.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public UserBean login(@RequestBody UserBean userBean) {			
		return userService.getLoginUserInfo(userBean);
	}
	
	@GetMapping("/info")
	public UserBean user(@RequestParam("token") String token) {
		return userService.getMyPageUserInfo(token);
	}
	
	@PostMapping("/join")
	public int user(@RequestBody UserBean userBean) {
		return userService.addUserInfo(userBean);
	}
	
	@PutMapping("/modify")
	public boolean modify(@RequestBody UserBean userBean) {
		return userService.modifyUserInfo(userBean);
	}
	
	@DeleteMapping("/delete")
	public boolean delete(@RequestParam(name = "user_idx") int user_idx) {
		return userService.deleteUserInfo(user_idx);
	}
	
}
