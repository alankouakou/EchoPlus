package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ResponseDetails;
import com.example.repositories.ResponseDetailsRepo;

@Service
@Transactional
public class ResponseDetailsService {
	
	@Autowired
	private ResponseDetailsRepo rdRepo;
	
	public List<ResponseDetails> findAll(){
		return rdRepo.findAll();
	}

	public void save(ResponseDetails rd){
		rdRepo.save(rd);
	}

	public void save(List<ResponseDetails> rds){
		rdRepo.save(rds);
	}
	
	public ResponseDetails findByMessageId(String messageId){
		return rdRepo.findByMessageId(messageId);
	}
	
	public void delete(List<ResponseDetails> rds){
		rdRepo.delete(rds);
	}

	public void delete(Long id){
		rdRepo.delete(id);
	}
	
	
}
