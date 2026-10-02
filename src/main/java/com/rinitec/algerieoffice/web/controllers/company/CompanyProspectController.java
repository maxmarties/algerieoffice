package com.rinitec.algerieoffice.web.controllers.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.prospect.IProspectService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.modal.company.prospect.ProspectDetail;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/prospect")
public class CompanyProspectController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private IProspectService prospectService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyProspectController(IAttributeService attributeService, IPremiumService premiumService, IProspectService prospectService, 
			ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.prospectService = prospectService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/quotes", method = RequestMethod.GET)
	public String showQuotes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PROSPECT1));
		return "companyProspectQuotes";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/quotes-load", method = RequestMethod.GET)
	public String loadQuotes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT1, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", prospectService.findQuoteList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListQuotes";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/quotes/detail", method = RequestMethod.GET)
	public String showQuoteDetail(final Model model, @RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final ProspectDetail prospectDetail = prospectService.readQuoteDetail(id, localUser.getCompanyId(), localUser.getUserId());
			if(prospectDetail == null) {
				return "redirect:/company/prospect/quotes?notFound=true";
			}
			model.addAttribute("prospectDetail", prospectDetail);
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
					ConstraintesJournal.COMPANY_DETAIL_QUOTE, prospectDetail.getUsername()));
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyProspectQuote";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/quotes/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateQuote(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.validateProspect(id, localUser.getCompanyId(), localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_QUOTE, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/quotes/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteQuote(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.deleteProspect(id, localUser.getCompanyId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_QUOTE, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/quotes/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteQuotes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteProspects(lines, localUser.getCompanyId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_QUOTE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/quotes/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllQuotes(@AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteAllProspects(localUser.getCompanyId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_QUOTE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String showAds(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PROSPECT2));
		return "companyProspectAds";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/ads-load", method = RequestMethod.GET)
	public String loadAds(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT2, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", prospectService.findAdsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListAds";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/ads/detail", method = RequestMethod.GET)
	public String showAdsDetail(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/prospect/ads";
		}
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final ProspectDetail prospectDetail = prospectService.readAdsDetail(id, localUser.getCompanyId(), localUser.getUserId());
			if(prospectDetail == null) {
				return "redirect:/company/prospect/ads?notFound=true";
			}
			model.addAttribute("prospectDetail", prospectDetail);
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
					ConstraintesJournal.COMPANY_DETAIL_AD, prospectDetail.getUsername()));
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyProspectAd";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateAd(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.validateProspect(id, localUser.getCompanyId(), localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_AD, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAd(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.deleteProspect(id, localUser.getCompanyId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_AD, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAds(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteProspects(lines, localUser.getCompanyId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_AD, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAds(@AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteAllProspects(localUser.getCompanyId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_AD, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/infos", method = RequestMethod.GET)
	public String showInfos(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PROSPECT3));
		return "companyProspectInfos";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/infos-load", method = RequestMethod.GET)
	public String loadInfos(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT3, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", prospectService.findInfoList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListInfos";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/infos/detail", method = RequestMethod.GET)
	public String showInfoDetail(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/prospect/infos";
		}
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final ProspectDetail prospectDetail = prospectService.readInfoDetail(id, localUser.getCompanyId(), localUser.getUserId());
			if(prospectDetail == null) {
				return "redirect:/company/prospect/infos?notFound=true";
			}
			model.addAttribute("prospectDetail", prospectDetail);
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
					ConstraintesJournal.COMPANY_DETAIL_INFO, prospectDetail.getUsername()));
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyProspectInfo";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/infos/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateInfo(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.validateProspect(id, localUser.getCompanyId(), localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_INFO, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/infos/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteInfo(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.deleteProspect(id, localUser.getCompanyId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_INFO, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/infos/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteInfos(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteProspects(lines, localUser.getCompanyId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_INFO, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/infos/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllInfos(@AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteAllProspects(localUser.getCompanyId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_INFO, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String showJobs(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT4, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PROSPECT4));
		return "companyProspectJobs";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/jobs-load", method = RequestMethod.GET)
	public String loadJobs(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PROSPECT4, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", prospectService.findJobList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListJobs";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/jobs/detail", method = RequestMethod.GET)
	public String showJobDetail(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/prospect/jobs";
		}
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final ProspectDetail prospectDetail = prospectService.readJobDetail(id, localUser.getCompanyId(), localUser.getUserId());
			if(prospectDetail == null) {
				return "redirect:/company/prospect/jobs?notFound=true";
			}
			model.addAttribute("prospectDetail", prospectDetail);
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
					ConstraintesJournal.COMPANY_DETAIL_JOB, prospectDetail.getUsername()));
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyProspectJob";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateJob(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.validateProspect(id, localUser.getCompanyId(), localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_JOB, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteJob(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = prospectService.deleteProspect(id, localUser.getCompanyId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_JOB, guestDocument.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteJobs(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteProspects(lines, localUser.getCompanyId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_JOB, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllJobs(@AuthenticationPrincipal final LocalUser localUser) {
		prospectService.deleteAllProspects(localUser.getCompanyId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_JOB, null));
		return new GenericResponse("success");
	}
	
}
