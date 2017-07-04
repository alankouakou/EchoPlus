package com.example.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.PasswordReset;
import com.example.model.RefillRequest;
import com.example.model.RequestStatus;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.RefillService;
import com.example.service.RoleService;
import com.example.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	RoleService roleSvc;
	
	@Autowired
	private RefillService refillService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@GetMapping
	public String listUsers(Model model){
		List<User> users = userService.findAll();
		model.addAttribute("users",users);
		return "listusers";
	}
	
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
	
	
	@RequestMapping("/new")
	public String createUser(Model model){
		User user = new User();
		model.addAttribute("user", user);
		return "register-user";
		
	}
	
	@PostMapping({"/new","/save"})
	public String saveUser(@ModelAttribute("user") User user){
			String password = user.getPassword();
			Role userRole = roleSvc.findByName("ROLE_USER");
			user.setRole(userRole);
			user.setEnabled(false);
			User u=userService.save(user);
			System.out.println("Compte créé! E-mail: "+u.getEmail()+"password: "+password);
			
			return "redirect:/";
	}
	
	

	
}
