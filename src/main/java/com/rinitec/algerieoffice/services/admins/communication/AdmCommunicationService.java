package com.rinitec.algerieoffice.services.admins.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.ads.NewsletterRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ChatbotRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.ChaterRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.MessageRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ContactRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmAppointLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmChatboterLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmChaterLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmContactsLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmGuestLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmMessageLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmNewsletterLine;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmNoticeLine;

@Service
public class AdmCommunicationService implements IAdmCommunicationService {

	private ChaterRepository chaterRepository;
	private MessageRepository messageRepository;
	private ContactRepository contactRepository;
	private AppointmentRepository appointmentRepository;
	private NoticeRepository noticeRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private NewsletterRepository newsletterRepository;
	private ChatbotRepository chatbotRepository;
	
	@Autowired
	public AdmCommunicationService(ChaterRepository chaterRepository, MessageRepository messageRepository, ContactRepository contactRepository, 
			AppointmentRepository appointmentRepository, NoticeRepository noticeRepository, GuestDocumentRepository guestDocumentRepository, 
			NewsletterRepository newsletterRepository, ChatbotRepository chatbotRepository) {
		this.chaterRepository = chaterRepository;
		this.messageRepository = messageRepository;
		this.contactRepository = contactRepository;
		this.appointmentRepository = appointmentRepository;
		this.noticeRepository = noticeRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.newsletterRepository = newsletterRepository;
		this.chatbotRepository = chatbotRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmChaterList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = chaterRepository.countAllChaterAdmin(filter, search);
		final List<AdmChaterLine> lines = countResult == 0L ? new ArrayList<AdmChaterLine>() : 
			chaterRepository.findAllChaterAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Chater deleteChater(final String id) {
		try {
			final Optional<Chater> uOptional = chaterRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Chater chater = uOptional.get();
			chaterRepository.delete(chater);
			return chater;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteChaters(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) chaterRepository.countChaters(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			chaterRepository.deleteChaters(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmMessageList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = messageRepository.countAllMessageAdmin(filter, search);
		final List<AdmMessageLine> lines = countResult == 0L ? new ArrayList<AdmMessageLine>() : 
			messageRepository.findAllMessageAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Message deleteMessage(final String id) {
		try {
			final Optional<Message> uOptional = messageRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Message message = uOptional.get();
			messageRepository.delete(message);
			return message;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteMessages(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) messageRepository.countMessages(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			messageRepository.deleteMessages(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmContactList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = contactRepository.countAllContactAdmin(filter, search);
		final List<AdmContactsLine> lines = countResult == 0L ? new ArrayList<AdmContactsLine>() : 
			contactRepository.findAllContactAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Contact deleteContact(final String id) {
		try {
			final Optional<Contact> uOptional = contactRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Contact contact = uOptional.get();
			contactRepository.delete(contact);
			return contact;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteContacts(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) contactRepository.countContacts(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			contactRepository.deleteContacts(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmAppointList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = appointmentRepository.countAllAppointAdmin(filter, search);
		final List<AdmAppointLine> lines = countResult == 0L ? new ArrayList<AdmAppointLine>() : 
			appointmentRepository.findAllAppointAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Appointment deleteAppoint(final String id) {
		try {
			final Optional<Appointment> uOptional = appointmentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Appointment appointment = uOptional.get();
			appointmentRepository.delete(appointment);
			return appointment;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAppoints(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) appointmentRepository.countAppointments(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			appointmentRepository.deleteAppointments(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmNoticeList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = noticeRepository.countAllNoticeAdmin(filter, search);
		final List<AdmNoticeLine> lines = countResult == 0L ? new ArrayList<AdmNoticeLine>() : 
			noticeRepository.findAllNoticeAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Notice deleteNotice(final String id) {
		try {
			final Optional<Notice> uOptional = noticeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Notice notice = uOptional.get();
			noticeRepository.delete(notice);
			return notice;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteNotices(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) noticeRepository.countNotices(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			noticeRepository.deleteNotices(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmGuestList(final Integer filter, final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = guestDocumentRepository.countAllGuestAdmin(filter, search);
		final List<AdmGuestLine> lines = countResult == 0L ? new ArrayList<AdmGuestLine>() : 
			guestDocumentRepository.findAllGuestAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public GuestDocument deleteGuest(final String id) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final GuestDocument guestDocument = uOptional.get();
			guestDocumentRepository.delete(guestDocument);
			return guestDocument;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteGuests(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) guestDocumentRepository.countGuests(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			guestDocumentRepository.deleteGuests(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmNewsletterList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = newsletterRepository.countAllNewsletterAdmin(filter, search);
		final List<AdmNewsletterLine> lines = countResult == 0L ? new ArrayList<AdmNewsletterLine>() : 
			newsletterRepository.findAllNewsletterAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Newsletter deleteNewsletter(final Long id) {
		final Optional<Newsletter> uOptional = newsletterRepository.findById(id);
		if(!uOptional.isPresent()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Newsletter newsletter = uOptional.get();
		newsletterRepository.delete(newsletter);
		return newsletter;
	}
	
	@Override
	@Transactional
	public void deleteNewsletters(final List<Long> lines) {
		if(lines.isEmpty() || lines.size() != newsletterRepository.countNewsletter(lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		newsletterRepository.deleteNewsletters(lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmChatboterList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = chatbotRepository.countAllAdmChatboterCriteria(filter, search);
		final List<AdmChatboterLine> lines = countResult == 0L ? new ArrayList<AdmChatboterLine>() : 
			chatbotRepository.findAllAdmChatboterCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Chatbot deleteChatboter(final String id) {
		try {
			final Optional<Chatbot> uOptional = chatbotRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Chatbot chatbot = uOptional.get();
			chatbotRepository.delete(chatbot);
			return chatbot;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteChatboters(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) chatbotRepository.countChatbots(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			chatbotRepository.deleteChatbots(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
