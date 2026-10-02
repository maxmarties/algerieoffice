package com.rinitec.algerieoffice.mail;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rinitec.algerieoffice.web.form.admins.ads.AdmMailingForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.NewsletterForm;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnEmailEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;

public interface IEmailService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 */
	void sendUnsubscribeMail(OnRegisterEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 */
	void sendSubscribeMail(OnEmailEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 */
	void sendAlertMail(OnAlertEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param lines
	 */
	void sendNewsletterBlog(HttpServletRequest request, List<String> users, List<BlogNewsletterMini> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param users
	 * @param lines
	 * @param todays
	 * @param offers
	 */
	void sendNewsletterAnnonces(HttpServletRequest request, List<String> users, List<AnnonceNewsletterMini> lines, int todays, int offers);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param mailingForm
	 */
	void sendNewsletterMailing(HttpServletRequest request, AdmMailingForm mailingForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param newsletterForm
	 */
	boolean sendNewsletterCompany(HttpServletRequest request, NewsletterForm newsletterForm);
	
}
