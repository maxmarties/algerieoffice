package com.rinitec.algerieoffice.web.controllers.register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;

@Controller
@RequestMapping(value = "/users")
public class LoginController {
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirmation", method = RequestMethod.GET)
	public String showConfirmation(final HttpServletRequest request, final Model model, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final String email = RequestUtil.getRemembredRegistred(request);
		if(email == null) {
			return "redirect:/register/user?notFound=true";
		}
		model.addAttribute("email", email);
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "registerConfirm";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin(final HttpServletRequest request, final Model model, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(request.getParameter("error") != null) {
			model.addAttribute("error", request.getParameter("error"));
			model.addAttribute("message", request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
		}
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("token", request.getParameter("token"));
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "homeLogin";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @return
	 */
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public String showResetPassword(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "resetLogin";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @return
	 */
	@RequestMapping(value = "/resend-token", method = RequestMethod.GET)
	public String showResendToken(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "resendLogin";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param username
	 * @param password
	 * @param remember
	 * @return
	 */
	@RequestMapping(value = "/login-ajax", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse performLogin(final HttpServletRequest request, @RequestParam("username") final String username,
			@RequestParam("password") final String password, @RequestParam("remember") final boolean remember) {
		try {
			request.login(username, password);
		} catch (Exception e) {
			throw new InvalidImageException("auth.message.badCredentials");
		}
		return new GenericResponse("success");
	}
	
}
