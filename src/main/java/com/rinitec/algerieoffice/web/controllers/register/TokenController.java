package com.rinitec.algerieoffice.web.controllers.register;

import java.io.UnsupportedEncodingException
;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.mail.IEmailService;
import com.rinitec.algerieoffice.persistence.modal.token.VerificationToken;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.token.ITokenService;
import com.rinitec.algerieoffice.services.user.IUserService;
import com.rinitec.algerieoffice.services.user.account.IAccountService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.utils.SecurityUtil;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.register.PasswordForm;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;

@Controller
@RequestMapping(value = "/website")
public class TokenController {
	private static final int ENABLED = 1;
	private static final int EXPIRED = 2;
	private static final int INVALID = 3;
	
	private IUserService userService;
	private ITokenService tokenService;
	private IAccountService accountService;
	private IEmailService emailService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public TokenController(IUserService userService, ITokenService tokenService, IAccountService accountService, IEmailService emailService, 
			ApplicationEventPublisher eventPublisher) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.accountService = accountService;
		this.emailService = emailService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param model
	 * @param token
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/confirmation", method = RequestMethod.GET)
	public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam(name = "token", required = false) final String token, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) throws UnsupportedEncodingException {
		if(StringUtils.isEmpty(token)) {
			return "redirect:/404";
		}
		final String result = tokenService.validateVerificationToken(token);
		if(result.equals(ITokenService.TOKEN_VALID)) {
			final User user = tokenService.getUserByVerificationToken(token);
			accountService.activateAccount(user.getId());
			RequestUtil.destroyedRemember(request);
			SecurityUtil.authWithoutPassword(user);
			eventPublisher.publishEvent(new OnNotificationEvent(user.getCompanyId() == null ? user.getId() : user.getCompanyId(), null, null, 
					user.getCompanyId() == null ? NotificationType.registerUser : NotificationType.registerCompany, request));
			return "redirect:/".concat(user.getCompanyId() == null ? "user" : "company").concat("/dashboard?info=verifiedSucc");
		}
		final int tokenState = result.equals(ITokenService.TOKEN_EXPIRED) ? EXPIRED : result.equals(ITokenService.TOKEN_ENABLED) ? ENABLED : INVALID;
		model.addAttribute("tokenState", tokenState);
		model.addAttribute("token", token);
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "confirmLogin";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/resend-token", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse resendToken(final HttpServletRequest request, @RequestParam("email") final String email) {
		final User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new NotFoundException("message.input.notfound");
		}
		VerificationToken verificationToken = tokenService.getVerificationToken(user);
		if(verificationToken == null) {
			verificationToken = tokenService.createVerificationTokenForUser(user, UUID.randomUUID().toString());
		}
		emailService.sendUnsubscribeMail(new OnRegisterEvent(user, request, EmailType.confirmRegister, verificationToken.getToken()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param existingToken
	 * @return
	 */
	@RequestMapping(value = "/resend-newtoken", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse resendNewToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
		final VerificationToken verificationToken = tokenService.generateNewVerificationToken(existingToken);
		final User user = tokenService.getUserByVerificationToken(verificationToken.getToken());
		if (user == null) {
			throw new NotFoundException("message.input.notfound");
		}
		emailService.sendUnsubscribeMail(new OnRegisterEvent(user, request, EmailType.confirmRegister, verificationToken.getToken()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String email) {
		final User user = userService.findUserByEmail(email);
		if(user == null) {
			throw new NotFoundException("message.input.notfound");
		}
		final String token = UUID.randomUUID().toString();
		tokenService.createPasswordResetToken(user, token);
		emailService.sendUnsubscribeMail(new OnRegisterEvent(user, request, EmailType.changePassword, token));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param id
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/update-password", method = RequestMethod.GET)
	public String changePassword(final Model model, @RequestParam(name = "id", required = false) final Long id, 
			@RequestParam(name = "token", required = false) final String token) {
		if(id == null || StringUtils.isEmpty(token)) {
			return "redirect:/404";
		}
		final String result = tokenService.validatePasswordResetToken(id, token);
		if (result.equals(ITokenService.TOKEN_VALID)) {
			return "redirect:/website/update-password/new";
		}
		return "redirect:/users/login?token=" + result;
	}
	
	/**
	 * AUTORITY PASSWORD_PRIVILEGE
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update-password/new", method = RequestMethod.GET)
	public String showUpdatePassword(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		model.addAttribute("pass", new PasswordForm());
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "updateLogin";
	}
	
	/**
	 * AUTORITY PASSWORD_PRIVILEGE
	 * @param passwordForm
	 * @return
	 */
	@RequestMapping(value = "/update-password/save", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse savePassword(@Valid final PasswordForm passwordForm) {
		final User user = SecurityUtil.getAuthentication().getUser();
		userService.changePassword(user, passwordForm.getPassword());
		return new GenericResponse("success");
	}

	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param localUser
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/login-target", method = RequestMethod.GET)
	public String determineTargetLogin(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "info", required = false) final String info) {
		final User user = localUser.getUser();
		final String param = !StringUtils.isEmpty(info) && info.equals("logSucc") ? "logSucc=true" : "info=alreadyLoged";
		final String target = user.getAdmin() ? "admin" : user.getCompanyId() != null ? "company" : "user";
		return "redirect:/".concat(target).concat("/dashboard?").concat(param);
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param model
	 * @param provider
	 * @return
	 */
	@RequestMapping(value = "/signin/{provider}/popup", method = RequestMethod.GET)
	public String showSocialPopup(final Model model, @PathVariable("provider") final String provider) {
		model.addAttribute("provider", provider);
		return "socialPopup";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signin/popup/close", method = RequestMethod.GET)
	public String closeSocialPopup(final HttpServletRequest request, final Model model) {
		if(request.getParameter("error") != null) {
			model.addAttribute("error", request.getParameter("error"));
			model.addAttribute("message", request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
		}
		model.addAttribute("closeWindow", true);
		return "socialPopup";
	}
	
}
