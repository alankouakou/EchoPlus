package com.example.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String>{

	@Override
	public void initialize(PhoneNumber phoneNumber) {
		
	}

	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext ctx) {
		return (phoneField != null) && phoneField.matches("[0-9]+") && (phoneField.length()>= 8 ) && (phoneField.length() <= 15); 
	}

}
