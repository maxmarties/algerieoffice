package com.rinitec.algerieoffice.services.inbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.inbox.InboxRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.NotificationRepository;
import com.rinitec.algerieoffice.persistence.modal.inbox.Inbox;
import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.inbox.NotificationPush;
import com.rinitec.algerieoffice.web.modal.user.feedback.NotificationLine;

@Service
public class NotificationService implements INotificationService {

	private InboxRepository inboxRepository;
	private NotificationRepository notificationRepository;
	
	@Autowired
	public NotificationService(InboxRepository inboxRepository, NotificationRepository notificationRepository) {
		this.inboxRepository = inboxRepository;
		this.notificationRepository = notificationRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countNotification(final Long userId) {
		final Optional<Integer> uOptional = inboxRepository.findCountNotificationById(userId);
		return uOptional.isPresent() ? uOptional.get() : 0;
	}
	
	@Transactional
	private final void incrementCount(final Long userId) {
		final Optional<Integer> uOptional = inboxRepository.findCountNotificationById(userId);
		if(uOptional.isPresent()) {
			final Integer countNotification = uOptional.get() + 1;
			if(countNotification < 100) {
				inboxRepository.updateCountNotificationById(countNotification, userId);
			}
		} else {
			final Inbox inbox = new Inbox(userId);
			inbox.setCountNotification(1);
			inboxRepository.save(inbox);
		}
	}
	
	private final Notification parseNewNotification(final Long userId, final Notification notification) {
		final Notification newNotification = new Notification();
		newNotification.setUserId(userId);
		newNotification.setHasIcon(notification.getHasIcon());
		newNotification.setIconimage(notification.getIconimage());
		newNotification.setNotifiedname(notification.getNotifiedname());
		newNotification.setMessage(notification.getMessage());
		newNotification.setLink(notification.getLink());
		newNotification.setNotifiedDate(notification.getNotifiedDate());
		newNotification.setCmsms(notification.getCmsms());
		newNotification.setConsulted(notification.getConsulted());
		return newNotification;
	}
	
	@Override
	@Transactional
	public List<Notification> addNotifications(final NotificationPush notificationPush) {
		final List<UserMini> users = notificationPush.getUsers();
		final Notification notification = notificationPush.getNotification();
		final List<Notification> notifications = new ArrayList<Notification>();
		for (final UserMini user : users) {
			incrementCount(user.getUserId());
			notifications.add(parseNewNotification(user.getUserId(), notification));
		}
		return notificationRepository.saveAll(notifications);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasAllConsulted(final Long userId) {
		return notificationRepository.countByUserIdAndConsulted(userId, false) == 0L;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Notification> findAllNotification(final Long userId, final Integer page, final Integer rows) {
		return notificationRepository.findAllNotification(userId, PageRequest.of(page - 1, rows));
	}
	
	@Override
	@Transactional
	public void resetInboxNotification(final Long userId) {
		inboxRepository.updateCountNotificationById(0, userId);
	}
	
	@Override
	@Transactional
	public void updateAllConsulted(final Long userId) {
		notificationRepository.updateAllConsultedByUserId(userId);
	}
	
	@Override
	@Transactional
	public String getLinkAndConsultedNotification(final Long userId, final String notificationId) {
		try {
			final UUID uuid = UUID.fromString(notificationId);
			final Optional<Notification> uOptional = notificationRepository.findById(uuid);
			if(uOptional.isPresent() && uOptional.get().getUserId().equals(userId)) {
				final Notification notification = uOptional.get();
				final String linked = notification.getLink();
				notification.setConsulted(true);
				notificationRepository.save(notification);
				return linked;
			}
		} catch (IllegalArgumentException e) {e.printStackTrace();}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findNotificationList(final Long userId, final Boolean filter, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = notificationRepository.countAllNotificationCriteria(userId, filter);
		final List<NotificationLine> lines = countResult == 0L ? new ArrayList<NotificationLine>() 
				: notificationRepository.findAllNotificationCriteria(userId, filter, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void consultNotification(final String id, final Long userId) {
		try {
			final Optional<Notification> uOptional = notificationRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Notification notification = uOptional.get();
			notification.setConsulted(true);
			notificationRepository.save(notification);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteNotification(final String id, final Long userId) {
		try {
			final Optional<Notification> uOptional = notificationRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Notification notification = uOptional.get();
			notificationRepository.delete(notification);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteNotifications(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) notificationRepository.countNotifications(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			
			notificationRepository.deleteNotifications(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllNotifications(final Long userId) {
		notificationRepository.deleteByUserId(userId);
	}
	
}
