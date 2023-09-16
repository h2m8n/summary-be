package com.jj.summary.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jj.summary.auth.AuthenticationService;
import com.jj.summary.domain.User;
import com.jj.summary.dto.AuthenticationRequest;
import com.jj.summary.dto.AuthenticationResponse;
import com.jj.summary.dto.RegisterRequest;
import com.jj.summary.dto.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(
            @RequestBody RegisterRequest request) 
    {
    	AuthenticationResponse responseDto = service.register(request);
    	
    	if(responseDto.getAccessToken() == null) {
            return ResponseEntity.ok(ResponseDto.builder()
                	.resultCode(200)
                	.resultMsg("회원가입이 실패하였습니다.")
                	.data(responseDto).build());	
    	} else {
    		return ResponseEntity.ok(ResponseDto.builder()
                	.resultCode(200)
                	.resultMsg("회원가입이 정상 처리되었습니다.")
                	.data(responseDto).build());	
    	}
    }
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
    	AuthenticationResponse responseDto = service.authenticate(request);
    	if (responseDto.getAccessToken() != null) {
    		return ResponseEntity.ok(
            		ResponseDto.builder()
            		.resultCode(200)
            		.resultMsg("인증에 성공하였습니다.")
            		.data(responseDto)
            		.build()
            		);
    	} else {
    		return ResponseEntity.ok(
            		ResponseDto.builder()
            		.resultCode(200)
            		.resultMsg("인증에 실패하였습니다.")
            		.build()
            		);
    	}
    	
        
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
    
    @GetMapping(value = "/userInfo")
    public ResponseEntity<User> extractUserInfo(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7); // Remove "Bearer " prefix
    
        User userInfo = service.getUserInfo(token);
        
        return ResponseEntity.ok(userInfo);
    }
    @PostMapping(value = "/checkEmail")
    public boolean validateDuplicateEmail(@RequestParam("email") String email){
    	return service.validateDuplicateNickName(email);
    }
    
    @PostMapping(value = "/checkNickName")
    public boolean validateDuplicateNickName(@RequestParam("nickName") String nickName){
    	return service.validateDuplicateNickName(nickName);
    }
}