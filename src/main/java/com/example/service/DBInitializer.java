package com.example.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.exception.InsufficientFundsException;
import com.example.model.Group;
import com.example.model.RequestStatus;
import com.example.model.Role;
import com.example.model.User;
import com.example.repositories.GroupRepository;
import com.example.repositories.RoleRepository;
import com.example.repositories.StatusRepo;
import com.example.repositories.UserRepository;

@Service
@Transactional
public class DBInitializer {

	@Autowired
	UserRepository userRepo;

	@Autowired
	GroupRepository groupRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	StatusRepo statusRepo;

	@PostConstruct
	public void initUser() throws InsufficientFundsException {

		if (roleRepo.count() == 0) {
			Role roleAdmin = new Role("ROLE_ADMIN");
			roleRepo.save(roleAdmin);

			Role roleUser = new Role("ROLE_USER");
			roleRepo.save(roleUser);

			User user = new User("user", passwordEncoder.encode("user2016123"));
			user.setName("Invité");
			user.setBalance(10);
			user.setRole(roleUser);
			userRepo.save(user);

			Group userGroup = new Group();
			userGroup.setUser(user);
			userGroup.setName("Tous les contacts");
			groupRepo.save(userGroup);

			User admin = new User("admin", passwordEncoder.encode("admin2016123"));
			admin.setBalance(50);
			admin.setName("Administrateur");
			admin.setRole(roleAdmin);
			userRepo.save(admin);

			Group adminGroup = new Group();
			adminGroup.setUser(admin);
			adminGroup.setName("Tous les contacts");
			groupRepo.save(adminGroup);
		}
		
		if( statusRepo.count() == 0 ){
			RequestStatus nouveau = new RequestStatus("NEW");
			RequestStatus annulé = new RequestStatus("CANCELLED");
			RequestStatus rejeté = new RequestStatus("REJECTED");
			RequestStatus validé = new RequestStatus("RECHARGED");
			statusRepo.save(Arrays.asList(nouveau, annulé, rejeté, validé));
		}

	}
}
