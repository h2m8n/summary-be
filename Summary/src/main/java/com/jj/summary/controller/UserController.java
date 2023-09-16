package com.jj.summary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jj.summary.domain.User;
import com.jj.summary.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
//	private final AuthenticationService authService;
	private final UserService userService;
	
    @PostMapping(value = "/changeInfo")
    public ResponseEntity<User> changeUserInfo(
    		@RequestHeader("Authorization") String tokenHeader,
    		@RequestBody User user) {
    	String token = tokenHeader.substring(7);
//    	User userInfo = authService.getUserInfo(token);
    	
//    	System.out.println("/api/user/changeInfo : " + userInfo.);
    	return ResponseEntity.ok(userService.changeInfo(user));
    }
}
