package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ResponseDetails;

@Repository
public interface ResponseDetailsRepo extends JpaRepository<ResponseDetails, Long>{

	ResponseDetails findByMessageId(String messageId);

}
