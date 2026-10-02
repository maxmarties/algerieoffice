package com.rinitec.algerieoffice.services.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioService implements ITwilioService {
	
	@Value("${twilio.account.sid}")
    private String accountSid;
	
	@Value("${twilio.auth.token}")
    private String authToken;
	
	@Value("${twilio.phone.number}")
    private String phoneNumber;
	
	@Override
	public void sendCode(final String phone, final String code) {
		Twilio.init(accountSid, authToken);
		final String bodySMS = code.concat(" : saisissez ce code pour valider votre portable sur algerieoffice.net");
		final Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber(phoneNumber), bodySMS).create();
		//TODO COMPLETED
		System.out.println("Etat: " + message.getSid() + "\nStatu: " + message.getStatus());
	}
	
}
