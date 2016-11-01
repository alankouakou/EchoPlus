package com.example.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Group;
import com.example.repositories.GroupRepository;

@Controller
public class GroupController {
	
	@Autowired
	private GroupRepository groupRepository;
	
	@RequestMapping(value="/newgroup",method=RequestMethod.GET)
	public String viewGroup(Model model) {
		Group group = new Group();
		model.addAttribute("group", group);
		return "register-group";
	}
	
	@RequestMapping(value="/newgroup",method=RequestMethod.POST)
	public String saveGroup(@ModelAttribute("group") Group group){
		
		groupRepository.save(group);
		return "redirect:/listgroups";
	}	
	
	@RequestMapping(value="/listgroups",method=RequestMethod.GET)
	public String listGroups(Model model){
		ArrayList<Group> groups =   (ArrayList<Group>) groupRepository.findAll();
		model.addAttribute("groups", groups);
		return "listgroups";
	}
	

}
