package com.example.converters;

import org.springframework.core.convert.converter.Converter;

import com.example.model.User;
import com.example.service.UserService;

public class UserConverter implements Converter<String, User> {

	private UserService userService;
	
	public UserConverter(UserService userService){
		this.userService = userService;
	}
	@Override
	public User convert(String username) {
		return userService.findByUsername(username);
	}


}
