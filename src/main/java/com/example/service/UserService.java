package com.example.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.exception.InsufficientFundsException;
import com.example.model.User;
import com.example.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	
	private void initUser() throws InsufficientFundsException {
		User user = new User("admin@grio.com","password");
		user.setBalance(5);
		userRepo.save(user);
	}
	
	public long count() {
		return userRepo.count();
	}

	public void delete(Long arg0) {
		userRepo.delete(arg0);
	}

	public void delete(User arg0) {
		userRepo.delete(arg0);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public <S extends User> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		return userRepo.findAll(arg0, arg1);
	}

	public <S extends User> List<S> findAll(Example<S> arg0, Sort arg1) {
		return userRepo.findAll(arg0, arg1);
	}

	public <S extends User> List<S> findAll(Example<S> arg0) {
		return userRepo.findAll(arg0);
	}

	public User findOne(Long arg0) {
		return userRepo.findOne(arg0);
	}

	public <S extends User> List<S> save(Iterable<S> arg0) {
		return userRepo.save(arg0);
	}

	public <S extends User> S save(S arg0) {
		return userRepo.save(arg0);
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	

}
