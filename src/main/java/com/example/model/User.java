package com.example.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.exception.InsufficientFundsException;

@Entity
public class User implements Serializable {
	@Id
	private String email;
	private String password;
	private String status;
	private int balance;

	public User() {
		super();
		this.password = "changeme";
		this.balance = 0;
		this.status = "disabled";
	}

	public User(String email, String password) {
		super();
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLogin() {
		return email;
	}

	public int charge(int charge) throws InsufficientFundsException {
		int solde = this.getBalance() - charge;
		this.setBalance(solde);
		return solde;
	}
}
