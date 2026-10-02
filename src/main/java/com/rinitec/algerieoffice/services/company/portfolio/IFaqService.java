package com.rinitec.algerieoffice.services.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Faq;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.portfolio.FaqForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IFaqService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countFaq(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	FaqForm readFaqForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param faqForm
	 * @param userId
	 * @return
	 */
	Faq addFaq(FaqForm faqForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param faqForm
	 * @param userId
	 * @return
	 */
	Faq updateFaq(FaqForm faqForm, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findFaqsList(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorFaq(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Faq deleteFaq(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFaqs(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllFaqs(Long companyId);
	
}
