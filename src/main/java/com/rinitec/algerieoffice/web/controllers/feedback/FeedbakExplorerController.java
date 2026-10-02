package com.rinitec.algerieoffice.web.controllers.feedback;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.captcha.ICaptchaService;
import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.services.admins.realtime.IContactusService;
import com.rinitec.algerieoffice.services.analytic.IReferringService;
import com.rinitec.algerieoffice.services.explorer.IExplorerPromoteService;
import com.rinitec.algerieoffice.services.publics.ISearchService;
import com.rinitec.algerieoffice.services.user.feedback.IFeedbackService;
import com.rinitec.algerieoffice.ujson.ChatbotResponse;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.listener.events.OnAccessDocumentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnDocumentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnEmailEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnTalkEvent;

@Controller
@RequestMapping(value = "/feedback/explorer")
public class FeedbakExplorerController {

	private ICaptchaService captchaService;
	private IFeedbackService feedbackService;
	private IReferringService referringService;
	private ISearchService searchService;
	private IExplorerPromoteService explorerPromoteService;
	private IContactusService contactusService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public FeedbakExplorerController(ICaptchaService captchaService, IFeedbackService feedbackService, IReferringService referringService, 
			ISearchService searchService, IExplorerPromoteService explorerPromoteService, IContactusService contactusService, ApplicationEventPublisher eventPublisher) {
		this.captchaService = captchaService;
		this.feedbackService = feedbackService;
		this.referringService = referringService;
		this.searchService = searchService;
		this.explorerPromoteService = explorerPromoteService;
		this.contactusService = contactusService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param explorerContactForm
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveContact(final HttpServletRequest request, @Valid final ExplorerContactForm explorerContactForm) {
		final String response = request.getParameter("g-recaptcha-response");
		if(!StringUtils.isEmpty(response)) {
			captchaService.processResponse(response);
		}
		feedbackService.postContact(explorerContactForm);
		eventPublisher.publishEvent(new OnTalkEvent(explorerContactForm.getCompanyId(), TalkType.contact));
		eventPublisher.publishEvent(new OnEmailEvent(explorerContactForm.getCompanyId(), request, EmailType.contacts));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/get-phone", method = RequestMethod.POST)
	@ResponseBody
	public String pushGetPhone(@RequestParam("companyId") final Long companyId) {
		referringService.postOrIncrementOutlook(companyId, IReferringService.GET_PHONE);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/app-phone", method = RequestMethod.POST)
	@ResponseBody
	public String pushAppPhone(@RequestParam("companyId") final Long companyId) {
		referringService.postOrIncrementOutlook(companyId, IReferringService.APP_PHONE);
		eventPublisher.publishEvent(new OnTalkEvent(companyId, TalkType.call));
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/send-mail", method = RequestMethod.POST)
	@ResponseBody
	public String pushSendMail(@RequestParam("companyId") final Long companyId) {
		referringService.postOrIncrementOutlook(companyId, IReferringService.SEND_MAIL);
		eventPublisher.publishEvent(new OnTalkEvent(companyId, TalkType.mail));
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	@ResponseBody
	public String pushFollow(@RequestParam("companyId") final Long companyId, @RequestParam("out") final String out) {
		referringService.postOrIncrementFollowCompany(companyId, out);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param documentContactForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/document", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveDocument(final HttpServletRequest request, @Valid final DocumentContactForm documentContactForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		final String response = request.getParameter("g-recaptcha-response");
		if(!StringUtils.isEmpty(response)) {
			captchaService.processResponse(response);
		}
		documentContactForm.setFile(file);
		final GuestDocument guestDocument = feedbackService.postGuestDocument(documentContactForm);
		if(guestDocument != null) {
			eventPublisher.publishEvent(new OnDocumentEvent(guestDocument.getCompanyId(), guestDocument.getType()));
			eventPublisher.publishEvent(new OnAccessDocumentEvent(guestDocument.getDocumentId().toString(), guestDocument.getType(), false));
			eventPublisher.publishEvent(new OnEmailEvent(guestDocument.getCompanyId(), request, ParseUtil.parseEmailType(guestDocument.getType())));
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/promote", method = RequestMethod.GET)
	public String readPromote(final Model model, @RequestParam("companyId") final Long companyId) {
		model.addAttribute("promoteFeedback", explorerPromoteService.readPromoteFeedback(companyId));
		return "explorerFeedbackPromote";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/promote-home", method = RequestMethod.GET)
	public String readPromoteHome(final Model model) {
		model.addAttribute("promoteFeedback", explorerPromoteService.readPromoteFeedbackHome());
		return "explorerFeedbackPromote";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/promote/click", method = RequestMethod.POST)
	@ResponseBody
	public String clickPromote(@RequestParam("id") final String id) {
		explorerPromoteService.incrementClickPromote(id);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/sponsore", method = RequestMethod.GET)
	public String readSponsore(final Model model, @RequestParam("type") final Integer type) {
		model.addAttribute("sponsoreFeedback", explorerPromoteService.readSponsoreFeedback(type));
		return "explorerFeedbackSponsore";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sponsore/click", method = RequestMethod.POST)
	@ResponseBody
	public String clickSponsore(@RequestParam("id") final String id) {
		explorerPromoteService.incrementClickSponsore(id);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/simultude", method = RequestMethod.GET)
	public String loadCompanySimultudes(final Model model, @RequestParam("companyId") final Long companyId) {
		model.addAttribute("list", searchService.findCompanySimultudesList(companyId, 20));
		return "explorerFeedbackSimultude";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param chatbotResponse
	 * @return
	 */
	@RequestMapping(value = "/chatbot-public", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveChatbot(final HttpServletRequest request, @RequestBody final ChatbotResponse chatbotResponse) {
		final Chatbot chatbot = contactusService.addChatbot(chatbotResponse);
		if(chatbotResponse.getCompanyId() != null && chatbotResponse.getAccount() == null) {
			eventPublisher.publishEvent(new OnTalkEvent(chatbotResponse.getCompanyId(), TalkType.chatbot));
		} else {
			eventPublisher.publishEvent(new OnNotificationEvent(null, null, chatbot.getId(), NotificationType.chatbot, request));
		}
		return new GenericResponse("success");
	}
	
}
