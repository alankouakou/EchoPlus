package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.model.Group;
import com.example.model.Person;
import com.example.model.User;
import com.example.repositories.GroupRepository;
import com.example.repositories.PersonRepository;

@Transactional
@Service
public class GroupService {
	
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	PersonRepository personRepo;
	
	public long count() {
		return groupRepository.count();
	}

	public void delete(Group arg0) {
		groupRepository.delete(arg0);
	}

	public void delete(Long arg0) {
		groupRepository.delete(arg0);
	}

	public void deleteAll() {
		groupRepository.deleteAll();
	}

	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	public List<Group> findAll(Sort arg0) {
		return groupRepository.findAll(arg0);
	}

	public Group findOne(Long arg0) {
		return groupRepository.findOne(arg0);
	}
	
	public <S extends Group> S save(S arg0) {
		return groupRepository.save(arg0);
	}
	
	
	public void addMembers(Group group, List<Person> persons){
		
		for(Person p:persons){
			p.addGroup(group);			
		}
		
		personRepo.save(persons);
	}
	
	public void removeMembers(Group group, List<Person> persons){
		for(Person person:persons){
			person.removeGroup(group);
		}
		
		personRepo.save(persons);
	}

	public List<Group> findByUser(User user, Sort sort) {
		return groupRepository.findByUser(user, sort);
	}
	
	public List<Person> findMembers(long id){
		Group groupe = groupRepository.findOne(id);
		return personRepo.findByGroupsContaining(groupe, new Sort(Sort.Direction.ASC,"lastName","firstName"));
	}
	

}
