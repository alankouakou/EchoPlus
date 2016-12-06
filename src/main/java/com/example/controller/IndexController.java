package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.MybootApplication;
import com.example.exception.InsufficientFundsException;
import com.example.model.User;
import com.example.model.Account;
import com.example.model.Group;
import com.example.model.Person;
import com.example.model.Sms;
import com.example.repositories.GroupRepository;
import com.example.repositories.PersonRepository;
import com.example.service.MessageService;
import com.example.service.UserService;
import com.example.util.SmsTool;

import infobip.api.client.SendSingleTextualSms;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;

@Controller

public class IndexController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;

	
	@Autowired
	private Account account;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private GroupRepository groupRepository;

	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		ArrayList<Group> groupes = (ArrayList<Group>) groupRepository.findAll();
		model.addAttribute("groupes", groupes);
		if(principal == null){
			model.addAttribute("username", "Invité");
		} else {
			model.addAttribute("username", principal.getName());
		}

		return "index";
	}

	@RequestMapping("/activites")
	public String acivites() {
		return "infoscompte";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String home() {
		return "Hello from Spring boot!";
	}

	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public String index(@ModelAttribute("person") Person p) {

		return "personview";
	}

	@RequestMapping("/compose")
	public String composeSms(Model model) {
		Sms message = new Sms();
		model.addAttribute("sms", message);
		return "compose";
	}

	@RequestMapping(value = "/testsend", method = RequestMethod.GET)
	public String testPage(@ModelAttribute("sms") Sms sms) {
		// Sms sms = new Sms();
		sms.setFrom("UN TEST");
		sms.setTo("09007718");
		sms.setText("Testing css!");
		return "confirmed";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendSms(@ModelAttribute("sms") Sms sms, Model model, Principal principal) throws InsufficientFundsException {
		ArrayList<String> destinataires = SmsTool.addPrefixToNumbers(sms.getTo());
		if (destinataires.size() > 0) {
			User user = new User();
			user = userService.findByUsername(principal.getName());
			SMSResponseDetails sentMessageInfo = messageService.sendSms(account, user, sms);
			model.addAttribute("sentMessageInfo", sentMessageInfo);
			model.addAttribute("balance",user.getBalance());
			System.out.println("Message ID: " + sentMessageInfo.getMessageId());
			System.out.println("Receiver: " + sentMessageInfo.getTo());
			System.out.println("SmsCount: " + sentMessageInfo.getSmsCount());
			System.out.println("Credit utilisé :" + destinataires.size() + " sms");			
			System.out.println("Solde du compte: "+user.getBalance());
			//System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
			System.out.println(destinataires.toString());
			return "confirmed";
		} else {
			return "compose";
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginForm(){
		return "login";
	}
	
	@RequestMapping("/403")
	public String forbidden(){
		return "forbidden";
	}
}
