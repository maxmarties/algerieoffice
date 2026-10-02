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

public interface IEmailTemplate {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @param html
	 * @return
	 */
	EmailModel generateUnsubscribeMail(OnRegisterEvent event, boolean html);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	EmailModel generateSubscribeMail(OnEmailEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	EmailModel generateAlertMail(OnAlertEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param lines
	 * @return
	 */
	EmailModel generateNewsletterBlog(HttpServletRequest request, List<BlogNewsletterMini> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param lines
	 * @param todays
	 * @param offers
	 * @return
	 */
	EmailModel generateNewsletterAnnonce(HttpServletRequest request, List<AnnonceNewsletterMini> lines, int todays, int offers);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param mailingForm
	 * @return
	 */
	EmailModel generateNewsletterMailing(HttpServletRequest request, AdmMailingForm mailingForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param newsletterForm
	 * @return
	 */
	EmailModel generateNewsletterCompany(HttpServletRequest request, NewsletterForm newsletterForm);
	
}
