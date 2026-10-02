package com.rinitec.algerieoffice.services.user.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ChatbotRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.CommentLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ContactRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.communication.AppointForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.communication.AppointDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.AppointLine;
import com.rinitec.algerieoffice.web.modal.company.communication.ChatbotDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.ChatbotLine;
import com.rinitec.algerieoffice.web.modal.company.communication.CollaboratorLine;
import com.rinitec.algerieoffice.web.modal.company.communication.ContactDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.ContactLine;
import com.rinitec.algerieoffice.web.modal.company.communication.EvaluationLine;
import com.rinitec.algerieoffice.web.modal.company.communication.NoticeLine;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;
import com.rinitec.algerieoffice.web.modal.user.communication.UserAppointLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserCommentLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserEvaluationLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserNoticeLine;

@Service
public class CommunicationService implements ICommunicationService {

	private UserRepository userRepository;
	private NoticeRepository noticeRepository;
	private AppointmentRepository appointmentRepository;
	private EvaluationRepository evaluationRepository;
	private CollaboratorRepository collaboratorRepository;
	private ContactRepository contactRepository;
	private ActualityRepository actualityRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private CommentLikeRepository commentLikeRepository;
	private ChatbotRepository chatbotRepository;
	
	@Autowired
	public CommunicationService(UserRepository userRepository, NoticeRepository noticeRepository, AppointmentRepository appointmentRepository, 
			EvaluationRepository evaluationRepository, CollaboratorRepository collaboratorRepository, ContactRepository contactRepository, 
			ActualityRepository actualityRepository, ActualityCommentRepository actualityCommentRepository, CommentLikeRepository commentLikeRepository, 
			ChatbotRepository chatbotRepository) {
		this.userRepository = userRepository;
		this.noticeRepository = noticeRepository;
		this.appointmentRepository = appointmentRepository;
		this.evaluationRepository = evaluationRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.contactRepository = contactRepository;
		this.actualityRepository = actualityRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.commentLikeRepository = commentLikeRepository;
		this.chatbotRepository = chatbotRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findNoticeList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = noticeRepository.countAllNoticeCompanyCriteria(companyId, filter, search);
		final List<NoticeLine> lines = countResult == 0L ? new ArrayList<NoticeLine>() 
				: noticeRepository.findAllNoticeCompanyCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findUserNoticeList(final Long userId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = noticeRepository.countAllNoticeUserCriteria(userId, filter, search);
		final List<UserNoticeLine> lines = countResult == 0L ? new ArrayList<UserNoticeLine>() 
				: noticeRepository.findAllNoticeUserCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Notice validateNotice(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<Notice> uOptional = noticeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Notice notice = uOptional.get();
			notice.setApprouved(true);
			notice.setApprouvedBy(userId);
			return noticeRepository.save(notice);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Notice deleteNotice(final String id, final Long companyId) {
		try {
			final Optional<Notice> uOptional = noticeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
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
	public void deleteNotices(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) noticeRepository.countNotices(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			noticeRepository.deleteNotices(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	public void deleteAllNotices(final Long companyId) {
		noticeRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional
	public void deleteUserNotice(final String id, final Long userId) {
		try {
			final Optional<Notice> uOptional = noticeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Notice notice = uOptional.get();
			noticeRepository.delete(notice);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteUserNotices(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) noticeRepository.countUserNotices(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			noticeRepository.deleteNotices(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllUserNotices(final Long userId) {
		noticeRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAppointList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = appointmentRepository.countAllAppointmentCompanyCriteria(companyId, filter, search);
		final List<AppointLine> lines = countResult == 0L ? new ArrayList<AppointLine>() 
				: appointmentRepository.findAllAppointmentCompanyCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findUserAppointList(final Long userId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = appointmentRepository.countAllAppointmentUserCriteria(userId, filter, search);
		final List<UserAppointLine> lines = countResult == 0L ? new ArrayList<UserAppointLine>() 
				: appointmentRepository.findAllAppointmentUserCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AppointDetail readAppointDetail(final String id, final Long companyId) {
		try {
			final Optional<Appointment> uOptional = appointmentRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Appointment appointment = uOptional.get();
				if(appointment.getCompanyId().equals(companyId) && !appointment.isApprouved()) {
					final UserAccountMini userMini = userRepository.findUserAccountMini(appointment.getUserId());
					return new AppointDetail(appointment, userMini);
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Appointment updateAppointment(final AppointForm appointForm, final Long companyId, final Long userId) {
		final Optional<Appointment> uOptional = appointmentRepository.findById(UUID.fromString(appointForm.getId()));
		if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
			final Appointment appointment = uOptional.get();
			appointment.setAppointDate(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").parseDateTime(appointForm.getAppointDate()
					.concat(" ").concat(ParseUtil.getFormattedClock(appointForm.getClock()))));
			appointment.setApprouved(true);
			appointment.setApprouvedBy(userId);
			return appointmentRepository.save(appointment);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteAppointment(final String id, final Long companyId) {
		try {
			final Optional<Appointment> uOptional = appointmentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Appointment appointment = uOptional.get();
			appointmentRepository.delete(appointment);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAppointments(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) appointmentRepository.countAppointments(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			appointmentRepository.deleteAppointments(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllAppointments(final Long companyId) {
		appointmentRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional
	public void deleteUserAppointment(final String id, final Long userId) {
		try {
			final Optional<Appointment> uOptional = appointmentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Appointment appointment = uOptional.get();
			appointmentRepository.delete(appointment);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteUserAppointments(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) appointmentRepository.countUserAppointments(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			appointmentRepository.deleteAppointments(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllUserAppointments(final Long userId) {
		appointmentRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEvaluationList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = evaluationRepository.countAllEvaluationCompanyCriteria(companyId, filter, search);
		final List<EvaluationLine> lines = countResult == 0L ? new ArrayList<EvaluationLine>() 
				: evaluationRepository.findAllEvaluationCompanyCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findUserEvaluationList(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = evaluationRepository.countAllEvaluationUserCriteria(userId, filter, search);
		final List<UserEvaluationLine> lines = countResult == 0L ? new ArrayList<UserEvaluationLine>() 
				: evaluationRepository.findAllEvaluationUserCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteUserEvaluation(final String id, final Long userId) {
		try {
			final Optional<Evaluation> uOptional = evaluationRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Evaluation evaluation = uOptional.get();
			evaluationRepository.delete(evaluation);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteUserEvaluations(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) evaluationRepository.countUserEvaluations(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			evaluationRepository.deleteUserEvaluations(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllUserEvaluations(final Long userId) {
		evaluationRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findUserCommentList(final Long userId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = actualityCommentRepository.countAllActualityCommentCriteria(userId, filter, search);
		final List<UserCommentLine> lines = countResult == 0L ? new ArrayList<UserCommentLine>() 
				: actualityCommentRepository.findAllActualityCommentCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional(readOnly = true)
	private final Actuality readActuality(final UUID actuId) {
		final Optional<Actuality> uOptional = actualityRepository.findById(actuId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional
	public Actuality deleteUserComment(final String id, final Long userId) {
		try {
			final Optional<ActualityComment> uOptional = actualityCommentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !uOptional.get().getUserId().equals(userId)) {
				throw new NotFoundException("message.error.notfound");
			}
			final ActualityComment actualityComment = uOptional.get();
			commentLikeRepository.deleteByCommentId(actualityComment.getId());
			actualityCommentRepository.delete(actualityComment);
			return readActuality(actualityComment.getActualityId());
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findCollaboratorList(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = collaboratorRepository.countAllCollaboratorCompanyCriteria(companyId, filter, search);
		final List<CollaboratorLine> lines = countResult == 0L ? new ArrayList<CollaboratorLine>() 
				: collaboratorRepository.findAllCollaboratorCompanyCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Collaborator validateCollaborator(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<Collaborator> uOptional = collaboratorRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && uOptional.get().getCompanyId().equals(companyId)) {
				final Collaborator collaborator = uOptional.get();
				collaborator.setApprouved(true);
				collaborator.setApprouvedBy(userId);
				return collaboratorRepository.save(collaborator);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Collaborator deleteCollaborator(final String id, final Long companyId) {
		try {
			final Optional<Collaborator> uOptional = collaboratorRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Collaborator collaborator = uOptional.get();
			collaboratorRepository.delete(collaborator);
			return collaborator;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteCollaborators(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) collaboratorRepository.countCollaborators(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			collaboratorRepository.deleteCollaborators(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllCollaborators(final Long companyId) {
		collaboratorRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findContactList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = contactRepository.countAllContactCriteria(companyId, filter, search);
		final List<ContactLine> lines = countResult == 0L ? new ArrayList<ContactLine>() 
				: contactRepository.findAllContactCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public ContactDetail readContactDetail(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<Contact> uOptional = contactRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Contact contact = uOptional.get();
				final ContactDetail contactDetail = new ContactDetail(contact);
				if(!contact.isApprouved()) {
					contact.setApprouved(true);
					contact.setApprouvedBy(userId);
					contactRepository.save(contact);
				}
				return contactDetail;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Contact deleteContact(final String id, final Long companyId) {
		try {
			final Optional<Contact> uOptional = contactRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
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
	public void deleteContacts(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) contactRepository.countContacts(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			contactRepository.deleteContacts(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllContacts(final Long companyId) {
		contactRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findChatbotList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = chatbotRepository.countAllChatbotCriteria(companyId, filter, search);
		final List<ChatbotLine> lines = countResult == 0L ? new ArrayList<ChatbotLine>() 
				: chatbotRepository.findAllChatbotCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public ChatbotDetail readChatbotDetail(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<Chatbot> uOptional = chatbotRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId()) && uOptional.get().getAccount() == null) {
				final Chatbot chatbot = uOptional.get();
				final ChatbotDetail chatbotDetail = new ChatbotDetail(chatbot);
				if(!chatbot.isConsulted()) {
					chatbot.setConsulted(true);
					chatbot.setConsultedBy(userId);
					chatbotRepository.save(chatbot);
				}
				return chatbotDetail;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Chatbot deleteChatbot(final String id, final Long companyId) {
		try {
			final Optional<Chatbot> uOptional = chatbotRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId()) || uOptional.get().getAccount() != null) {
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
	public void deleteChatbots(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) chatbotRepository.countChatbots(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			chatbotRepository.deleteChatbots(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllChatbots(final Long companyId) {
		chatbotRepository.deleteByCompanyId(companyId);
	}
	
}
