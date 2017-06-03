package com.example.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.model.Person;
import com.example.model.User;
import com.example.repositories.PersonRepository;
import com.example.repositories.UserRepository;

@Service
@Transactional
public class PersonService {

	
	@Autowired
	private PersonRepository personRepository;

	public long count() {
		return personRepository.count();
	}

	public void delete(Long arg0) {
		personRepository.delete(arg0);
	}

	public void delete(Person arg0) {
		personRepository.delete(arg0);
	}

	public void deleteAll() {
		personRepository.deleteAll();
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public Page<Person> findAll(Pageable arg0) {
		return personRepository.findAll(arg0);
	}

	public List<Person> findAll(Sort arg0) {
		return personRepository.findAll(arg0);
	}

	public Person findOne(Long id) {
		return personRepository.findOne(id);
	}

	public <S extends Person> List<S> save(Iterable<S> arg0) {
		return personRepository.save(arg0);
	}

	public <S extends Person> S save(S arg0) {
		return personRepository.save(arg0);
	}
	
	
	public List<Person> findByUser(User user, Sort sort){
		return personRepository.findByUser(user, sort);
	}

	public List<Person> findByUser(User user) {
		// TODO Auto-generated method stub
		return personRepository.findByUser(user);
	}
	
	
	
	
}
