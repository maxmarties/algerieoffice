package com.rinitec.algerieoffice.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.services.cloud.ITwilioService;
import com.rinitec.algerieoffice.services.user.account.IProfileService;
import com.rinitec.algerieoffice.web.listener.events.OnValidatePhoneEvent;

@Component
public class ValidatePhoneListener {

	private ITwilioService twilioService;
	private IProfileService profileService;
	
	@Autowired
	public ValidatePhoneListener(ITwilioService twilioService, IProfileService profileService) {
		this.twilioService = twilioService;
		this.profileService = profileService;
	}

	@Async
	@EventListener
	public void pushCode(final OnValidatePhoneEvent event) {
		final String phone = profileService.getPhoneProfile(event.getUserId());
		if(phone != null) {
			twilioService.sendCode("+213".concat(phone), event.getToken());
		}
	}
	
}
