package com.rinitec.algerieoffice.services.admins.companies;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.companies.FeatureForm;
import com.rinitec.algerieoffice.web.form.admins.premium.AdmBudgetForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyDelete;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyInfo;
import com.rinitec.algerieoffice.web.modal.company.tools.PremiumLine;

public interface IAdmCompanyService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmCompanyList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	AdmCompanyDelete deleteCompany(Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmCompanyProfileList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmCompanyFeatureList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmCompanyCustomerList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	String deleteWidgetB2C(Long id) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 */
	void deleteWidgetsB2C(List<Long> lines) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AdmCompanyInfo readAdmCompanyInfo(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<PremiumLine> findAllPremiumCompanyList(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param featureForm
	 * @return
	 */
	Premium validateFeature(FeatureForm featureForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AdmBudgetForm readAdmBudgetForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admBudgetForm
	 * @return
	 */
	String updateBudget(AdmBudgetForm admBudgetForm);
	
}
