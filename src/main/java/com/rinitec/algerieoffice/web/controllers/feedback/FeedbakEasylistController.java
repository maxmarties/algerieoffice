package com.rinitec.algerieoffice.web.controllers.feedback;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.user.easylist.IEasylistService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;

@Controller
@RequestMapping(value = "/feedback/easylist")
public class FeedbakEasylistController {

	private IEasylistService easylistService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public FeedbakEasylistController(IEasylistService easylistService, ApplicationEventPublisher eventPublisher) {
		this.easylistService = easylistService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param searchCompanyForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/companies-search", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse easylistSearchCompanies(@Valid final SearchCompanyForm searchCompanyForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(searchCompanyForm.getUserId())) {
			throw new AccessAuthorityException();
		}
		final EasylistCompany easylistCompany = easylistService.addEasylistCompany(searchCompanyForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EASYLIST_COMPANY, easylistCompany.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param token
	 * @param userId
	 * @param easyname
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/companies-sector", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse easylistSectorCompanies(@RequestParam("code") final String code, @RequestParam("wilaya") final Integer wilaya, @RequestParam("token") final String token, 
			@RequestParam("id") final Long userId, @RequestParam("easyname") final String easyname, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(userId)) {
			throw new AccessAuthorityException();
		}
		final String search = StringUtils.isEmpty(token) ? null : token.replaceAll("\\+", " ");
		final EasylistCompany easylistCompany = easylistService.addEasylistCompany(userId, code, wilaya, search, easyname.replaceAll("\\+", " "));
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EASYLIST_COMPANY, easylistCompany.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param searchPostForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts-search", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse easylistPosts(@Valid final SearchPostForm searchPostForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(searchPostForm.getUserId())) {
			throw new AccessAuthorityException();
		}
		final EasylistDocument easylistDocument = easylistService.addEasylistPost(searchPostForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EASYLIST_POST, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param searchAnnonceForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/annonces-search", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse easylistAnnonces(@Valid final SearchAnnonceForm searchAnnonceForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(searchAnnonceForm.getUserId())) {
			throw new AccessAuthorityException();
		}
		final EasylistDocument easylistDocument = easylistService.addEasylistAnnonce(searchAnnonceForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EASYLIST_ADS, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param searchEventForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events-search", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse easylistEvents(@Valid final SearchEventForm searchEventForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(searchEventForm.getUserId())) {
			throw new AccessAuthorityException();
		}
		final EasylistDocument easylistDocument = easylistService.addEasylistEvent(searchEventForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EASYLIST_EVENT, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param searchEmployeForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/employes-search", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse easylistEmployes(@Valid final SearchEmployeForm searchEmployeForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(searchEmployeForm.getUserId())) {
			throw new AccessAuthorityException();
		}
		final EasylistDocument easylistDocument = easylistService.addEasylistEmploye(searchEmployeForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EASYLIST_JOB, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
}
