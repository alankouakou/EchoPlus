package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.Group;
import com.example.model.Person;
import com.example.model.User;
import com.example.service.GroupService;
import com.example.service.UserService;

@SessionAttributes("user")
@Controller
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	

	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String viewGroup(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Group group = new Group();
		group.setUser(user);
		model.addAttribute("group", group);
		return "register-group";
	}
	
	@RequestMapping(value="/new",method=RequestMethod.POST)
	public String saveGroup(@ModelAttribute("group") Group group){
		groupService.save(group);
		return "redirect:/groups/list";
	}	
	
	@RequestMapping(value={"/list",""},method=RequestMethod.GET)
	public String listGroups(Model model, Principal principal){
		User user = userService.findByUsername(principal.getName());
		ArrayList<Group> groups =   (ArrayList<Group>) groupService.findByUser(user, new Sort(Direction.ASC,"name"));
		model.addAttribute("groups", groups);
		return "listgroups";
	}
	
	
	@RequestMapping(value="/membres/{id}",method=RequestMethod.GET)
	public String membres(Model model, @PathVariable("id") long id, Principal principal){
		Group groupe = groupService.findOne(id);
		List<Person> membres = groupService.findMembers(id);
		model.addAttribute("persons", membres);
		User user = userService.findByUsername(principal.getName());
		ArrayList<Group> groups =   (ArrayList<Group>) groupService.findByUser(user, new Sort(Direction.ASC,"name"));
		model.addAttribute("groups", groups);

		return "membres"; 
	}

}
