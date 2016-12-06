package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Group;
import com.example.model.User;
import com.example.service.GroupService;
import com.example.service.UserService;

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
	

}
