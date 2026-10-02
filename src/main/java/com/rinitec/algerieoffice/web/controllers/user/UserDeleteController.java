package com.rinitec.algerieoffice.web.controllers.user;

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

import com.rinitec.algerieoffice.enums.EmailType;
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
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;

@Controller
@RequestMapping(value = "/user/delete")
public class UserDeleteController {

	private IAttributeService attributeService;
	private ITeamService teamService;
	private IUserService userService;
	private IDeactivateService deactivateService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserDeleteController(IAttributeService attributeService, ITeamService teamService, IUserService userService, 
			IDeactivateService deactivateService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.teamService = teamService;
		this.userService = userService;
		this.deactivateService = deactivateService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showDeactivate(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		if(localUser.getCompanyId() == null || !teamService.hasSuperAdmin(localUser.getUserId(), localUser.getCompanyId())) {
			model.addAttribute("deactivate", new DeactivateForm(localUser.getUserId()));
		}
		return "userAccountDeactivate";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
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
		if(!deactivateForm.getCompanyId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		if(!userService.checkIfValidOldPassword(localUser.getUser(), deactivateForm.getPassword())) {
			throw new InvalidPasswordException("message.input.invalid");
		}
		deactivateService.deactivateUser(deactivateForm);
		eventPublisher.publishEvent(new OnRegisterEvent(localUser.getUser(), request, EmailType.deactivateUser));
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), null, null, NotificationType.deactivateUser, request));
		return new GenericResponse("success");
	}
	
}
