package com.example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {

}
