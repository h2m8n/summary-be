package com.jj.summary.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jj.summary.domain.User;
import com.jj.summary.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	
	public User changeInfo(User user) {
		User userOrg = repository.findById(user.getId()).get();
		
		userOrg.setInterest(user.getInterest());
		userOrg.setNickName(user.getNickName());
		userOrg.setPhone(user.getPhone());
		userOrg.setDepartment(user.getDepartment());
//		userOrg.setPassword(user.getPassword());
		userOrg.setProfileImg(user.getProfileImg());
		
		
		return repository.save(userOrg); 
	}
}
