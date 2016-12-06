package com.example.exception;


public class InsufficientFundsException extends Exception {
	
	@Override
	public String getMessage(){
		return "Solde Insuffisant!";
	}
	
	
}
