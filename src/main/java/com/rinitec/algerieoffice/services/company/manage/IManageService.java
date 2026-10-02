package com.rinitec.algerieoffice.services.company.manage;

import java.util.List;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Appearance;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Maindisplay;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Preferences;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Sticky;
import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.manage.AppearanceForm;
import com.rinitec.algerieoffice.web.form.company.manage.MaindisplayForm;
import com.rinitec.algerieoffice.web.form.company.manage.PreferenceForm;
import com.rinitec.algerieoffice.web.form.company.manage.StickyForm;
import com.rinitec.algerieoffice.web.form.company.manage.WidgetB2CForm;
import com.rinitec.algerieoffice.web.modal.company.manage.AppearanceView;
import com.rinitec.algerieoffice.web.modal.company.manage.WidgetView;

public interface IManageService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	MaindisplayForm readMaindisplayForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param maindisplayForm
	 * @return
	 */
	Maindisplay updateMaindisplay(MaindisplayForm maindisplayForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	StickyForm readStickyForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param stickyForm
	 * @return
	 * @throws AlreadyExistException
	 */
	Sticky updateSticky(StickyForm stickyForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	WidgetView readWidgetView(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	WidgetB2CForm readWidgetB2CForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param widgetB2CForm
	 * @return
	 * @throws AlreadyExistException
	 */
	WidgetB2C upadteWidgetB2C(WidgetB2CForm widgetB2CForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AppearanceView readAppearanceView(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AppearanceForm readAppearanceForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param appearanceForm
	 * @param hasPremium
	 * @return
	 */
	Appearance updateAppearance(AppearanceForm appearanceForm, boolean hasPremium);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllMessengers(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	PreferenceForm readPreferenceForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param preferenceForm
	 * @return
	 * @throws NotFoundException
	 */
	Preferences updatePreferences(PreferenceForm preferenceForm) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @return
	 */
	boolean hasNotificationTalk(Long companyId, TalkType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @return
	 */
	boolean hasNotificationMail(Long companyId, EmailType type);
	
}
