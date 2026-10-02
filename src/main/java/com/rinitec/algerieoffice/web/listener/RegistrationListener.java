package com.rinitec.algerieoffice.web.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.mail.IEmailService;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.services.token.ITokenService;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;

@Component
public class RegistrationListener {

	private ITokenService tokenService;
	private IEmailService emailService;
	
	@Autowired
	public RegistrationListener(ITokenService tokenService, IEmailService emailService) {
		this.tokenService = tokenService;
		this.emailService = emailService;
	}
	
	@Async
	@EventListener
	public void sendRegisterConfirmation(final OnRegisterEvent event) {
		final User user = event.getUser();
		switch(event.getType()) {
		case confirmRegister: case updateLogin: case editUser: case addUser: 
			final String token = UUID.randomUUID().toString();
			tokenService.createVerificationTokenForUser(user, token);
			event.setToken(token);
			break;
		default:;
		}
		emailService.sendUnsubscribeMail(event);
	}
	
}
