package com.example.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Group;
import com.example.model.Person;
import com.example.model.User;

public interface GroupRepository extends JpaRepository<Group, Long> {

	public List<Group> findByUser(User user, Sort sort);
}
