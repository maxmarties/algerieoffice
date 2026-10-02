package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.services.inbox.ISupportService;
import com.rinitec.algerieoffice.web.listener.events.OnSupportEvent;
import com.rinitec.algerieoffice.web.modal.inbox.SupportPush;

@Component
public class SupportListener {

	private MessageSource messages;
	private ISupportService supportService;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	public SupportListener(MessageSource messages, ISupportService supportService, SimpMessagingTemplate messagingTemplate) {
		this.messages = messages;
		this.supportService = supportService;
		this.messagingTemplate = messagingTemplate;
	}
	
	@Async
	@EventListener
	public void pushSupport(final OnSupportEvent event) {
		final String message = messages.getMessage("txt.inbox.support".concat(String.valueOf(event.getType())), null, event.getLocale());
		final SupportPush supportPush = supportService.addSupportHelp(event.getUserId(), message);
		messagingTemplate.convertAndSendToUser(event.getEmail(), "/queue/support", supportPush);
	}
	
}
