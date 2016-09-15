package com.example.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.model.FileBucket;
import com.example.model.SmsCsv;

@Component
public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FileBucket.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		FileBucket file = (FileBucket) obj;
		if (file.getFile()!= null) {
			if (file.getFile().getSize() == 0) {
				errors.rejectValue("file", "missing.file");
			}
		}

	}

}
