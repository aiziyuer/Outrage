package com.aiziyuer.outrage.modules.core.models;

import lombok.Data;

@Data
public class User {

	public User() {

	}

	public User(String userName, String passowrd) {
		this.userName = userName;
		this.passowrd = passowrd;
	}

	private String userName;

	private String passowrd;

}
