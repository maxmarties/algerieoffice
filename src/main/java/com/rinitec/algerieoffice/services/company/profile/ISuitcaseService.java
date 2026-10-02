package com.rinitec.algerieoffice.services.company.profile;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.CompanymailExistException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.MobileExistException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.profile.BriefcaseForm;
import com.rinitec.algerieoffice.web.form.company.profile.CreditForm;
import com.rinitec.algerieoffice.web.form.company.profile.LinkedForm;
import com.rinitec.algerieoffice.web.form.company.profile.LocationForm;
import com.rinitec.algerieoffice.web.form.company.profile.SheduleForm;

public interface ISuitcaseService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	BriefcaseForm readBriefcaseForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param briefcaseForm
	 * @return
	 */
	CompanyBriefcase updateCompanyBriefcase(BriefcaseForm briefcaseForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	SheduleForm readSheduleForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sheduleForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws CompanymailExistException
	 * @throws PhoneExistException
	 * @throws MobileExistException
	 */
	CompanyShedule updateCompanyShedule(SheduleForm sheduleForm) throws AlreadyExistException, CompanymailExistException, 
		PhoneExistException, MobileExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	LocationForm readLocationForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param locationForm
	 * @return
	 * @throws UrlUnavailableException
	 */
	CompanyLocation updateCompanyLocation(LocationForm locationForm) throws UrlUnavailableException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	LinkedForm readLinkedForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param linkedForm
	 * @param maxKeysword
	 * @return
	 * @throws UrlUnavailableException
	 * @throws MaxKeyswordException
	 * @throws AlreadyExistException
	 * @throws SocialExistException
	 */
	CompanyLinked updateCompanyLinked(LinkedForm linkedForm, int maxKeysword) throws UrlUnavailableException, MaxKeyswordException, 
		AlreadyExistException, SocialExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CreditForm readCreditForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param creditForm
	 * @return
	 */
	CompanyCredit updateCompanyCredit(CreditForm creditForm);
	
}
