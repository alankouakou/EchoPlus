package com.example.converters;

import org.springframework.core.convert.converter.Converter;

import com.example.model.Person;
import com.example.service.PersonService;

public class PersonConverter implements Converter<String, Person>{

	private PersonService personService;
	
	public PersonConverter(PersonService personService){
		this.personService = personService;
	}
	
	@Override
	public Person convert(String id) {
		return personService.findOne(Long.parseLong(id));
	}

}
