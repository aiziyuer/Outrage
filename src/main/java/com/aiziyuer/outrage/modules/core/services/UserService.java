package com.aiziyuer.outrage.modules.core.services;

import org.springframework.stereotype.Service;

import com.aiziyuer.outrage.modules.core.models.User;

@Service
public class UserService {

	public User login(String userName, String passowrd) {
		return new User(userName, passowrd);
	}

}
