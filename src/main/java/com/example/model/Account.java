package com.example.model;

public class Account {
	private String login;
	private String password;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Account(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	

}
