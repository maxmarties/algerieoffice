package com.rinitec.algerieoffice.persistence.dao.admins.ads;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmNewsletterLine;

public interface NewsletterRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllNewsletterAdmin(Integer filter, String search);
	
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
	List<AdmNewsletterLine> findAllNewsletterAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param users
	 * @param limit
	 * @return
	 */
	List<String> findAllNewsletter(List<String> users, int limit);
	
}
