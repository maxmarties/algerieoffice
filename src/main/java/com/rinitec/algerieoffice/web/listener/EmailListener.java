package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.mail.IEmailService;
import com.rinitec.algerieoffice.services.company.ICompanyService;
import com.rinitec.algerieoffice.services.company.manage.IManageService;
import com.rinitec.algerieoffice.services.user.IUserService;
import com.rinitec.algerieoffice.services.user.alerts.IAlertSettingService;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnEmailEvent;

@Component
public class EmailListener {

	private IUserService userService;
	private ICompanyService companyService;
	private IEmailService emailService;
	private IManageService manageService;
	private IAlertSettingService settingService;
	
	@Autowired
	public EmailListener(IUserService userService, ICompanyService companyService, IEmailService emailService, 
			IManageService manageService, IAlertSettingService settingService) {
		this.userService = userService;
		this.companyService = companyService;
		this.emailService = emailService;
		this.manageService = manageService;
		this.settingService = settingService;
	}
	
	private final boolean hasNotificationMail(final Long id, final EmailType type) {
		switch(type) {
		case contacts: case quotes: case ads: case infos: case jobs: return manageService.hasNotificationMail(id, type);
		default: return settingService.hasNotificationMail(id, type);
		}
	}
	
	private final String findNotificationMail(final Long id, final EmailType type) {
		switch(type) {
		case contacts: case quotes: case ads: case infos: case jobs: return companyService.findCompanymail(id);
		default: return userService.findUsermail(id);
		}
	}
	
	@Async
	@EventListener
	public void sendNotificationMail(final OnEmailEvent event) {
		if(hasNotificationMail(event.getId(), event.getType())) {
			final String email = findNotificationMail(event.getId(), event.getType());
			if(!StringUtils.isEmpty(email)) {
				event.setEmail(email);
				emailService.sendSubscribeMail(event);
			}
		}
	}
	
	@Async
	@EventListener
	public void sendNotificationAlert(final OnAlertEvent event) {
		if(settingService.hasNotificationMail(event.getAlert().getUserId(), EmailType.alerts)) {
			emailService.sendAlertMail(event);
		}
	}
	
}
