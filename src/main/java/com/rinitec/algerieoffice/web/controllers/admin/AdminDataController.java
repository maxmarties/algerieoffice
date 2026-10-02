package com.rinitec.algerieoffice.web.controllers.admin;

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

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.data.OrderPostal;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.data.IActivityService;
import com.rinitec.algerieoffice.services.admins.data.IPostalService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumFormuleService;
import com.rinitec.algerieoffice.services.company.profile.IIdentityService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.datas.ActivityForm;
import com.rinitec.algerieoffice.web.form.admins.datas.IdentityResponseForm;
import com.rinitec.algerieoffice.web.form.admins.datas.SocialFooterForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/data")
public class AdminDataController {

	private IAttributeService attributeService;
	private IActivityService activityService;
	private IIdentityService identityService;
	private IPostalService postalService;
	private IPremiumFormuleService premiumFormuleService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminDataController(IAttributeService attributeService, IActivityService activityService, IIdentityService identityService, 
			IPostalService postalService, IPremiumFormuleService premiumFormuleService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.activityService = activityService;
		this.identityService = identityService;
		this.postalService = postalService;
		this.premiumFormuleService = premiumFormuleService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	public String showActivities(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminDataActivities";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/activities-load", method = RequestMethod.GET)
	public String loadActivities(final Model model, @RequestParam("fl") final String filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", activityService.findActivityList(filter, search, sort, rows, page, hasDesc));
		return "adminListActivities";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/activities/new", method = RequestMethod.GET)
	public String showNewActivity(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("activity", new ActivityForm());
		return "adminDataActivity";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/activities/edit", method = RequestMethod.GET)
	public String showEditActivity(final Model model, @RequestParam("id") final Long id,
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		if(!activityService.existsById(id)) {
			return "redirect:/admin/data/activities?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("activity", activityService.readActivity(id));
		return "adminDataActivity";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/activities/check-url", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse checkActivityURL(@RequestParam("url") final String url) {
		if(activityService.existsByURL(url)) {
			throw new UrlUnavailableException("message.error.url");
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param codeActivityForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/activities/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateActivity(@Valid final ActivityForm codeActivityForm, @AuthenticationPrincipal final LocalUser localUser) {
		Activity activity = null;
		if(codeActivityForm.getId() == null) {
			activity = activityService.addActivity(codeActivityForm);
		} else {
			activity = activityService.updateActivity(codeActivityForm);
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), codeActivityForm.getId() == null ? ConstraintesJournal.ADMIN_ADD_ACTIVITY 
				: ConstraintesJournal.ADMIN_UPDATE_ACTIVITY, activity.getUrl()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/activities/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteActivity(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final Activity activity = activityService.deleteActivity(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ACTIVITY, activity.getUrl()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/activities/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteActivities(@RequestParam("lines[]") final List<Long> lines, @AuthenticationPrincipal final LocalUser localUser) {
		activityService.deleteActivities(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ACTIVITIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/identities", method = RequestMethod.GET)
	public String showIdentities(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminDataIdentities";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/identities-load", method = RequestMethod.GET)
	public String loadIdentities(final Model model, @RequestParam("fl") final String filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", identityService.findIdentitiesList(search, sort, rows, page, hasDesc));
		return "adminListIdentities";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/identities/edit", method = RequestMethod.GET)
	public String showEditIdentity(final Model model, @RequestParam("id") final Long id, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(!identityService.existsByCompanyId(id)) {
			return "redirect:/admin/data/identities?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("identity", new IdentityResponseForm(id));
		model.addAttribute("detail", identityService.readIdentityDetail(id));
		return "adminDataIdentity";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param identityResponseForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateIdentity(final HttpServletRequest request, @Valid final IdentityResponseForm identityResponseForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Object[] result = identityService.validateIdentity(localUser.getUserId(), identityResponseForm);
		final NotificationType type = identityResponseForm.isResponse() ? NotificationType.identityAccepted : NotificationType.identityRejected;
		eventPublisher.publishEvent(new OnNotificationEvent(null, (Long) result[0], null, type, request));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), identityResponseForm.isResponse() ? ConstraintesJournal.ADMIN_ACCEPT_IDENTITY 
				: ConstraintesJournal.ADMIN_REJECT_IDENTITY, (String) result[1]));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteIdentity(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final String tradename = identityService.deleteIdentity(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_IDENTITY, tradename));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteIdentities(@RequestParam("lines[]") final List<Long> lines, @AuthenticationPrincipal final LocalUser localUser) {
		identityService.deleteIdentities(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_IDENTITIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/postal", method = RequestMethod.GET)
	public String showPostal(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminDataPostal";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/postal-load", method = RequestMethod.GET)
	public String loadPostal(final Model model, @RequestParam("fl") final String filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", postalService.findPostalList(search, sort, rows, page, hasDesc));
		return "adminListPostal";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/postal/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePostal(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final OrderPostal orderPostal = postalService.deletePostal(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_POSTAL, orderPostal.getSerial()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/postal/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePostals(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		postalService.deletePostals(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_POSTALS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/social", method = RequestMethod.GET)
	public String showSocial(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("social", premiumFormuleService.readSocialFooterForm());
		return "adminDataSocial";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param socialFooterForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/social/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateSocial(@Valid final SocialFooterForm socialFooterForm, @AuthenticationPrincipal final LocalUser localUser) {
		premiumFormuleService.updateSocialFooter(socialFooterForm);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UPDATE_SOCIAL, null));
		return new GenericResponse("success");
	}
	
}
