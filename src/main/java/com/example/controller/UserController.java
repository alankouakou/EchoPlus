package com.example.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Secured("ROLE_ADMIN")
	@GetMapping
	public String listUsers(Model model, Principal principal){
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
	public String passwordReset(@ModelAttribute("p") PasswordReset p, Principal principal){
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
	public String sendRefillRequest(@ModelAttribute("refill") RefillRequest refill, Principal principal){
		RequestStatus status_new = refillService.getStatus("NEW");
		refill.setStatus(status_new);
		refillService.save(refill);
		return "refill_request_confirmed";
	}
	
	
	@RequestMapping("/new")
	public String createUser(Model model, Principal principal){
		User user = new User();
		model.addAttribute("user", user);
		return "register-user";
		
	}
	
	@PostMapping({"/new","/save"})
	public String saveUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult result, Principal principal){
		if(result.hasErrors()){
			return "register-user";
		} else {
			
			Role userRole = roleSvc.findByName("ROLE_USER");
			user.setRole(userRole);
			user.setEnabled(true);
			
			User u=userService.save(user);
			model.addAttribute("user",u);
			System.out.println("Compte créé! Login: "+user.getUsername()+"E-mail: "+u.getEmail());
			
			return "account_created";
		}
	}
	
	@RequestMapping("suspend/{username}")
	public String lockUser(@PathVariable("username") String username, Principal principal){
		User u=userService.suspend(username);
		System.out.println(u);
		return "redirect:/users";
	}

	@RequestMapping("unsuspend/{username}")
	public String unlockUser(@PathVariable("username") String username, Principal principal){
		User u=userService.unsuspend(username);
		System.out.println(u);
		return "redirect:/users";
	}
	
	
	@GetMapping("/edit/{username}")
	public String editUser(Model model, @PathVariable("username") String username, Principal principal){
		User u = userService.findByUsername(username);
		List<Role> roles = roleSvc.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("user", u);
		return "edit-user";
	}
	
	
	@PostMapping("/save_changes")
	public String saveUser(@ModelAttribute("role") Role role, @ModelAttribute("user") User u, Principal principal){
		//Role role = roleSvc.findByName(roleName);
		u.setRole(role);
		User user = userService.updateInfos(u);
		System.out.println("login: "+user.getLogin()+", statut: "+user.getEnabled());
		return "redirect:/users";
	}

	
}
