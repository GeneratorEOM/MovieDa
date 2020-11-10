package com.movieda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.movieda.service.UserService;
import com.myweb.beans.UserBean;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	UserService userService;

	@GetMapping("/api/hello")
	public String hello() {
		return "hello";
	}	
	@PostMapping("/api/login")
	public UserBean login(@RequestBody UserBean userBean) {		
		return userService.getLoginUserInfo(userBean);
	}
	
	@GetMapping("/api/user")
	public UserBean user(@RequestParam("token") String token) {
		return userService.getMyPageUserInfo(token);
	}
}
