package com.rinitec.algerieoffice.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.services.user.alerts.IAlertPostService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostResult;

@Service
public class AlertPurgeTask {
	private static final int ALERT_LIMIT = 100;

	private IAlertPostService alertService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AlertPurgeTask(IAlertPostService alertService, ApplicationEventPublisher eventPublisher) {
		this.alertService = alertService;
		this.eventPublisher = eventPublisher;
	}
	
	private final void purgeNotificationAlert(final List<AlertPostResult> alerts, int type) {
		if(!alerts.isEmpty()) {
			for (final AlertPostResult alert : alerts) {
				eventPublisher.publishEvent(new OnAlertEvent(alert, type));
			}
		}
	}
	
	@Scheduled(cron = "${purge.cron.alert.post}", zone = "Europe/Paris")
	public void purgeAlertPost() {
		final int currDay = ParseUtil.getCurrDay();
		if(currDay <= 5) {
			final List<AlertPostResult> alerts = alertService.findLastAlertPost(DocumentType.post, currDay, ALERT_LIMIT);
			purgeNotificationAlert(alerts, 1);
		}
	}
	
	@Scheduled(cron = "${purge.cron.alert.ads}", zone = "Europe/Paris")
	public void purgeAlertAnnonce() {
		final int currDay = ParseUtil.getCurrDay();
		if(currDay <= 5) {
			final List<AlertPostResult> alerts = alertService.findLastAlertPost(DocumentType.annonce, currDay, ALERT_LIMIT);
			purgeNotificationAlert(alerts, 2);
		}
	}
	
	@Scheduled(cron = "${purge.cron.alert.event}", zone = "Europe/Paris")
	public void purgeAlertEvent() {
		final int currDay = ParseUtil.getCurrDay();
		if(currDay <= 5) {
			final List<AlertPostResult> alerts = alertService.findLastAlertPost(DocumentType.event, currDay, ALERT_LIMIT);
			purgeNotificationAlert(alerts, 3);
		}
	}
	
	@Scheduled(cron = "${purge.cron.alert.jobs}", zone = "Europe/Paris")
	public void purgeAlertEmploye() {
		final int currDay = ParseUtil.getCurrDay();
		if(currDay <= 5) {
			final List<AlertPostResult> alerts = alertService.findLastAlertPost(DocumentType.employe, currDay, ALERT_LIMIT);
			purgeNotificationAlert(alerts, 4);
		}
	}
	
}
