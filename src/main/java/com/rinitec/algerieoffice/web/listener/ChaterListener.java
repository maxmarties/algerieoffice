package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.services.inbox.IChaterService;
import com.rinitec.algerieoffice.web.listener.events.OnChaterEvent;

@Component
public class ChaterListener {

	private IChaterService chaterService;
	
	@Autowired
	public ChaterListener(IChaterService chaterService) {
		this.chaterService = chaterService;
	}
	
	@Async
	@EventListener
	public void pushChatter(final OnChaterEvent event) {
		chaterService.addChater(event);
	}
	
}
