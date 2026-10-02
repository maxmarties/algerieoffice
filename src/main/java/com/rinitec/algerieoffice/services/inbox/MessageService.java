package com.rinitec.algerieoffice.services.inbox;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistMemberRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.MessageRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.inbox.MessageInfo;
import com.rinitec.algerieoffice.web.modal.inbox.MessageNotification;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePopup;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePush;
import com.rinitec.algerieoffice.web.modal.user.feedback.MessageLine;

@Service
public class MessageService implements IMessageService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private MessageRepository messageRepository;
	private BlacklistMemberRepository blacklistMemberRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public MessageService(UserRepository userRepository, CompanyRepository companyRepository, 
			MessageRepository messageRepository, BlacklistMemberRepository blacklistMemberRepository, 
			ActiveUserStore activeUserStore) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.messageRepository = messageRepository;
		this.blacklistMemberRepository = blacklistMemberRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countMessages(final Long userId) {
		final long count = messageRepository.countByRecepientIdAndConsulted(userId, false);
		return count >= 100L ? 99 : (int) count;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasAllConsulted(final Long userId) {
		return messageRepository.countByRecepientIdAndConsulted(userId, false) == 0L;
	}
	
	@Transactional(readOnly = true)
	private final boolean hasBlockedMessenger(final Long senderId, final Long recepientId) {
		return blacklistMemberRepository.existsByUserIdAndMemberId(senderId, recepientId) 
				|| blacklistMemberRepository.existsByUserIdAndMemberId(recepientId, senderId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findEmailRecepient(final Long senderId, final Long recepientId) {
		if(!hasBlockedMessenger(senderId, recepientId)) {
			final Optional<String> uOptional = userRepository.findEmailById(recepientId);
			return uOptional.isPresent() ? uOptional.get() : null;
		}
		return null;
	}
	
	@Override
	@Transactional
	public Message addMessage(final MessagePush messagePush) {
		final Message message = new Message();
		message.setSenderId(messagePush.getSenderId());
		message.setRecepientId(messagePush.getRecepientId());
		message.setMessage(messagePush.parseMessage());
		message.setPostedDate(new DateTime(Date.from(Instant.now())));
		message.setEmojis(messagePush.isEmojis());
		return messageRepository.save(message);
	}
	
	@Override
	@Transactional(readOnly = true)
	public MessageInfo readMessageInfo(final Long userId, final Long recepientId) {
		final Object[] companyHref = companyRepository.findCompanyHrefFromUserId(recepientId);
		final boolean hasBlocked = hasBlockedMessenger(userId, recepientId);
		final boolean hasLogin = activeUserStore.hasLogged((String) companyHref[0]);
		return new MessageInfo((String) companyHref[1], (String) companyHref[2], hasBlocked, hasLogin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MessagePopup> findAllMessagePopup(final Long userId, final Long recepientId, final int page, final int rows) {
		return messageRepository.findAllMessagePopupCriteria(userId, recepientId, page, rows);
	}
	
	@Override
	@Transactional
	public void updateConsulted(final Long userId, final String messageId) {
		try {
			messageRepository.updateConsultedMessage(UUID.fromString(messageId), userId);
		} catch (IllegalArgumentException e) {e.printStackTrace();}
	}
	
	@Override
	@Transactional
	public void updateAllConsulted(final Long userId) {
		messageRepository.updateAllConsultedByUserId(userId);
	}
	
	@Override
	@Transactional
	public void updateAllConsulted(final Long userId, final Long senderId) {
		messageRepository.updateAllConsultedByUserIdAndSenderId(userId, senderId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MessageNotification> findAllMessageNotification(final Long userId, final int page, final int rows) {
		final List<MessageNotification> lines = messageRepository.findAllMessageNotification(userId, page, rows);
		if(!lines.isEmpty()) {
			for (final MessageNotification line : lines) {
				final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
				line.updateOnline(hasOnline);
			}
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllUserMessage(final Long userId, final boolean recevied) {
		return messageRepository.findAllUserMessageCriteria(userId, recevied);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findMessagesList(final Long userId, final boolean recevied, final Long filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = messageRepository.countAllMessageCriteria(userId, recevied, filter, search);
		final List<MessageLine> lines = countResult == 0L ? new ArrayList<MessageLine>() 
				: messageRepository.findAllMessageCriteria(userId, recevied, filter, search, sort, rows, page, hasDesc);
		if(!lines.isEmpty()) {
			for (final MessageLine line : lines) {
				final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
				line.updateOnline(hasOnline);
			}
		}
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void consultMessage(final String id, final Long userId) {
		try {
			final Optional<Message> uOptional = messageRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getRecepientId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Message message = uOptional.get();
			message.setConsulted(true);
			messageRepository.save(message);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteMessage(final String id, final Long userId, final boolean recevied) {
		try {
			final Optional<Message> uOptional = messageRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || (recevied && !userId.equals(uOptional.get().getRecepientId())) 
					|| (!recevied && !userId.equals(uOptional.get().getSenderId()))) {
				throw new NotFoundException("message.error.notfound");
			}
			final Message message = uOptional.get();
			messageRepository.delete(message);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteMessages(final List<String> lines, final Long userId, final boolean recevied) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || (recevied && linesUUID.size() != (int) messageRepository.countMessagesRecepient(userId, linesUUID)) 
					|| (!recevied && linesUUID.size() != (int) messageRepository.countMessagesSender(userId, linesUUID))) {
				throw new NotFoundException("message.error.notfound");
			}
			messageRepository.deleteMessages(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllMessages(final Long userId, final boolean recevied) {
		if(recevied) {
			messageRepository.deleteByRecepientId(userId);
		} else {
			messageRepository.deleteBySenderId(userId);
		}
	}
	
}
