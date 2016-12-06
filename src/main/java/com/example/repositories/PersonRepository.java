package com.example.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.model.Person;
import com.example.model.User;

public interface PersonRepository extends JpaRepository<Person, Long>{
	
	public Person findOne(Long id);
	public List<Person> findAll();
	public List<Person> findAll(Sort sort);
	public List<Person> findByUser(User user, Sort sort);
}
