package com.example.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.exception.InsufficientFundsException;
import com.example.model.RefillRequest;
import com.example.model.User;
import com.example.service.RefillService;
import com.example.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RefillService refillService;

	@GetMapping("")
	public String index(){
		return "admin";
	}
	
	@GetMapping("/refills")
	public String viewRefillRequests(Model model, Principal principal){
		User user = userService.findByUsername(principal.getName());
		List<RefillRequest> refills = refillService.findNewRefillRequests();
		model.addAttribute("balance",user.getBalance());
		model.addAttribute("refills", refills);		
		return "admin_refill_requests";
	}

	@GetMapping("/refills_history")
	public String viewAllRefillRequests(Model model, Principal principal){
		User user = userService.findByUsername(principal.getName());
		List<RefillRequest> refills = refillService.findAll();
		model.addAttribute("balance",user.getBalance());
		model.addAttribute("refills", refills);		
		return "admin_refill_requests";
	}
	
	@RequestMapping("/refill/{refillId}")
	public String rechargeUserAccount(Principal principal,@PathVariable("refillId") Long refillId) throws InsufficientFundsException {
		refillService.refill(principal.getName(), refillId);
		return "redirect:/admin/refills";
	}

}
