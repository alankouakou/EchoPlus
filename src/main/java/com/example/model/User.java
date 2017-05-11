package com.example.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.exception.InsufficientFundsException;

@Entity
public class User implements Serializable {
	@Id
	private String username;
	private String password;
	private String name;
	private String status;
	private int balance;
	@ManyToOne
	private Role role;
	
	

	public User() {
		super();
		this.password = "changeme";
		this.balance = 0;
		this.status = "disabled";
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.status = "disabled";
		this.balance = 1;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) throws InsufficientFundsException {
		if (balance < 0) throw new InsufficientFundsException();
		this.balance = balance;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLogin() {
		return username;
	}

	public int charge(int charge) throws InsufficientFundsException {
		int solde = this.getBalance() - charge;
		this.setBalance(solde);
		return solde;
	}
	
	@Override
	public String toString(){
		return username;
	}
}
