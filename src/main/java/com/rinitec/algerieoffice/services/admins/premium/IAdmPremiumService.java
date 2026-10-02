package com.rinitec.algerieoffice.services.admins.premium;

import java.util.List;

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.form.admins.premium.AdmBudgetForm;
import com.rinitec.algerieoffice.web.form.admins.premium.PremiumForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumLine;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumOrderLine;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterBudget;

public interface IAdmPremiumService {

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
	ElementsList findAdmPremiumList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	PremiumForm readPremiumForm(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param premiumForm
	 * @return
	 */
	Premium updatePremium(PremiumForm premiumForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deletePremium(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deletePremiums(List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @param type
	 * @return
	 */
	ElementsList findAdmPremiumOrderList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc, OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param type
	 * @return
	 */
	AdmPremiumOrderLine readAdmPremiumOrderLine(String id, OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<AdmPremiumLine> findAdmPremiumCompanyList(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param premiumForm
	 * @return
	 */
	Long validateOrder(PremiumForm premiumForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteOrder(String id, OrderType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteOrders(List<String> lines, OrderType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	NewsletterBudget readNewsletterBudget(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admOrderForm
	 * @return
	 * @throws AlreadyExistException
	 */
	Object[] validateEmailing(AdmOrderForm admOrderForm) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findAdmBudgetList(String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmBudgetForm readAdmBudgetForm(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param admBudgetForm
	 * @return
	 */
	String updateBudget(AdmBudgetForm admBudgetForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @throws NotFoundException
	 */
	void deleteBudget(Long id) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 */
	void deleteBudgets(List<Long> lines) throws NotFoundException;
	
}
