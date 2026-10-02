package com.rinitec.algerieoffice.services.user.feedback;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.enums.FileType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ContactRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ReportRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.RateRepository;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.persistence.modal.medias.Filereader;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteAccount;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteCompany;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.form.feedback.AppointmentForm;
import com.rinitec.algerieoffice.web.form.feedback.NoticeForm;
import com.rinitec.algerieoffice.web.form.feedback.RateForm;
import com.rinitec.algerieoffice.web.form.feedback.ReportForm;

@Service
public class FeedbackService implements IFeedbackService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private ContactRepository contactRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private AppointmentRepository appointmentRepository;
	private ReportRepository reportRepository;
	private RateRepository rateRepository;
	private CollaboratorRepository collaboratorRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private IEnvelopeService envelopeService;
	private IFilereaderService filereaderService;
	
	@Autowired
	public FeedbackService(UserRepository userRepository, CompanyRepository companyRepository, PostRepository postRepository, AnnonceRepository annonceRepository, 
			EventRepository eventRepository, EmployeRepository employeRepository, FavoriteAccountRepository favoriteAccountRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, FavoriteDocumentRepository favoriteDocumentRepository, ContactRepository contactRepository, 
			EvaluationRepository evaluationRepository, NoticeRepository noticeRepository, AppointmentRepository appointmentRepository, ReportRepository reportRepository, 
			RateRepository rateRepository, CollaboratorRepository collaboratorRepository, GuestDocumentRepository guestDocumentRepository, IEnvelopeService envelopeService, 
			IFilereaderService filereaderService) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.contactRepository = contactRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.appointmentRepository = appointmentRepository;
		this.reportRepository = reportRepository;
		this.rateRepository = rateRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.envelopeService = envelopeService;
		this.filereaderService = filereaderService;
	}
	
	@Transactional
	private final FavoriteAccount postFavoriteAccount(final Long userId, final Long accountId) {
		FavoriteAccount favoriteAccount = favoriteAccountRepository.findByUserIdAndAccountId(userId, accountId);
		if(favoriteAccount == null) {
			favoriteAccount = new FavoriteAccount(userId, accountId);
		}
		favoriteAccount.setPostedDate(new DateTime(Date.from(Instant.now())));
		return favoriteAccountRepository.save(favoriteAccount);
	}
	
	@Override
	@Transactional
	public String postOrUpdateFavoriteAccount(final Long userId, final Long accountId) {
		postFavoriteAccount(userId, accountId);
		final Optional<String> uOptional = userRepository.findUsernameById(accountId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	@Transactional
	private final FavoriteCompany postFavoriteCompany(final Long userId, final Long companyId, final Integer type) {
		FavoriteCompany favoriteCompany = favoriteCompanyRepository.findByUserIdAndCompanyId(userId, companyId);
		if(favoriteCompany == null) {
			favoriteCompany = new FavoriteCompany(userId, companyId);
		}
		favoriteCompany.setType(type != null ? type : 1);
		favoriteCompany.setPostedDate(new DateTime(Date.from(Instant.now())));
		return favoriteCompanyRepository.save(favoriteCompany);
	}
	
	@Override
	@Transactional
	public String postOrUpdateFavoriteCompany(final Long userId, final Long companyId, final Integer type) {
		postFavoriteCompany(userId, companyId, type);
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	private final DocumentType parseFavoriteType(final String type) {
		switch(type) {
		case "post": return DocumentType.post;
		case "annonce": return DocumentType.annonce;
		case "event": return DocumentType.event;
		default: return DocumentType.employe;
		}
	}
	
	@Transactional(readOnly = true)
	private final Optional<String> findTitleDocument(final UUID documentId, final String type) {
		switch(type) {
		case "post": return postRepository.findTitleByPostId(documentId);
		case "annonce": return annonceRepository.findTitleByAnnonceId(documentId);
		case "event": return eventRepository.findTitleByEventId(documentId);
		default: return employeRepository.findTitleByEmployeId(documentId);
		}
	}
	
	@Transactional
	private final FavoriteDocument postFavoriteDocument(final Long userId, final UUID documentId, final DocumentType type) {
		FavoriteDocument favoriteDocument = favoriteDocumentRepository.findByUserIdAndDocumentIdAndType(userId, documentId, type);
		if(favoriteDocument == null) {
			favoriteDocument = new FavoriteDocument();
			favoriteDocument.setUserId(userId);
			favoriteDocument.setDocumentId(documentId);
			favoriteDocument.setType(type);
		}
		favoriteDocument.setPostedDate(new DateTime(Date.from(Instant.now())));
		return favoriteDocumentRepository.save(favoriteDocument);
	}
	
	@Override
	@Transactional
	public String postOrUpdateFavoriteDocument(final Long userId, final String documentId, final String type) {
		try {
			final UUID uuid = UUID.fromString(documentId);
			final Optional<String> uOptional = findTitleDocument(uuid, type);
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			postFavoriteDocument(userId, uuid, parseFavoriteType(type));
			return uOptional.get();
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional
	private final Evaluation postEvaluation(final Long userId, final Long companyId, final boolean liked, final Integer note) {
		Evaluation evaluation = evaluationRepository.findByUserIdAndCompanyId(userId, companyId);
		if(evaluation == null) {
			evaluation = new Evaluation(userId, companyId);
		}
		evaluation.setLiked(liked);
		evaluation.setNote(note);
		evaluation.setPostedDate(new DateTime(Date.from(Instant.now())));
		return evaluationRepository.save(evaluation);
	}
	
	@Override
	@Transactional
	public String postOrUpdateEvaluation(final Long userId, final Long companyId, final boolean liked, final Integer note) {
		postEvaluation(userId, companyId, liked, note);
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional
	private final Notice postNotice(final Long userId, final NoticeForm noticeForm) {
		final Notice notice = new Notice();
		notice.setUserId(userId);
		notice.setCompanyId(noticeForm.getCompanyNotice());
		notice.setTitle(noticeForm.getTitleNotice());
		notice.setMessage(noticeForm.getMessageNotice());
		notice.setAutorised(noticeForm.isAutorisedNotice());
		notice.setPostedDate(new DateTime(Date.from(Instant.now())));
		return noticeRepository.save(notice);
	}
	
	@Override
	@Transactional
	public String addNotice(final Long userId, final NoticeForm noticeForm) {
		postNotice(userId, noticeForm);
		final Optional<String> uOptional = companyRepository.findTradenameById(noticeForm.getCompanyNotice());
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional
	private final Appointment postAppointment(final Long userId, final AppointmentForm appointmentForm) {
		final Appointment appointment = new Appointment();
		appointment.setUserId(userId);
		appointment.setCompanyId(appointmentForm.getCompanyAppoint());
		appointment.setMotif(appointmentForm.getMotifAppoint());
		appointment.setDegree(appointmentForm.getDegreeAppoint());
		appointment.setForDate(!StringUtils.isEmpty(appointmentForm.getForDateAppoint()) 
				? DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(appointmentForm.getForDateAppoint()) : null);
		appointment.setToDate(!StringUtils.isEmpty(appointmentForm.getToDateAppoint()) 
				? DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(appointmentForm.getToDateAppoint()) : null);
		appointment.setPeriod(!StringUtils.isEmpty(appointmentForm.getPeriodAppoint()) ? appointmentForm.getPeriodAppoint() : null);
		appointment.setPostedDate(new DateTime(Date.from(Instant.now())));
		return appointmentRepository.save(appointment);
	}
	
	@Override
	@Transactional
	public String addAppointment(final Long userId, final AppointmentForm appointmentForm) {
		postAppointment(userId, appointmentForm);
		final Optional<String> uOptional = companyRepository.findTradenameById(appointmentForm.getCompanyAppoint());
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional
	public Report addReport(final Long userId, final ReportForm reportForm) {
		UUID fileId = null;
		final Report report = new Report();
		if(reportForm.isHasFileReport()) {
			final Envelope envelope = envelopeService.addEnvelope(reportForm.getFile(), EnvelopeType.report);
			fileId = envelope.getId();
		}
		report.setUserId(userId);
		report.setCompanyId(reportForm.getCompanyReport());
		report.setType(reportForm.getTypeReport());
		report.setReason(reportForm.getReasonReport());
		report.setFileUUID(fileId);
		report.setPostedDate(new DateTime(Date.from(Instant.now())));
		return reportRepository.save(report);
	}
	
	@Override
	@Transactional
	public Rate addRate(final Long userId, final RateForm rateForm) {
		final Rate rate = new Rate();
		rate.setUserId(userId);
		rate.setMemberId(rateForm.getMemberRate());
		rate.setType(rateForm.getTypeRate());
		rate.setReason(rateForm.getReasonRate());
		rate.setPostedDate(new DateTime(Date.from(Instant.now())));
		return rateRepository.save(rate);
	}
	
	@Transactional
	private final Collaborator postCollaborator(final Long userId, final Long companyId, final String function) {
		final Collaborator collaborator = new Collaborator();
		collaborator.setUserId(userId);
		collaborator.setCompanyId(companyId);
		collaborator.setFunction(function);
		collaborator.setPostedDate(new DateTime(Date.from(Instant.now())));
		return collaboratorRepository.save(collaborator);
	}
	
	@Override
	@Transactional
	public String addCollaborator(final Long userId, final Long companyId, final String function) {
		if(collaboratorRepository.existsByUserId(userId)) {
			throw new AccessLeaderException("message.error.collaborate");
		}
		postCollaborator(userId, companyId, function);
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional
	public Contact postContact(final ExplorerContactForm explorerContactForm) {
		final Contact contact = new Contact();
		contact.setCompanyId(explorerContactForm.getCompanyId());
		contact.setPro(explorerContactForm.getPro());
		contact.setSexe(explorerContactForm.getSexe());
		contact.setObject(explorerContactForm.getObject());
		contact.setFirstName(explorerContactForm.getFirstname());
		contact.setLastName(explorerContactForm.getLastname());
		contact.setFunction(explorerContactForm.getFunction());
		contact.setPhone(explorerContactForm.getPhone());
		contact.setEmail(explorerContactForm.getEmail());
		contact.setPostal(explorerContactForm.getPostal());
		contact.setMessage(explorerContactForm.getMessage());
		contact.setPostedDate(new DateTime(Date.from(Instant.now())));
		return contactRepository.save(contact);
	}
	
	@Override
	@Transactional
	public GuestDocument postGuestDocument(final DocumentContactForm documentContactForm) {
		try {
			UUID fileId = null;
			UUID documentId = UUID.fromString(documentContactForm.getDocumentId());
			if(documentContactForm.isHasFile()) {
				final Filereader filereader = filereaderService.addFilereader(documentContactForm.getFile(), 
						documentContactForm.getCompanyId(), FileType.document);
				fileId = filereader.getId();
			}
			final GuestDocument guestDocument = new GuestDocument();
			guestDocument.setCompanyId(documentContactForm.getCompanyId());
			guestDocument.setUsername(documentContactForm.getName());
			guestDocument.setEmail(documentContactForm.getEmail());
			guestDocument.setPhone(documentContactForm.getPhone());
			guestDocument.setMessage(documentContactForm.getMessage());
			guestDocument.setDocumentId(documentId);
			guestDocument.setFileUUID(fileId);
			guestDocument.setType(documentContactForm.getType());
			guestDocument.setPostedDate(new DateTime(Date.from(Instant.now())));
			return guestDocumentRepository.save(guestDocument);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
}
