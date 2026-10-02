package com.rinitec.algerieoffice.services.user.alerts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.dao.users.alerts.AlertSettingRepository;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertSetting;
import com.rinitec.algerieoffice.web.form.user.setting.NotificationsForm;
import com.rinitec.algerieoffice.web.form.user.setting.SecurityForm;

@Service
public class AlertSettingService implements IAlertSettingService {

	private AlertSettingRepository alertSettingRepository;
	
	@Autowired
	public AlertSettingService(AlertSettingRepository alertSettingRepository) {
		this.alertSettingRepository = alertSettingRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public NotificationsForm readNotificationsForm(final Long userId, final boolean[] sounds) {
		final NotificationsForm notificationsForm = new NotificationsForm();
		final Optional<AlertSetting> uOptional = alertSettingRepository.findById(userId);
		if(uOptional.isPresent()) {
			final AlertSetting alertSetting = uOptional.get();
			notificationsForm.parseNotifications(alertSetting.getNotifications());
			notificationsForm.parseCommunications(alertSetting.getCommunications());
		} else {
			notificationsForm.parseDefaultNotification();
		}
		notificationsForm.setId(userId);
		notificationsForm.parseSounds(sounds);
		return notificationsForm;
	}
	
	@Override
	@Transactional
	public AlertSetting updateAlertSetting(final NotificationsForm notificationsForm) {
		final Optional<AlertSetting> uOptional = alertSettingRepository.findById(notificationsForm.getId());
		final AlertSetting alertSetting = uOptional.isPresent() ? uOptional.get() : new AlertSetting(notificationsForm.getId(), true);
		alertSetting.setCommunications(notificationsForm.builderCommunicatios());
		alertSetting.setNotifications(notificationsForm.builderNotifications());
		return alertSettingRepository.save(alertSetting);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SecurityForm readSecurityForm(final Long userId) {
		final SecurityForm securityForm = new SecurityForm();
		final Optional<AlertSetting> uOptional = alertSettingRepository.findById(userId);
		if(uOptional.isPresent()) {
			final AlertSetting alertSetting = uOptional.get();
			securityForm.setAnalytic(alertSetting.getAnalytic());
			securityForm.setSecured(alertSetting.isSecured());
			securityForm.setCodage(alertSetting.getCodage());
		} else {
			securityForm.setAnalytic(true);
			securityForm.setSecured(false);
		}
		securityForm.setId(userId);
		return securityForm;
	}
	
	@Override
	@Transactional
	public AlertSetting updateAlertSetting(final SecurityForm securityForm) {
		final Optional<AlertSetting> uOptional = alertSettingRepository.findById(securityForm.getId());
		final AlertSetting alertSetting = uOptional.isPresent() ? uOptional.get() : new AlertSetting(securityForm.getId(), "11111", "11111");
		alertSetting.setAnalytic(securityForm.isAnalytic());
		alertSetting.setSecured(securityForm.isSecured());
		alertSetting.setCodage(securityForm.isSecured() ? securityForm.getCodage() : null);
		return alertSettingRepository.save(alertSetting);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasNotificationSetting(final Long userId, final NotificationType type) {
		final Optional<AlertSetting> uOptional = alertSettingRepository.findById(userId);
		if(uOptional.isPresent()) {
			final AlertSetting alertSetting = uOptional.get();
			switch(type) {
			case detectVisit: return alertSetting.getNotifications().charAt(1) == '1';
			case alertPost: return alertSetting.getNotifications().charAt(2) == '1';
			case favoriteAccount: return alertSetting.getNotifications().charAt(3) == '1';
			case accessProfile: return alertSetting.getNotifications().charAt(4) == '1';
			default: return true;
			}
		}
		return true;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasNotificationMail(final Long userId, final EmailType type) {
		final Optional<String> uOptional = alertSettingRepository.findCommunicationsById(userId);
		if(uOptional.isPresent()) {
			final String communications = uOptional.get();
			switch(type) {
			case messages: return communications.charAt(0) == '1';
			case alerts: return communications.charAt(1) == '1';
			default: return true;
			}
		}
		return true;
	}
	
}
