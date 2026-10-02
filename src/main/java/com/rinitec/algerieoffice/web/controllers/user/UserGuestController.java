package com.rinitec.algerieoffice.web.controllers.user;

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
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.data.IActivityService;
import com.rinitec.algerieoffice.services.company.team.IGuestService;
import com.rinitec.algerieoffice.services.register.IRegistrationService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.utils.SecurityUtil;
import com.rinitec.algerieoffice.web.form.user.AddcompanyForm;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/guest")
public class UserGuestController {

	private IAttributeService attributeService;
	private IGuestService guestService;
	private IActivityService activityService;
	private IRegistrationService registrationService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserGuestController(IAttributeService attributeService, IGuestService guestService, IActivityService activityService, 
			IRegistrationService registrationService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.guestService = guestService;
		this.activityService = activityService;
		this.registrationService = registrationService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/add-company", method = RequestMethod.GET)
	public String showAddCompany(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("choseActivities", activityService.findAllChoseActivity());
		model.addAttribute("company", new AddcompanyForm(RequestContextUtils.getLocale(request).getLanguage()));
		return "userAddCompany";
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param addcompanyForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/add-company/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveCompany(@AuthenticationPrincipal final LocalUser localUser, @Valid final AddcompanyForm addcompanyForm, 
			@RequestParam(name = "file", required = false) MultipartFile file) {
		addcompanyForm.setFile(file);
		final User user = registrationService.addNewCompany(localUser.getUser(), addcompanyForm);
		SecurityUtil.authWithoutPassword(user);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/communication/contributors", method = RequestMethod.GET)
	public String showContributors(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION04, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION04));
		return "userCommunicationContributors";
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
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
	@RequestMapping(value = "/communication/contributors-load", method = RequestMethod.GET)
	public String loadContributors(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION04, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", guestService.findUserContributorList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListContributors";
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/communication/contributors/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateContributor(final HttpServletRequest request, @RequestParam("id") final Long id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Guest guest = guestService.validateUserGuest(id, localUser.getUser());
		SecurityUtil.authWithoutPassword(localUser.getUser());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), guest.getGuestBy(), null, NotificationType.guestAccepted, request));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/communication/contributors/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteContributor(@RequestParam("id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		guestService.deleteUserGuest(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/communication/contributors/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteContributors(@RequestParam("lines[]") final List<Long> lines, @AuthenticationPrincipal final LocalUser localUser) {
		guestService.deleteUserGuests(lines, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY VISITOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/communication/contributors/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllContributors(@AuthenticationPrincipal final LocalUser localUser) {
		guestService.deleteAllUserGuests(localUser.getUserId());
		return new GenericResponse("success");
	}
	
}
