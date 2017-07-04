package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepo;

	public long count() {
		return roleRepo.count();
	}

	public void delete(Iterable<? extends Role> arg0) {
		roleRepo.delete(arg0);
	}

	public void delete(Long arg0) {
		roleRepo.delete(arg0);
	}

	public void delete(Role arg0) {
		roleRepo.delete(arg0);
	}

	public void deleteAll() {
		roleRepo.deleteAll();
	}

	public boolean exists(Long arg0) {
		return roleRepo.exists(arg0);
	}

	public List<Role> findAll() {
		return roleRepo.findAll();
	}

	public List<Role> findAll(Iterable<Long> arg0) {
		return roleRepo.findAll(arg0);
	}

	public Role findOne(Long arg0) {
		return roleRepo.findOne(arg0);
	}

	public List<Role> save(Iterable<Role> arg0) {
		return roleRepo.save(arg0);
	}

	public Role save(Role arg0) {
		return roleRepo.save(arg0);
	}

	public Role findByName(String name) {
		return roleRepo.findByName(name);
	}
	
	

}
