package com.example.controller;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Person;
import com.example.model.Sms;
import com.example.util.SmSTool;

import infobip.api.client.SendSingleTextualSms;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;

@Controller
public class IndexController {

	private static final String USERNAME = "PHONEIVOIRE";
	private static final String PASSWORD = "zAgg2016";

	@RequestMapping("/")
	public String index() {
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

	@RequestMapping("/register")
	public String showPerson(Model model) {
		Person p = new Person();
		model.addAttribute("person", p);
		return "register-form";
	}

	@RequestMapping("/compose")
	public String composeSms(Model model) {
		Sms message = new Sms();
		model.addAttribute("sms", message);
		return "compose";
	}
	
	@RequestMapping(value = "/testsend", method = RequestMethod.GET)
	public String testPage(@ModelAttribute("sms") Sms sms){
		// Sms sms = new Sms();
		sms.setFrom("UN TEST");
		sms.setTo("09007718");
		sms.setText("Testing css!");
		return "confirmed";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendSms(@ModelAttribute("sms") Sms sms) {
		//SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));

		//SMSTextualRequest requestBody = new SMSTextualRequest();
		//requestBody.setFrom(sms.getFrom());
		
		ArrayList<String> destinataires = new ArrayList<String>();
		destinataires = SmSTool.addPrefixToNumbers(sms.getTo());

		//requestBody.setTo(destinataires);
		//requestBody.setText(sms.getText());
		if (destinataires.size() > 0) {
			System.out.println(destinataires.toString());
			/*
			SMSResponse response = client.execute(requestBody);
			SMSResponseDetails sentMessageInfo = response.getMessages().get(0);
			System.out.println("Message ID: " + sentMessageInfo.getMessageId());
			System.out.println("Receiver: " + sentMessageInfo.getTo());
			System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
			*/
			return "confirmed";
		} else {
			return "compose";
		}
	}
	
	


}
