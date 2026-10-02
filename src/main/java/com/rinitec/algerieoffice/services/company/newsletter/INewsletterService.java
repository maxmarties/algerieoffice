package com.rinitec.algerieoffice.services.company.newsletter;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Maintemplate;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.form.company.newsletter.BudgetForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.EmailingForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.NewsletterForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.TemplateForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterBudget;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterSocial;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterTemplate;

public interface INewsletterService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean checkOrder(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	NewsletterSocial readNewsletterSocial(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	TemplateForm readTemplateForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param templateForm
	 * @return
	 */
	Maintemplate updateMaintemplate(TemplateForm templateForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	NewsletterBudget readNewsletterBudget(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param budgetForm
	 * @return
	 */
	DocumentOrder addDocumentOrder(Long userId, BudgetForm budgetForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	NewsletterTemplate readNewsletterTemplate(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @return
	 */
	List<UUIDMini> findAllNewsletterArticles(Long companyId, Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllNewsletterUserCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	List<UserMini> findAllNewsletterCompanies(Long companyId, Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	List<UUIDMini> findAllEasylistCompany(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @return
	 */
	List<UserMini> findAllNewsletterEasylist(String id, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param lines
	 * @return
	 * @throws InvalidResourceException
	 */
	List<NewsletterItem> findAllNewsletterItem(Long companyId, Integer type, List<String> lines) throws InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param emailingForm
	 * @param email
	 * @return
	 * @throws MaxKeyswordException
	 * @throws EmptyElementException
	 * @throws InvalidResourceException
	 */
	NewsletterForm readNewsletterForm(EmailingForm emailingForm, String email) throws MaxKeyswordException, EmptyElementException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param contacts
	 * @return
	 */
	Budget updateConsumeBudget(Long companyId, int contacts);
	
}
