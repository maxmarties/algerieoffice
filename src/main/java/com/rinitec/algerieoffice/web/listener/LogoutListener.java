package com.rinitec.algerieoffice.web.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.services.user.IUserService;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvents;

@Component
public class LogoutListener {

	private IUserService userService;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	public LogoutListener(IUserService userService, SimpMessagingTemplate messagingTemplate) {
		this.userService = userService;
		this.messagingTemplate = messagingTemplate;
	}
	
	@Async
	@EventListener
	public void pushLogout(final OnLogoutEvent event) {
		if(!StringUtils.isEmpty(event.getEmail())) {
			messagingTemplate.convertAndSendToUser(event.getEmail(), "/queue/pushlogout", "");
		} else if(event.getUsersId() != null) {
			final List<String> emails = userService.findAllEmail(event.getUsersId());
			for (final String email : emails) {
				messagingTemplate.convertAndSendToUser(email, "/queue/pushlogout", "");
			}
		}
	}
	
	@Async
	@EventListener
	public void pushLogouts(final OnLogoutEvents event) {
		for (final String email : event.getEmails()) {
			messagingTemplate.convertAndSendToUser(email, "/queue/pushlogout", "");
		}
	}
	
}
