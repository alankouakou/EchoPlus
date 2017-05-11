package com.example.service;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.exception.InsufficientFundsException;
import com.example.model.RefillRequest;
import com.example.model.RequestStatus;
import com.example.model.User;
import com.example.repositories.RefillRepo;
import com.example.repositories.StatusRepo;

@Service
@Transactional
public class RefillService {

	@Autowired
	private RefillRepo refillRepo;

	@Autowired
	private UserService userService;

	@Autowired
	StatusRepo statusRepo;

	public RefillRequest findOne(Long id) {
		return refillRepo.findOne(id);
	}

	public List<RefillRequest> findAll() {
		return refillRepo.findAll(new Sort(Direction.DESC, "DateCreated"));
	}

	public Page<RefillRequest> findNewRefillRequests(Pageable p) {
		RequestStatus status = statusRepo.getOne(1l);
		return refillRepo.findByStatusOrderByDateCreatedDesc(status, p);
	}

	public Page<RefillRequest> findByUser(User user, Pageable page) {
		return refillRepo.findByUserOrderByDateCreatedDesc(user, page);
	}
	
	public Page<RefillRequest> findAll(Pageable page){
		return refillRepo.findAll(page);
	}
	

	public <S extends RefillRequest> S save(S arg0) {
		return refillRepo.save(arg0);
	}

	public RequestStatus getStatus(String name) {
		return statusRepo.findByName(name);
	}

	public void refill(String admin_account, Long refillId) throws InsufficientFundsException {
		// TODO Auto-generated method stub
		int adminBalance = 0;
		int newAdminBalance = 0;
		int newUserBalance = 0;

		RefillRequest refill = findOne(refillId);
		User user = userService.findByUsername(refill.getUser().getUsername());
		User admin = userService.findByUsername(admin_account);
		adminBalance = admin.getBalance();
		if (admin_account.equals(user.getUsername())) {
			newAdminBalance = adminBalance;
		} else {
			newAdminBalance = adminBalance - refill.getCredit();
		}
		newUserBalance = user.getBalance() + refill.getCredit();
		admin.setBalance(newAdminBalance);
		user.setBalance(newUserBalance);
		userService.save(admin);
		userService.save(user);
		refill.setStatus(getStatus("RECHARGED"));
		save(refill);
	}

}
