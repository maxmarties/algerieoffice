package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.services.admins.dashboard.IJournalAdminService;
import com.rinitec.algerieoffice.services.company.dashboard.IJournalCompanyService;
import com.rinitec.algerieoffice.services.user.dashboard.IJournalUserService;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;

@Component
public class JournalListener {

	private IJournalUserService journalUserService;
	private IJournalCompanyService journalCompanyService;
	private IJournalAdminService journalAdminService;
	
	@Autowired
	public JournalListener(IJournalUserService journalUserService, IJournalCompanyService journalCompanyService, IJournalAdminService journalAdminService) {
		this.journalUserService = journalUserService;
		this.journalCompanyService = journalCompanyService;
		this.journalAdminService = journalAdminService;
	}
	
	@Async
	@EventListener
	public void pushJournalUser(final OnJournalUserEvent event) {
		journalUserService.addJournalUser(event);
	}
	
	@Async
	@EventListener
	public void pushJournalCompany(final OnJournalCompanyEvent event) {
		journalCompanyService.addJournalCompany(event);
	}
	
	@Async
	@EventListener
	public void pushJournalAdmin(final OnJournalAdminEvent event) {
		journalAdminService.addJournalAdmin(event);
	}
	
}
