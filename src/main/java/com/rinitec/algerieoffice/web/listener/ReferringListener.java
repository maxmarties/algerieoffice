package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.services.analytic.IReferringService;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompletedEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringPostEvent;

@Component
public class ReferringListener {

	private IReferringService referringService;
	
	@Autowired
	public ReferringListener(IReferringService referringService) {
		this.referringService = referringService;
	}
	
	@Async
	@EventListener
	public void pushReferringCompany(final OnReferringCompanyEvent event) {
		referringService.incrementReferringCompanies(event);
	}
	
	@Async
	@EventListener
	public void pushReferringPost(final OnReferringPostEvent event) {
		referringService.incrementReferringPosts(event);
	}
	
	@Async
	@EventListener
	public void pushReferringCompleted(final OnReferringCompletedEvent event) {
		referringService.updateReferringCompleted(event);
	}
	
}
