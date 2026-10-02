package com.rinitec.algerieoffice.web.controllers.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.LiveType;
import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Faq;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.IGuestPartnerService;
import com.rinitec.algerieoffice.services.company.portfolio.IActualityService;
import com.rinitec.algerieoffice.services.company.portfolio.IEventService;
import com.rinitec.algerieoffice.services.company.portfolio.IFaqService;
import com.rinitec.algerieoffice.services.company.portfolio.IPartnerService;
import com.rinitec.algerieoffice.services.company.portfolio.IWorkService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.portfolio.ActualityForm;
import com.rinitec.algerieoffice.web.form.company.portfolio.EventForm;
import com.rinitec.algerieoffice.web.form.company.portfolio.FaqForm;
import com.rinitec.algerieoffice.web.form.company.portfolio.PartnerguestForm;
import com.rinitec.algerieoffice.web.form.company.portfolio.PartneruserForm;
import com.rinitec.algerieoffice.web.form.company.portfolio.WorkForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLiveEvent;
import com.rinitec.algerieoffice.web.listener.events.OnTalkEvent;
import com.rinitec.algerieoffice.web.modal.premium.PremiumPost;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/portfolio")
public class CompanyPortfolioController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private IWorkService workService;
	private IActualityService actualityService;
	private IEventService eventService;
	private IFaqService faqService;
	private IPartnerService partnerService;
	private IGuestPartnerService guestPartnerService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyPortfolioController(IAttributeService attributeService, IPremiumService premiumService, IWorkService workService, 
			IActualityService actualityService, IEventService eventService, IFaqService faqService, IPartnerService partnerService, 
			IGuestPartnerService guestPartnerService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.workService = workService;
		this.actualityService = actualityService;
		this.eventService = eventService;
		this.faqService = faqService;
		this.partnerService = partnerService;
		this.guestPartnerService = guestPartnerService;
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
	@RequestMapping(value = "/works", method = RequestMethod.GET)
	public String showWorks(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("filterPartners", workService.findAllFilterPartner(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PORTFOLIO1));
		return "companyPortfolioWorks";
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
	@RequestMapping(value = "/works-load", method = RequestMethod.GET)
	public String loadWorks(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO1, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", workService.findWorksList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListWorks";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/works/new", method = RequestMethod.GET)
	public String showNewWork(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumWork = premiumService.parsePremiumPost(companyId, workService.countWork(companyId));
		if(!premiumWork.isHasConsumer()) {
			model.addAttribute("chosePartners", workService.findAllChosePartner(localUser.getCompanyId()));
			model.addAttribute("urlWorks", ConstraintesURL.URL_WORKS.concat("/"));
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("premium", premiumWork);
		model.addAttribute("work", new WorkForm(companyId));
		return "companyPortfolioWork";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/works/edit", method = RequestMethod.GET)
	public String showEditWork(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "id", required = false) final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/portfolio/works";
		}
		final WorkForm workForm = workService.readWorkForm(id, localUser.getCompanyId());
		if(workForm == null) {
			return "redirect:/company/portfolio/works?notFound=true";
		}
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumWork = premiumService.parsePremiumPost(companyId, 0L);
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("premium", premiumWork);
		model.addAttribute("work", workForm);
		model.addAttribute("chosePartners", workService.findAllChosePartner(localUser.getCompanyId()));
		model.addAttribute("urlWorks", ConstraintesURL.URL_WORKS.concat("/"));
		return "companyPortfolioWork";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param workForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/works/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveWork(final HttpServletRequest request, @Valid final WorkForm workForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!workForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumWork = premiumService.parsePremiumPost(companyId, workService.countWork(companyId));
		workForm.setFile(file);
		if(StringUtils.isEmpty(workForm.getId())) {
			if(premiumWork.isHasConsumer()) {
				throw new MaxPlanException("message.plan.works");
			}
			final Work work = workService.addWork(workForm, premiumWork.getMaxKeywords(), localUser.getUser().getId());
			if(work.getHasPublished()) {
				eventPublisher.publishEvent(new OnLiveEvent(work.getCompanyId(), work.getIdentify(), LiveType.addWork, request));
			}
		} else {
			final Work work = workService.updateWork(workForm, premiumWork.getMaxKeywords(), localUser.getUser().getId());
			if(work == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(workForm.getId()) ? ConstraintesJournal.COMPANY_ADD_WORK : ConstraintesJournal.COMPANY_UPDATE_WORK, workForm.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/works/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteWork(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Work work = workService.deleteWork(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_WORK, work.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/works/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteWorks(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		workService.deleteWorks(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_WORK, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/works/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllWorks(@AuthenticationPrincipal final LocalUser localUser) {
		workService.deleteAllWorks(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_WORK, null));
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
	@RequestMapping(value = "/actus", method = RequestMethod.GET)
	public String showActualities(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("autors", actualityService.findAllAutorActuality(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PORTFOLIO2));
		return "companyPortfolioActualities";
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
	@RequestMapping(value = "/actus-load", method = RequestMethod.GET)
	public String loadActualities(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO2, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("companyPublished", attributeService.checkCompanyPublished(localUser.getCompanyId()));
		model.addAttribute("list", actualityService.findActualitiesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListActualities";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/actus/new", method = RequestMethod.GET)
	public String showNewActuality(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxActu = premiumService.hasMaxProduct(companyId, actualityService.countActuality(companyId));
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("hasMaxActu", hasMaxActu);
		model.addAttribute("actu", new ActualityForm(companyId));
		return "companyPortfolioActuality";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/actus/edit", method = RequestMethod.GET)
	public String showEditActuality(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "id", required = false) final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/portfolio/actus";
		}
		final ActualityForm actualityForm = actualityService.readActualityForm(id, localUser.getCompanyId());
		if(actualityForm == null) {
			return "redirect:/company/portfolio/actus?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("actu", actualityForm);
		return "companyPortfolioActuality";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param actualityForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/actus/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveActuality(final HttpServletRequest request, @Valid final ActualityForm actualityForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		if(!actualityForm.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		Actuality actuality = null;
		actualityForm.setFile(file);
		if(StringUtils.isEmpty(actualityForm.getId())) {
			final boolean hasMaxActu = premiumService.hasMaxProduct(companyId, actualityService.countActuality(companyId));
			if(hasMaxActu) {
				throw new MaxPlanException("message.plan.actus");
			}
			actuality = actualityService.addActuality(actualityForm, localUser.getUser().getId());
		} else {
			actuality = actualityService.updateActuality(actualityForm, localUser.getUser().getId());
			if(actuality == null) {
				throw new AccessAuthorityException();
			}
		}
		if(actuality.getHasPublished() && (StringUtils.isEmpty(actualityForm.getId()) || actualityForm.isHasNotified())) {
			eventPublisher.publishEvent(new OnLiveEvent(actuality.getCompanyId(), actuality.getId().toString(), StringUtils.isEmpty(actualityForm.getId()) 
					? LiveType.addActu : LiveType.updateActu, request));
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(actualityForm.getId()) ? ConstraintesJournal.COMPANY_ADD_ACTU : ConstraintesJournal.COMPANY_UPDATE_ACTU, actualityForm.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/actus/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteActuality(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Actuality actuality = actualityService.deleteActuality(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ACTU, actuality.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/actus/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteActualities(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		actualityService.deleteActualities(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_ACTU, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/actus/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllActualities(@AuthenticationPrincipal final LocalUser localUser) {
		actualityService.deleteAllActualities(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_ACTU, null));
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
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String showEvents(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("autors", eventService.findAllAutorEvent(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PORTFOLIO3));
		return "companyPortfolioEvents";
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
	@RequestMapping(value = "/events-load", method = RequestMethod.GET)
	public String loadEvents(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO3, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", eventService.findEventsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListEvents";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/events/new", method = RequestMethod.GET)
	public String showNewEvent(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumEvent = premiumService.parsePremiumPost(companyId, eventService.countEvent(companyId));
		if(!premiumEvent.isHasConsumer()) {
			model.addAttribute("urlEvents", ConstraintesURL.URL_EVENTS.concat("/"));
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("premium", premiumEvent);
		model.addAttribute("event", new EventForm(companyId));
		return "companyPortfolioEvent";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/events/edit", method = RequestMethod.GET)
	public String showEditEvent(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/portfolio/events";
		}
		final EventForm eventForm = eventService.readEventForm(id, localUser.getCompanyId());
		if(eventForm == null) {
			return "redirect:/company/portfolio/events?notFound=true";
		}
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumEvent = premiumService.parsePremiumPost(companyId, 0L);
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("premium", premiumEvent);
		model.addAttribute("event", eventForm);
		model.addAttribute("urlEvents", ConstraintesURL.URL_EVENTS.concat("/"));
		return "companyPortfolioEvent";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * @param request
	 * @param eventForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveEvent(final HttpServletRequest request, @Valid final EventForm eventForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!eventForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumEvent = premiumService.parsePremiumPost(companyId, eventService.countEvent(companyId));
		Event event = null;
		eventForm.setFile(file);
		if(StringUtils.isEmpty(eventForm.getId())) {
			if(premiumEvent.isHasConsumer()) {
				throw new MaxPlanException("message.plan.events");
			}
			event = eventService.addEvent(eventForm, premiumEvent.getMaxKeywords(), localUser.getUser().getId());
		} else {
			event = eventService.updateEvent(eventForm, premiumEvent.getMaxKeywords(), localUser.getUser().getId());
			if(event == null) {
				throw new AccessAuthorityException();
			}
		}
		if(event.getHasPublished() && (StringUtils.isEmpty(eventForm.getId()) || eventForm.isHasNotified())) {
			eventPublisher.publishEvent(new OnLiveEvent(event.getCompanyId(), event.getIdentify(), StringUtils.isEmpty(eventForm.getId()) 
					? LiveType.addEvent : LiveType.updateEvent, request));
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(eventForm.getId()) ? ConstraintesJournal.COMPANY_ADD_EVENT : ConstraintesJournal.COMPANY_UPDATE_EVENT, eventForm.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEvent(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Event event = eventService.deleteEvent(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_EVENT, event.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEvents(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		eventService.deleteEvents(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_EVENT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEvents(@AuthenticationPrincipal final LocalUser localUser) {
		eventService.deleteAllEvents(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_EVENT, null));
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
	@RequestMapping(value = "/faqs", method = RequestMethod.GET)
	public String showFaqs(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO4, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PORTFOLIO4));
		return "companyPortfolioFaqs";
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
	@RequestMapping(value = "/faqs-load", method = RequestMethod.GET)
	public String loadFaqs(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO4, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", faqService.findFaqsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListFaqs";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/faqs/new", method = RequestMethod.GET)
	public String showNewFaq(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxFaq = premiumService.hasMaxProduct(companyId, faqService.countFaq(companyId));
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("hasMaxFaq", hasMaxFaq);
		model.addAttribute("faq", new FaqForm(companyId));
		return "companyPortfolioFaq";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/faqs/edit", method = RequestMethod.GET)
	public String showEditFaq(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/portfolio/faqs";
		}
		final FaqForm faqForm = faqService.readFaqForm(id, localUser.getCompanyId());
		if(faqForm == null) {
			return "redirect:/company/portfolio/faqs?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("faq", faqForm);
		return "companyPortfolioFaq";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param faqForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/faqs/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveFaq(@Valid final FaqForm faqForm, @AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		if(!faqForm.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		if(StringUtils.isEmpty(faqForm.getId())) {
			final boolean hasMaxFaq = premiumService.hasMaxProduct(companyId, faqService.countFaq(companyId));
			if(hasMaxFaq) {
				throw new MaxPlanException("message.plan.faq");
			}
			faqService.addFaq(faqForm, localUser.getUser().getId());
		} else {
			final Faq faq = faqService.updateFaq(faqForm, localUser.getUser().getId());
			if(faq == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(faqForm.getId()) ? ConstraintesJournal.COMPANY_ADD_FAQ : ConstraintesJournal.COMPANY_UPDATE_FAQ, faqForm.getQuestion()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/faqs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFaq(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Faq faq = faqService.deleteFaq(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_FAQ, faq.getQuestion()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/faqs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFaqs(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		faqService.deleteFaqs(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_FAQ, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/faqs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllFaqs(@AuthenticationPrincipal final LocalUser localUser) {
		faqService.deleteAllFaqs(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_FAQ, null));
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
	@RequestMapping(value = "/partners", method = RequestMethod.GET)
	public String showParteners(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO5, defaultValue = "") final String table) {
		final Long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("autors", partnerService.findAllAutorPartner(companyId));
		model.addAttribute("countGuestPartner", partnerService.countGuestPartner(companyId));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_PORTFOLIO5));
		return "companyPortfolioPartners";
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
	@RequestMapping(value = "/partners-load", method = RequestMethod.GET)
	public String loadPartners(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_PORTFOLIO5, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", partnerService.findPartnersList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListPartners";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/partners/new", method = RequestMethod.GET)
	public String showNewPartener(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxPartner = premiumService.hasMaxProduct(companyId, partnerService.countPartnerAndGuest(companyId));
		attributeService.attributeCompany(model, config, localUser);
		if(!hasMaxPartner) {
			model.addAttribute("partnerguest", new PartnerguestForm());
			model.addAttribute("partneruser", new PartneruserForm(companyId));
		}
		model.addAttribute("hasMaxPartner", hasMaxPartner);
		return "companyPortfolioNewpartner";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/partners/edit", method = RequestMethod.GET)
	public String showEditPartener(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/portfolio/partners";
		}
		final PartneruserForm partneruserForm = partnerService.readPartneruserForm(id, localUser.getCompanyId());
		if(partneruserForm == null) {
			return "redirect:/company/portfolio/partners?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("partneruser", partneruserForm);
		return "companyPortfolioEditpartner";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param partneruserForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePartner(@Valid final PartneruserForm partneruserForm, @AuthenticationPrincipal final LocalUser localUser,  
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		final Long companyId = localUser.getCompanyId();
		if(!partneruserForm.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		partneruserForm.setFile(file);
		if(StringUtils.isEmpty(partneruserForm.getId())) {
			final boolean hasMaxPartner = premiumService.hasMaxProduct(companyId, partnerService.countPartnerAndGuest(companyId));
			if(hasMaxPartner) {
				throw new MaxPlanException("message.plan.partner");
			}
			partnerService.addPartner(partneruserForm, localUser.getUser().getId());
		} else {
			final Partner partner = partnerService.updatePartner(partneruserForm, localUser.getUser().getId());
			if(partner == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(partneruserForm.getId()) ? ConstraintesJournal.COMPANY_ADD_PARTNER : ConstraintesJournal.COMPANY_UPDATE_PARTNER, partneruserForm.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePartner(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Partner partner = partnerService.deletePartner(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_PARTNER, partner.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePartners(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		partnerService.deletePartners(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_PARTNER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllPartners(@AuthenticationPrincipal final LocalUser localUser) {
		partnerService.deleteAllPartners(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_PARTNER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param partnerguestForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/guest", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse guestPartner(final HttpServletRequest request, @Valid final PartnerguestForm partnerguestForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxPartner = premiumService.hasMaxProduct(companyId, partnerService.countPartnerAndGuest(companyId));
		if(hasMaxPartner) {
			throw new MaxPlanException("message.plan.partner");
		}
		final GuestPartner guestPartner = guestPartnerService.addGuestPartner(partnerguestForm, companyId, localUser.getUserId());
		eventPublisher.publishEvent(new OnTalkEvent(guestPartner.getPartnerId(), TalkType.partner));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_GUEST_PARTNER, partnerguestForm.getEmail()));
		return new GenericResponse("success");
	}
	
}
