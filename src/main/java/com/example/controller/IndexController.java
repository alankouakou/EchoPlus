package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.exception.InsufficientFundsException;
import com.example.model.Account;
import com.example.model.Group;
import com.example.model.Person;
import com.example.model.RefillRequest;
import com.example.model.ResponseDetails;
import com.example.model.Sms;
import com.example.model.User;
import com.example.repositories.GroupRepository;
import com.example.repositories.PersonRepository;
import com.example.service.MessageService;
import com.example.service.RefillService;
import com.example.service.UserService;
import com.example.util.SmsTool;

import infobip.api.client.GetSentSmsLogs;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.logs.SMSLog;
import infobip.api.model.sms.mt.logs.SMSLogsResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;

@Controller
@SessionAttributes("user")
public class IndexController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Autowired
	private RefillService refillService;

	@Autowired
	private Account account;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private GroupRepository groupRepository;

	List<String> destinataires;
	private User user;
	
	@Value("${account.login}")
	private String login;
	@Value("${account.password}")
	private String password;

	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		if (principal == null) {
			model.addAttribute("username", "Invité");
		} else {
			user = userService.findByUsername(principal.getName());
			List<Group> groupes = (ArrayList<Group>) groupRepository.findByUser(user,
					new Sort(Sort.Direction.ASC, "name"));
			model.addAttribute("groupes", groupes);
			model.addAttribute("username", principal.getName());
			model.addAttribute("user", user);

		}

		return "index";
	}

	@RequestMapping("/activites")
	public String activites(Model model, Pageable p, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Page<RefillRequest> refills = refillService.findByUser(user, p);
		model.addAttribute("balance", user.getBalance());
		model.addAttribute("page", refills);
		return "infoscompte";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public String home() {
		return "Hello from Spring boot!";
	}

	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public String index(@ModelAttribute("person") Person p, Principal principal) {

		return "personview";
	}

	@RequestMapping(value="/compose",method=RequestMethod.GET)
	public String composeSms(Model model, Principal principal) {
		Sms message = new Sms();
		String sender = StringUtils.upperCase(principal.getName());
		message.setFrom(sender);
		if (principal != null) {
			user = userService.findByUsername(principal.getName());
			String senderName = StringUtils.capitalize(user.getSenderName()); 
			message.setFrom(senderName);
			List<Group> groupes = (ArrayList<Group>) groupRepository.findByUser(user,
					new Sort(Sort.Direction.ASC, "name"));
			model.addAttribute("groupes", groupes);
		}

		model.addAttribute("sms", message);
		return "compose";
	}

	@RequestMapping(value = "/testsend", method = RequestMethod.GET)
	public String testPage(@ModelAttribute("sms") Sms sms, Principal principal) {
		// Sms sms = new Sms();
		sms.setFrom("UN TEST");
		sms.setTo("09007718");
		sms.setText("Testing css!");
		return "confirmed";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendSms(@ModelAttribute("sms") Sms sms, Model model, Principal principal)
			throws InsufficientFundsException {
		if (sms.getTo().isEmpty()) {
			destinataires = SmsTool.addPrefixToNumbers(sms.getGroup().getContacts());
		} else {
			destinataires = SmsTool.addPrefixToNumbers(sms.getTo());
		}
		if (destinataires.size() > 0) {
			User user = new User();
			user = userService.findByUsername(principal.getName());
			SMSResponseDetails sentMessageInfo = messageService.sendSms(account, user, sms);
			model.addAttribute("sentMessageInfo", sentMessageInfo);
			model.addAttribute("balance", user.getBalance());
			logDetails(user, sentMessageInfo,principal);
			return "confirmed";
		} else {
			return "compose";
		}
	}

	private void logDetails(User user, SMSResponseDetails sentMessageInfo, Principal principal) {
		System.out.println("Message ID: " + sentMessageInfo.getMessageId());
		System.out.println("Receiver: " + sentMessageInfo.getTo());
		System.out.println("SmsCount: " + sentMessageInfo.getSmsCount());
		System.out.println("Credit utilisé :" + destinataires.size() + " sms");
		System.out.println("Solde du compte: " + user.getBalance());

		// System.out.println("Message status: " +
		// sentMessageInfo.getStatus().getName());
		System.out.println(destinataires.toString());
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login2";
	}

	@RequestMapping("/403")
	public String forbidden(Principal principal) {
		return "403";
	}
	
	
	@RequestMapping("/logs")
	public String getLogs(Model model, Principal principal)
	{
		System.out.println("login: "+login+", password: "+password);
        GetSentSmsLogs client = new GetSentSmsLogs(new BasicAuthConfiguration(login, password));
        List<ResponseDetails> rdList = new ArrayList();
        SMSLogsResponse responses = client.execute(null, null, null, null, null, null, null, 500, null, null);
        for (SMSLog result:  responses.getResults()) {
            //SMSLog result = responses.getResults().get(i);
            ResponseDetails rd = new ResponseDetails();
            rd.setMessageId(result.getMessageId());
            rd.setSentAt(result.getSentAt());
            rd.setSender(result.getFrom());
            rd.setTo(result.getTo());
            rd.setStatus(result.getStatus().getName());
            rdList.add(rd);
            
            System.out.println("Message ID: " + result.getMessageId());
            System.out.println("Sent at: " + result.getSentAt());
            System.out.println("Sender: " + result.getFrom());
            System.out.println("Receiver: " + result.getTo());
            System.out.println("Message text: " + result.getText());
            System.out.println("Status: " + result.getStatus().getName());
            System.out.println("Price: " + result.getPrice().getPricePerMessage() + " " + result.getPrice().getCurrency());
         	
        }
        
		model.addAttribute("responses",rdList);
		return "logs";
	}
	
	
}
