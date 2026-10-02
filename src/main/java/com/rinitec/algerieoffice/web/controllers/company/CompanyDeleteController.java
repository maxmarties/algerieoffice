package com.rinitec.algerieoffice.web.controllers.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.realtime.IDeactivateService;
import com.rinitec.algerieoffice.services.company.team.ITeamService;
import com.rinitec.algerieoffice.services.user.IUserService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.InvalidPasswordException;
import com.rinitec.algerieoffice.web.form.company.tools.DeactivateForm;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvents;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;

@Controller
@RequestMapping(value = "/company/delete")
public class CompanyDeleteController {

	private IAttributeService attributeService;
	private ITeamService teamService;
	private IUserService userService;
	private IDeactivateService deactivateService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyDeleteController(IAttributeService attributeService, ITeamService teamService, IUserService userService, 
			IDeactivateService deactivateService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.teamService = teamService;
		this.userService = userService;
		this.deactivateService = deactivateService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showDeactivate(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		if(teamService.hasSuperAdmin(localUser.getUserId(), localUser.getCompanyId())) {
			model.addAttribute("deactivate", new DeactivateForm(localUser.getCompanyId()));
		}
		return "companyToolsDeactivate";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param deactivateForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveDeactivate(final HttpServletRequest request, @Valid final DeactivateForm deactivateForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!deactivateForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		if(!userService.checkIfValidOldPassword(localUser.getUser(), deactivateForm.getPassword())) {
			throw new InvalidPasswordException("message.input.invalid");
		}
		final List<String> emails = deactivateService.deactivateCompany(deactivateForm);
		eventPublisher.publishEvent(new OnLogoutEvents(emails));
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, null, NotificationType.deactivateCompany, request));
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), localUser.getCompanyId(), null, NotificationType.deleteCompany, request));
		return new GenericResponse("success");
	}
	
}
