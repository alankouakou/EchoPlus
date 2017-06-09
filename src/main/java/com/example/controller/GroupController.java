package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.Group;
import com.example.model.Person;
import com.example.model.User;
import com.example.service.GroupService;
import com.example.service.PersonService;
import com.example.service.UserService;

@SessionAttributes("user")
@Controller
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PersonService personService;
	
	@ModelAttribute("user")
	public User getUser(Principal principal){
		return userService.findByUsername(principal.getName());
	}
	
	@ModelAttribute("groups")
	public List<Group> getMyGroups(Principal principal){
		List<Group> groups = groupService.findByUser(getUser(principal), new Sort("name"));
		return groups;
	}
	

	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public String newGroup(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Group group = new Group();
		group.setUser(user);
		model.addAttribute("group", group);
		return "register-group";
	}
	
@RequestMapping(value="/{group}",method=RequestMethod.GET)
public String editGroup(@ModelAttribute("group") Group groupe){
	return "register-group";
}
	@RequestMapping(value={"/new","/{group}"},method=RequestMethod.POST)
	public String saveGroup(@Valid @ModelAttribute("group") Group group, BindingResult result){
		if(result.hasErrors()){
			return "register-group";
		} else {
			groupService.save(group);
			return "redirect:/groups/list";
		}
		
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
		model.addAttribute("group",groupe);
		User user = userService.findByUsername(principal.getName());
		ArrayList<Group> groups =   (ArrayList<Group>) groupService.findByUser(user, new Sort(Direction.ASC,"name"));
		model.addAttribute("groups", groups);

		return "membres"; 
	}
	
	@RequestMapping(value="/delete/{group}")
	public String delete(Group group){
		for(Person p: group.getMembers()){
			p.removeGroup(group);
		}
		groupService.delete(group);
		return "redirect:/groups";
	}

	@RequestMapping(value="/remove/{group}/{person}")
	public String delete(Group group, Person person){
			person.removeGroup(group);
			personService.save(person);
		return "redirect:/groups";
	}
	
	
	@RequestMapping(value="/{group}/assign-orphans",method=RequestMethod.GET)
	public String assignOrphans(@ModelAttribute("group") Group group, Principal principal){
		User user = userService.findByUsername(principal.getName());
		List<Person> contactsAll = personService.findByUser(user);
		List<Person> contactsRecupérés = new ArrayList<Person>();
		for(Person p: contactsAll){
			if(p.getGroups().size() == 0){
				p.addGroup(group);
				contactsRecupérés.add(p);
			}
			
		personService.save(contactsAll);
		}
		System.out.println("Groupe : "+group);
		System.out.println(contactsRecupérés);
		return "redirect:/groups/membres/"+group.getId();
	}
	
	@RequestMapping(value = {"membres/{groupId}/new","membres/{groupId}/{person}"}, method = RequestMethod.POST)
	public String savePerson(@PathVariable("groupId") int groupId, @ModelAttribute("person") Person person) {
		//person.addGroup(group);
		personService.save(person);
		return "redirect:/groups/membres/"+groupId;
	}
	
	@RequestMapping(value="membres/{groupId}/{person}",method=RequestMethod.GET)
	public String editPerson(Model model, @PathVariable("groupId") int groupId, @ModelAttribute("person") Person person, Principal principal){
		System.out.println(person.getGroups());
		return "register-member";
	}	

}
