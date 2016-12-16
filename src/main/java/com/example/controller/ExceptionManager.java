package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.exception.InsufficientFundsException;

@ControllerAdvice
public class ExceptionManager {
	

	public String nullExceptionPage(){
		return "null_exception_page";
	}
	
	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String fondsInsuffisantsExc(){
		return "fonds_insuffisants";
	}

}
