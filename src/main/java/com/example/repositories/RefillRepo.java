package com.example.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.RefillRequest;
import com.example.model.RequestStatus;
import com.example.model.User;

public interface RefillRepo extends JpaRepository<RefillRequest, Long>{

	List<RefillRequest> findByUserOrderByDateCreatedDesc(User user);
	
	Page<RefillRequest> findByUser(User user, Pageable page);
	
	List<RefillRequest> findAll(Sort sort);
	
	Page<RefillRequest> findAll(Pageable page);
	
	Page<RefillRequest> findByUserOrderByDateCreatedDesc(User user, Pageable page);

	Page<RefillRequest> findByStatusOrderByDateCreatedDesc(RequestStatus status, Pageable page);

}
