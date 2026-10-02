package com.rinitec.algerieoffice.web.controllers.register;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.captcha.ICaptchaService;
import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.services.admins.data.IActivityService;
import com.rinitec.algerieoffice.services.register.IRegistrationService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.register.CompanyForm;
import com.rinitec.algerieoffice.web.form.register.UserForm;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	private ICaptchaService captchaService;
	private IActivityService activityService;
	private IRegistrationService registrationService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public RegisterController(ICaptchaService captchaService, IActivityService activityService, IRegistrationService registrationService,
			ApplicationEventPublisher eventPublisher) {
		this.captchaService = captchaService;
		this.activityService = activityService;
		this.registrationService = registrationService;
		this.eventPublisher = eventPublisher;
	}
	
	@ModelAttribute("recaptchaSiteKey")
    public String getRecaptchaSiteKey() {
        return captchaService.getReCaptchaSite();
    }
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/register/user";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String showUser(final HttpServletRequest request, final Model model, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("user", new UserForm());
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return "registerUser";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param userForm
	 * @return
	 */
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveUser(final HttpServletRequest request, @Valid final UserForm userForm) {
		final String response = request.getParameter("g-recaptcha-response");
		captchaService.processResponse(response);
		final User user = registrationService.registerNewUser(userForm);
		RequestUtil.rememberRegistred(request, user.getEmail());
		eventPublisher.publishEvent(new OnRegisterEvent(user, request, EmailType.confirmRegister));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String showCompany(final HttpServletRequest request, final Model model, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		model.addAttribute("currentConfig", new CurrentConfig(config));
		model.addAttribute("company", new CompanyForm(RequestContextUtils.getLocale(request).getLanguage()));
		model.addAttribute("choseActivities", activityService.findAllChoseActivity());
		return "registerCompany";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * @param request
	 * @param companyForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/company/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveCompany(final HttpServletRequest request, @Valid final CompanyForm companyForm, 
			@RequestParam(name = "file", required = false) MultipartFile file) {
		final String response = request.getParameter("g-recaptcha-response");
		captchaService.processResponse(response);
		final User user = registrationService.registerNewCompany(companyForm);
		RequestUtil.rememberRegistred(request, user.getEmail());
		eventPublisher.publishEvent(new OnRegisterEvent(user, request, EmailType.confirmRegister));
		return new GenericResponse("success");
	}
	
}
