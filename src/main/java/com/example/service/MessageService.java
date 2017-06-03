package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.InsufficientFundsException;
import com.example.model.Account;
import com.example.model.ResponseDetails;
import com.example.model.Sms;
import com.example.model.User;
import com.example.util.SmsTool;

import infobip.api.client.SendSingleTextualSms;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;

@Service
@Transactional
public class MessageService {

	@Autowired
	private UserService userService;

	@Autowired
	private ResponseDetailsService rdService;

	private List<ResponseDetails> rds= new ArrayList<ResponseDetails>();
	private ArrayList<String> destinataires;

	public SMSResponseDetails sendSms(Account account, User user, Sms sms) throws InsufficientFundsException {

		// user=userService.findByUsername(user.getUsername());
		SMSResponseDetails sentMessageInfo = new SMSResponseDetails();
		List<SMSResponseDetails> msgResponses;
		if(sms.getTo().isEmpty()){
			destinataires = SmsTool.addPrefixToNumbers(sms.getGroup().getContacts());
		} else {
		 destinataires = SmsTool.addPrefixToNumbers(sms.getTo());
		}
		if (user.getBalance() > destinataires.size()) {

			System.out.println(account.getLogin() + ":" + account.getPassword());
			SendSingleTextualSms client = new SendSingleTextualSms(
					new BasicAuthConfiguration(account.getLogin(), account.getPassword()));

			SMSTextualRequest requestBody = new SMSTextualRequest();
			requestBody.setFrom(sms.getFrom());

			requestBody.setTo(destinataires);
			requestBody.setText(sms.getText());
			// Mise à jour du solde du compte
			SMSResponse response = client.execute(requestBody);
			sentMessageInfo = response.getMessages().get(0);
			msgResponses = response.getMessages();

			for (SMSResponseDetails mi : msgResponses) {
				ResponseDetails rd = new ResponseDetails(mi.getMessageId(), mi.getSmsCount(), mi.getStatus().getName(),
						sms.getFrom(), mi.getTo());
				rds.add(rd);
			}
			// Enregistrement details envoi
			rdService.save(rds);
			
			user.charge(destinataires.size() * sentMessageInfo.getSmsCount());

			userService.save(user);
			System.out.println("Message envoyé! Nouveau solde :" + user.getBalance());
			//System.out.println(new BasicAuthConfiguration(account.getLogin(), account.getPassword()).getAuthorizationHeader());
			System.out.println("----- details ------");
			for (ResponseDetails r : rds) {
				System.out.println("Details reponse:" + r);
			}
		}
		return sentMessageInfo;
	}

}
