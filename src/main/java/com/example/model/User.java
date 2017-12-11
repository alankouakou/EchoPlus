package com.example.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.example.exception.InsufficientFundsException;

@Entity
public class User implements Serializable {
	@Id
	private String username;
	@NotNull(message="Renseignez le mot de passe")
	@Size(min=3,message="Trop court!(3 caract. minimum)")
	private String password;
	@Size(min=3,message="Trop court!(3 caract. minimum)")
	private String firstName;
	@Size(min=3,message="Trop court!(3 caract. minimum)")
	private String lastName;
	@Size(min=3,max=11,message="Trop court!(3 caract. minimum)")
	private String senderName;

	@NotNull(message="Renseignez l'e-mail")
	@Email
	private String email;
	private Boolean enabled;
	private String status;
	private int balance;
	@ManyToOne
	private Role role;
	
	

	public User() {
		super();
		this.password = "changeme";
		this.balance = 0;
		this.status = "disabled";
		this.enabled = true;
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

	public String getEmail() {
		return email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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
