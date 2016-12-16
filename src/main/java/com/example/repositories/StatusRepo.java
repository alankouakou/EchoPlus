package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.RequestStatus;

public interface StatusRepo extends JpaRepository<RequestStatus, Long>{

	public RequestStatus findByName(String name);

}
