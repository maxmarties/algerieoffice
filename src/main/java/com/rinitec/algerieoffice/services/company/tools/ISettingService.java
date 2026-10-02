package com.rinitec.algerieoffice.services.company.tools;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Settings;
import com.rinitec.algerieoffice.web.form.company.tools.SettingForm;

public interface ISettingService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	SettingForm readSettingForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param settingForm
	 * @return
	 */
	Settings updateSettings(SettingForm settingForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean readService(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	String readBanner(Long companyId);
	
}
