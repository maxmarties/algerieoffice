package com.rinitec.algerieoffice.web.controllers.param;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.inbox.IMessageService;
import com.rinitec.algerieoffice.services.inbox.ISupportService;
import com.rinitec.algerieoffice.ujson.TopicQuizResponse;
import com.rinitec.algerieoffice.ujson.TopicReplyResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.listener.events.OnChaterEvent;
import com.rinitec.algerieoffice.web.modal.inbox.ChaterPush;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePush;
import com.rinitec.algerieoffice.web.modal.inbox.SupportPush;

@Controller
public class SocketController {
	
	private IMessageService messageService;
	private ISupportService supportService;
	private SimpMessagingTemplate messagingTemplate;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public SocketController(IMessageService messageService, ISupportService supportService, SimpMessagingTemplate messagingTemplate, 
			ApplicationEventPublisher eventPublisher) {
		this.messageService = messageService;
		this.supportService = supportService;
		this.messagingTemplate = messagingTemplate;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/csrf", method = RequestMethod.GET)
	@ResponseBody
	public String getCsrfToken(final HttpServletRequest request) {
        final CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return csrf.getToken();
    }
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public String handleHttpMediaTypeNotAcceptableException() {
	    return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
	}

	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param exception
	 * @return
	 */
	@MessageExceptionHandler
	@SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param chaterPush
	 * @param localUser
	 * @return
	 */
	@MessageMapping("/chat.public")
	@SendTo("/topic/public")
	public ChaterPush sendChaterPublic(@Payload final ChaterPush chaterPush, @AuthenticationPrincipal final LocalUser localUser) {
		if(localUser.getCompanyId() == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnChaterEvent(chaterPush));
		return chaterPush;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param topicReplyResponse
	 * @param localUser
	 * @param topicId
	 * @return
	 */
	@MessageMapping("/chat.forums")
	public void sendChaterForums(@Payload final TopicReplyResponse topicReplyResponse, @AuthenticationPrincipal final LocalUser localUser) {
		if(localUser.getCompanyId() == null) {
			throw new AccessAuthorityException();
		}
		messagingTemplate.convertAndSend("/topic/forums/".concat(topicReplyResponse.getTopicId()), topicReplyResponse);
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param topicQuizResponse
	 * @param localUser
	 */
	@MessageMapping("/chat.quiz")
	public void sendChaterQuiz(@Payload final TopicQuizResponse topicQuizResponse, @AuthenticationPrincipal final LocalUser localUser) {
		if(localUser.getCompanyId() == null) {
			throw new AccessAuthorityException();
		}
		messagingTemplate.convertAndSend("/topic/quiz/".concat(topicQuizResponse.getTopicId()), topicQuizResponse);
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param messagePush
	 * @param localUser
	 */
	@MessageMapping("/chat.private")
	public void sendChaterPrivate(@Payload final MessagePush messagePush, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(messagePush.getSenderId()) || localUser.getUserId().equals(messagePush.getRecepientId())) {
			throw new AccessAuthorityException();
		}
		final String email = messageService.findEmailRecepient(messagePush.getSenderId(), messagePush.getRecepientId());
		if(email != null) {
			final Message message = messageService.addMessage(messagePush);
			messagePush.setId(message.getId().toString());
			messagingTemplate.convertAndSendToUser(email, "/queue/messages", messagePush);
		}
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param messagePush
	 * @param localUser
	 */
	@MessageMapping("/chat.keyup")
	public void prepareChaterPrivate(@Payload final MessagePush messagePush, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(messagePush.getSenderId()) || localUser.getUserId().equals(messagePush.getRecepientId())) {
			throw new AccessAuthorityException();
		}
		final String email = messageService.findEmailRecepient(messagePush.getSenderId(), messagePush.getRecepientId());
		if(email != null) {
			messagingTemplate.convertAndSendToUser(email, "/queue/messages", messagePush);
		}
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param supportPush
	 * @param localUser
	 */
	@MessageMapping("/chat.support")
	public void sendChaterSupport(@Payload final SupportPush supportPush, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(supportPush.getUserId())) {
			throw new AccessAuthorityException();
		}
		final Support support = supportService.addSupport(supportPush);
		final List<String> admins = supportService.findAllEmailsAdmin();
		supportPush.setId(support.getId().toString());
		for (final String admin : admins) {
			messagingTemplate.convertAndSendToUser(admin, "/queue/supports", supportPush);
		}
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param supportPush
	 * @param localUser
	 */
	@MessageMapping("/chat.supports")
	public void sendChaterSupports(@Payload final SupportPush supportPush, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(supportPush.getAdminId()) || !localUser.getUser().getAdmin()) {
			throw new AccessAuthorityException();
		}
		final String email = supportService.findUserEmail(supportPush.getUserId());
		if(email != null) {
			final Support support = supportService.addSupport(supportPush);
			final List<String> admins = supportService.findAllEmailsAdminOne(supportPush.getAdminId());
			supportPush.setId(support.getId().toString());
			messagingTemplate.convertAndSendToUser(email, "/queue/support", supportPush);
			for (final String admin : admins) {
				messagingTemplate.convertAndSendToUser(admin, "/queue/supports", supportPush);
			}
		}
	}
	
}
