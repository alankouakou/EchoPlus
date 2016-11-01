package com.example.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.InsufficientFundsException;
import com.example.model.Account;
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
UserService userService;


	public SMSResponseDetails sendSms(Account account, User user, Sms sms) throws InsufficientFundsException {
		
		user=userService.findByEmail("admin@grio.com");
		SMSResponseDetails sentMessageInfo = new SMSResponseDetails();
		ArrayList<String> destinataires = SmsTool.addPrefixToNumbers(sms.getTo());
		
		
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
			user.charge(destinataires.size() * sentMessageInfo.getSmsCount());
			userService.save(user);
			System.out.println("Message envoyé! Nouveau solde :" + user.getBalance());
		}
		return sentMessageInfo;
	}

}
