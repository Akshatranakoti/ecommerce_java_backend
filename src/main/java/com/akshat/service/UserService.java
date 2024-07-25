package com.akshat.service;

import org.springframework.stereotype.Service;

import com.akshat.exception.UserException;
import com.akshat.model.User;

@Service
public interface UserService {

	public User findUserById(Long userId)throws UserException;
	public User findUserProfileByJwt(String jwt)throws UserException;
	
	
	
}
