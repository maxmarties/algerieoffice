package com.rinitec.algerieoffice.services.user.alerts;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertSetting;
import com.rinitec.algerieoffice.web.form.user.setting.NotificationsForm;
import com.rinitec.algerieoffice.web.form.user.setting.SecurityForm;

public interface IAlertSettingService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param sounds
	 * @return
	 */
	NotificationsForm readNotificationsForm(Long userId, boolean[] sounds);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param notificationsForm
	 * @return
	 */
	AlertSetting updateAlertSetting(NotificationsForm notificationsForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	SecurityForm readSecurityForm(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param securityForm
	 * @return
	 */
	AlertSetting updateAlertSetting(SecurityForm securityForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean hasNotificationSetting(Long userId, NotificationType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean hasNotificationMail(Long userId, EmailType type);
	
}
