package com.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.PasswordReset;
import com.example.model.RefillRequest;
import com.example.model.RequestStatus;
import com.example.repositories.StatusRepo;
import com.example.service.RefillService;
import com.example.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RefillService refillService;
	
	@GetMapping("/password/change")
	public String initPasswordChange(Model model, Principal principal){
		PasswordReset p = new PasswordReset();
		p.setUsername(principal.getName());
		model.addAttribute("p",p);
		return "change_password";
	}
	
	@PostMapping("/password/change")
	public String passwordReset(@ModelAttribute("p") PasswordReset p){
		System.out.println("new password: "+p.getPassword()+" confirm: "+p.getPasswordConfirm());
		if(userService.passwordReset(p.getUsername(), p.getPassword(), p.getPasswordConfirm())){
			return "reset_password_confirmed";
		} else {
			return "redirect:/users/password/change";
		}
	}
	
	@GetMapping("/refill")
	public String requestRefill(Model model, Principal principal){
		RefillRequest refill = userService.requestRefill(principal);
		model.addAttribute("refill", refill);
		return "refill_request"; 
	}
	
	@PostMapping("/refill")
	public String sendRefillRequest(@ModelAttribute("refill") RefillRequest refill){
		RequestStatus status_new = refillService.getStatus("NEW");
		refill.setStatus(status_new);
		refillService.save(refill);
		return "refill_request_confirmed";
	}
	
	
	

	
}
