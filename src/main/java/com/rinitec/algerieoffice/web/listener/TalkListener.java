package com.rinitec.algerieoffice.web.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Talk;
import com.rinitec.algerieoffice.services.company.manage.IManageService;
import com.rinitec.algerieoffice.services.inbox.ITalkService;
import com.rinitec.algerieoffice.ujson.TalkResponse;
import com.rinitec.algerieoffice.web.listener.events.OnDocumentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnTalkEvent;

@Component
public class TalkListener {

	private ITalkService talkService;
	private IManageService manageService;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	public TalkListener(ITalkService talkService, IManageService manageService, SimpMessagingTemplate messagingTemplate) {
		this.talkService = talkService;
		this.manageService = manageService;
		this.messagingTemplate = messagingTemplate;
	}
	
	@Async
	@EventListener
	public void pushTalk(final OnTalkEvent event) {
		final Long companyId = event.getCompanyId();
		if(manageService.hasNotificationTalk(companyId, event.getType())) {
			final Talk talk = talkService.addTalk(companyId, event.getType());
			final List<String> users = talkService.findAllEmailByCompanyId(companyId);
			final TalkResponse talkResponse = new TalkResponse(talk);
			for (final String user : users) {
				messagingTemplate.convertAndSendToUser(user, "/queue/talk", talkResponse);
			}
		}
	}
	
	@Async
	@EventListener
	public void pushTalk(final OnDocumentEvent event) {
		final Long companyId = event.getCompanyId();
		final TalkType type = event.getTalkType();
		if(manageService.hasNotificationTalk(companyId, type)) {
			final Talk talk = talkService.addTalk(companyId, type);
			final List<String> users = talkService.findAllEmailByCompanyId(companyId);
			final TalkResponse talkResponse = new TalkResponse(talk);
			for (final String user : users) {
				messagingTemplate.convertAndSendToUser(user, "/queue/talk", talkResponse);
			}
		}
	}
	
}
