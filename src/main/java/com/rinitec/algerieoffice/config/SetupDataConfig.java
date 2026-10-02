package com.rinitec.algerieoffice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.services.admins.ISetupDataService;

@Component
public class SetupDataConfig implements ApplicationListener<ContextRefreshedEvent> {

	/*	ignore setup = true	*/
	private boolean alreadySetup = true;

	private ISetupDataService setupDataService;
	
	@Autowired
	public SetupDataConfig(ISetupDataService setupDataService) {
		this.setupDataService = setupDataService;
	}
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
            return;
        }
		setupDataService.initData();
		setupDataService.initTemp();
		this.alreadySetup = true;
	}
	
}
