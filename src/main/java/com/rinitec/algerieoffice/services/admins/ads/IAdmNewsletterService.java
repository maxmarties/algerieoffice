package com.rinitec.algerieoffice.services.admins.ads;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.form.admins.ads.AdmMarketplaceForm;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;

public interface IAdmNewsletterService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 * @throws AlreadyExistException
	 */
	Newsletter registerNewsletter(String email) throws AlreadyExistException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<String> findAllNewsletter(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<UUIDMini> findAllBolgNewsletter(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 * @throws InvalidResourceException
	 */
	List<BlogNewsletterMini> findAllBlogNewsletterMini(List<String> lines) throws InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limitUser
	 * @param limitAds
	 * @return
	 */
	AdmMarketplaceForm readMarketplaceForm(int limitUser, int limitAds);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 * @throws InvalidResourceException
	 */
	List<AnnonceNewsletterMini> findAllAnnonceNewsletterMini(List<String> lines) throws InvalidResourceException;
	
}
