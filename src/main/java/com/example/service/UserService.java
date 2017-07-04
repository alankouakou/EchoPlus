package com.example.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.RefillRequest;
import com.example.model.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.StatusRepo;
import com.example.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	StatusRepo statusRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	
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

	public List<User> save(Iterable<User> users) {
		
		return userRepo.save(users);
	}

	public User save(User user) {
		String encodedPwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPwd);
		return userRepo.save(user);
	}

	public User findByUsername(String email) {
		return userRepo.findByUsername(email);
	}
	
	public Boolean passwordReset(String username, String password, String passwordConfirm){
		if(password.equals(passwordConfirm)){	
			User user = userRepo.findByUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			userRepo.save(user);
			return true;
		} else {
			return false;
		}
	}
	
	public RefillRequest requestRefill(Principal principal){
		RefillRequest refill = new RefillRequest();
		User user = findByUsername(principal.getName());
		refill.setUser(user);
		refill.setDateCreated(new Date());
		return refill;
	}




}
