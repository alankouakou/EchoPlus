package com.example.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.RefillRequest;
import com.example.model.RequestStatus;
import com.example.model.User;

public interface RefillRepo extends JpaRepository<RefillRequest, Long>{

	List<RefillRequest> findByUserOrderByDateCreatedDesc(User user);
	
	List<RefillRequest> findAll(Sort sort);

	List<RefillRequest> findByStatusOrderByDateCreatedDesc(RequestStatus status);

}
