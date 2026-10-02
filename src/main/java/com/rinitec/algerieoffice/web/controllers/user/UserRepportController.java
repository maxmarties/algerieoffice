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

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.user.repport.IRepportService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.user.repport.AssistForm;
import com.rinitec.algerieoffice.web.form.user.repport.ProblemForm;
import com.rinitec.algerieoffice.web.form.user.repport.TestimonialForm;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnSupportEvent;

@Controller
@RequestMapping(value = "/user/repports")
public class UserRepportController {

	private IAttributeService attributeService;
	private IRepportService repportService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserRepportController(IAttributeService attributeService, IRepportService repportService, 
			ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.repportService = repportService;
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
	@RequestMapping(value = "/testimonial", method = RequestMethod.GET)
	public String showTestimonial(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("testimonial", repportService.readTestimonialForm(localUser.getUser()));
		return "userRepportTestimonial";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param testimonialForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/testimonial/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveTestimonial(final HttpServletRequest request, @Valid final TestimonialForm testimonialForm,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!testimonialForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		final Testimonial testimonial = repportService.addTestimonial(testimonialForm);
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), null, testimonial.getId(), NotificationType.testimonial, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_TESTIMONIAL, request));
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
	@RequestMapping(value = "/assist", method = RequestMethod.GET)
	public String showAssist(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("assist", new AssistForm(localUser.getUserId()));
		return "userRepportAssist";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param assistForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/assist/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAssist(final HttpServletRequest request, @Valid final AssistForm assistForm,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!assistForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		repportService.addAssist(assistForm);
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), null, null, NotificationType.assist, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_ASSIST, request));
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
	@RequestMapping(value = "/problem", method = RequestMethod.GET)
	public String showProblem(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("problem", new ProblemForm(localUser.getUserId()));
		return "userRepportProblem";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param problemForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/problem/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveProblem(final HttpServletRequest request, @Valid final ProblemForm problemForm,
			@RequestParam(name = "file", required = false) final MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!problemForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		problemForm.setFile(file);
		repportService.addProblem(problemForm);
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), null, null, NotificationType.problem, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_PROBLEM, request));
		return new GenericResponse("success");
	}
	
}
