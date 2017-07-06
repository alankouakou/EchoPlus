package com.example.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.exception.InsufficientFundsException;

@ControllerAdvice(basePackages={"com.example.controller"})
public class GlobalControllerAdvice {


	
	@ExceptionHandler(NullPointerException.class)
	public String nullExceptionPage(){
		return "null_exception_page";
	}
	
	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String fondsInsuffisantsExc(){
		return "fonds_insuffisants";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String accessDeniedExc(){
		return "403";
	}
	
	@ExceptionHandler(Exception.class)
	public String otherExceptionHandler(Model model, Exception exception){
		model.addAttribute("error_message",exception.getMessage());
		return "general_error";
	}
}
