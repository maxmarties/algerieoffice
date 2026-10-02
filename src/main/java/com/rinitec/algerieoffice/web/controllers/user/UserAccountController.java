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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.persistence.modal.token.PhoneToken;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.publics.IMemberService;
import com.rinitec.algerieoffice.services.token.ITokenService;
import com.rinitec.algerieoffice.services.user.IUserService;
import com.rinitec.algerieoffice.services.user.account.IProfileService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.InvalidPasswordException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.user.account.CoordinatesForm;
import com.rinitec.algerieoffice.web.form.user.account.LoginForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.listener.events.OnValidatePhoneEvent;

@Controller
@RequestMapping(value = "/user/account")
public class UserAccountController {

	private IAttributeService attributeService;
	private IUserService userService;
	private IProfileService profileService;
	private IMemberService memberService;
	private ITokenService tokenService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserAccountController(IAttributeService attributeService, IUserService userService, IProfileService profileService, 
			IMemberService memberService, ITokenService tokenService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.userService = userService;
		this.profileService = profileService;
		this.memberService = memberService;
		this.tokenService = tokenService;
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
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfile(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("profile", memberService.readPrivateProfile(localUser.getUser()));
		return "userAccountProfile";
	}

	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/coordinates", method = RequestMethod.GET)
	public String showCoordinates(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("coordinates", profileService.readCoordinatesForm(localUser.getUserId()));
		return "userAccountCoordinates";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param coordinatesForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/coordinates/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateCoordinates(@Valid final CoordinatesForm coordinatesForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!coordinatesForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		profileService.updateProfile(coordinatesForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_COORDINATES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/identities", method = RequestMethod.GET)
	public String showIdentities(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("identities", profileService.readIdentitiesForm(localUser.getUser()));
		return "userAccountIdentities";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/remembred", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse remembredIdentity(final HttpServletRequest request, @RequestParam("id") final Long id,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(id)) {
			throw new AccessAuthorityException();
		}
		RequestUtil.rememberIdentity(request, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param provider
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse removeIdentity(@RequestParam("id") final Long id, @RequestParam("provider") final String provider,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(id)) {
			throw new AccessAuthorityException();
		}
		profileService.deleteIdentity(id, provider);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_IDENTITIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/create-token", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse createPhoneToken(final HttpServletRequest request, @RequestParam("id") final Long id,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(id)) {
			throw new AccessAuthorityException();
		}
		final String token = ParseUtil.generateVerificationSMS(6);
		final PhoneToken phoneToken = tokenService.createPhoneTokenForUser(id, token);
		if(phoneToken == null)  {
			throw new InvalidResourceException();
		}
		eventPublisher.publishEvent(new OnValidatePhoneEvent(id, token));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param token
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identities/validate-token", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validatePhoneToken(@RequestParam("id") final Long id, @RequestParam("token") final String token,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(id)) {
			throw new AccessAuthorityException();
		}
		final String result = tokenService.validatePhoneToken(id, token);
		if(result == null) {
			throw new InvalidResourceException();
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_IDENTITIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("loginform", profileService.readLoginForm(localUser.getUser()));
		model.addAttribute("urlProfiles", ConstraintesURL.URL_PROFILES.concat("/"));
		return "userAccountLogin";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param pseudo
	 * @return
	 */
	@RequestMapping(value = "/login/check-pseudo", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse checkPseudo(@RequestParam("pseudo") final String pseudo) {
		if(profileService.existsByPseudo(pseudo)) {
			throw new UrlUnavailableException("message.error.url");
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param loginForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/login/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateLogin(final HttpServletRequest request, @Valid final LoginForm loginForm,
			@RequestParam(name = "file", required = false) final MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!loginForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		if(!userService.checkIfValidOldPassword(localUser.getUser(), loginForm.getPassword())) {
			throw new InvalidPasswordException("message.input.invalid");
		}
		loginForm.setFile(file);
		final User user = profileService.updateLogin(loginForm, localUser.getUser());
		if(!user.isEnabled()) {
			eventPublisher.publishEvent(new OnRegisterEvent(user, request, EmailType.updateLogin));
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ACCOUNT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String showStarted(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		return "userAccountHelp";
	}
	
}
