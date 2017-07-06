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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.Group;
import com.example.model.Person;
import com.example.model.User;
import com.example.service.GroupService;
import com.example.service.PersonService;
import com.example.service.UserService;

@SessionAttributes("user")
@Controller
@RequestMapping("/contacts")
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@ModelAttribute("groups")
	private List<Group> listGroups(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		return groupService.findByUser(user, new Sort(Direction.ASC, "name"));
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createPerson(Model model, Principal principal) {
		Person p = new Person();
		User user = userService.findByUsername(principal.getName());
		p.setUser(user);
		model.addAttribute("person", p);
		return "register-form";
	}

	@RequestMapping(value = { "/new", "/{person}" }, method = RequestMethod.POST)
	public String savePerson(@Valid @ModelAttribute("person") Person person, BindingResult result,
			Principal principal) {
		if (result.hasErrors()) {
			return "register-form";
		} else {
			personService.save(person);
			return "redirect:/contacts/";
		}

	}

	@RequestMapping(value = "/{person}", method = RequestMethod.GET)
	public String editPerson(@ModelAttribute("person") Person person, Principal principal) {
		return "register-form";
	}

	@RequestMapping(value = "/delete/{person}", method = RequestMethod.GET)
	public String delete(Person person, Principal principal) {
		if (principal != null) {
			person.setGroups(null);
			personService.delete(person.getId());
		}
		return "redirect:/contacts";
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String listContacts(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ArrayList<Person> persons = (ArrayList<Person>) personService.findByUser(user,
				new Sort(Sort.Direction.ASC, "firstName", "lastName"));
		model.addAttribute("persons", persons);
		return "listcontacts";
	}

}
