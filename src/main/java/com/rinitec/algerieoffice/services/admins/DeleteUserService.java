package com.rinitec.algerieoffice.services.admins;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.AssistRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ProblemRepository;
import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistMemberRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.CommentLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.GuestRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicMarkRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicQuizRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicSignalRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicVoteRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.ChaterRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.InboxRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.MessageRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.NotificationRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.SupportRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalUserRepository;
import com.rinitec.algerieoffice.persistence.dao.token.PasswordResetTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.token.PhoneTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.token.VerificationTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.users.IdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.alerts.AlertPostRepository;
import com.rinitec.algerieoffice.persistence.dao.users.alerts.AlertSettingRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ReportRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.RateRepository;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;

@Service
public class DeleteUserService implements IDeleteUserService {

	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private ProfileRepository profileRepository;
	private RateRepository rateRepository;
	private IdentityRepository identityRepository;
	private AppointmentRepository appointmentRepository;
	private CollaboratorRepository collaboratorRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private ReportRepository reportRepository;
	private InboxRepository inboxRepository;
	private ChaterRepository chaterRepository;
	private SupportRepository supportRepository;
	private MessageRepository messageRepository;
	private NotificationRepository notificationRepository;
	private AlertPostRepository alertPostRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private AgentRepository agentRepository;
	private GuestRepository guestRepository;
	private BlacklistMemberRepository blacklistMemberRepository;
	private BlacklistCompanyRepository blacklistCompanyRepository;
	private AssistRepository assistRepository;
	private ProblemRepository problemRepository;
	private AlertSettingRepository alertSettingRepository;
	private VerificationTokenRepository verificationTokenRepository;
	private PasswordResetTokenRepository passwordResetTokenRepository;
	private PhoneTokenRepository phoneTokenRepository;
	private JournalUserRepository journalUserRepository;
	private ActualityLikeRepository actualityLikeRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private CommentLikeRepository commentLikeRepository;
	private EasylistCompanyRepository easylistCompanyRepository;
	private EasylistDocumentRepository easylistDocumentRepository;
	private TopicRepository topicRepository;
	private TopicCommentRepository topicCommentRepository;
	private TopicLikeRepository topicLikeRepository;
	private TopicLikeCommentRepository topicLikeCommentRepository;
	private TopicMarkRepository topicMarkRepository;
	private TopicSignalRepository topicSignalRepository;
	private TopicQuizRepository topicQuizRepository;
	private TopicVoteRepository topicVoteRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public DeleteUserService(UserRepository userRepository, AccountRepository accountRepository, ProfileRepository profileRepository, RateRepository rateRepository, 
			IdentityRepository identityRepository, AppointmentRepository appointmentRepository, CollaboratorRepository collaboratorRepository, EvaluationRepository evaluationRepository, 
			NoticeRepository noticeRepository, ReportRepository reportRepository, InboxRepository inboxRepository, ChaterRepository chaterRepository, SupportRepository supportRepository, 
			MessageRepository messageRepository, NotificationRepository notificationRepository, AlertPostRepository alertPostRepository, FavoriteAccountRepository favoriteAccountRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, FavoriteDocumentRepository favoriteDocumentRepository, JournalCompanyRepository journalCompanyRepository, 
			AgentRepository agentRepository, GuestRepository guestRepository, BlacklistMemberRepository blacklistMemberRepository, BlacklistCompanyRepository blacklistCompanyRepository, 
			AssistRepository assistRepository, ProblemRepository problemRepository, AlertSettingRepository alertSettingRepository, VerificationTokenRepository verificationTokenRepository, 
			PasswordResetTokenRepository passwordResetTokenRepository, PhoneTokenRepository phoneTokenRepository, JournalUserRepository journalUserRepository, 
			ActualityLikeRepository actualityLikeRepository, ActualityCommentRepository actualityCommentRepository, CommentLikeRepository commentLikeRepository, 
			EasylistCompanyRepository easylistCompanyRepository, EasylistDocumentRepository easylistDocumentRepository, TopicRepository topicRepository, 
			TopicCommentRepository topicCommentRepository, TopicLikeRepository topicLikeRepository, TopicLikeCommentRepository topicLikeCommentRepository, 
			TopicMarkRepository topicMarkRepository, TopicSignalRepository topicSignalRepository, TopicQuizRepository topicQuizRepository, TopicVoteRepository topicVoteRepository, 
			IAvatarService avatarService) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.profileRepository = profileRepository;
		this.rateRepository = rateRepository;
		this.identityRepository = identityRepository;
		this.appointmentRepository = appointmentRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.reportRepository = reportRepository;
		this.inboxRepository = inboxRepository;
		this.chaterRepository = chaterRepository;
		this.supportRepository = supportRepository;
		this.messageRepository = messageRepository;
		this.notificationRepository = notificationRepository;
		this.alertPostRepository = alertPostRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.agentRepository = agentRepository;
		this.guestRepository = guestRepository;
		this.blacklistMemberRepository = blacklistMemberRepository;
		this.blacklistCompanyRepository = blacklistCompanyRepository;
		this.assistRepository = assistRepository;
		this.problemRepository = problemRepository;
		this.alertSettingRepository = alertSettingRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.phoneTokenRepository = phoneTokenRepository;
		this.journalUserRepository = journalUserRepository;
		this.actualityLikeRepository = actualityLikeRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.commentLikeRepository = commentLikeRepository;
		this.easylistCompanyRepository = easylistCompanyRepository;
		this.easylistDocumentRepository = easylistDocumentRepository;
		this.topicRepository = topicRepository;
		this.topicCommentRepository = topicCommentRepository;
		this.topicLikeRepository = topicLikeRepository;
		this.topicLikeCommentRepository = topicLikeCommentRepository;
		this.topicMarkRepository = topicMarkRepository;
		this.topicSignalRepository = topicSignalRepository;
		this.topicQuizRepository = topicQuizRepository;
		this.topicVoteRepository = topicVoteRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional
	public void deleteUser(final User user) {
		if(user.getAdmin()) {
			throw new AccessAuthorityException();
		}
		final Long userId = user.getId();
		final List<UUID> commentsUUID = actualityCommentRepository.findAllCommentUUIDByUserId(userId);
		final List<UUID> topicsUUID = topicRepository.findAllTopicByUser(userId);
		accountRepository.deleteById(userId);
		rateRepository.deleteByUserId(userId);
		rateRepository.deleteByMemberId(userId);
		identityRepository.deleteUser(userId);
		appointmentRepository.deleteByUserId(userId);
		collaboratorRepository.deleteByUserId(userId);
		evaluationRepository.deleteByUserId(userId);
		noticeRepository.deleteByUserId(userId);
		reportRepository.deleteByUserId(userId);
		chaterRepository.deleteByUserId(userId);
		supportRepository.deleteByUserId(userId);
		messageRepository.deleteBySenderId(userId);
		messageRepository.deleteByRecepientId(userId);
		notificationRepository.deleteByUserId(userId);
		alertPostRepository.deleteByUserId(userId);
		favoriteAccountRepository.deleteByUserId(userId);
		favoriteCompanyRepository.deleteByUserId(userId);
		favoriteDocumentRepository.deleteByUserId(userId);
		journalCompanyRepository.deleteByUserId(userId);
		agentRepository.trashUserId(userId);
		guestRepository.deleteByUserId(userId);
		blacklistMemberRepository.deleteByUserId(userId);
		blacklistMemberRepository.deleteByMemberId(userId);
		blacklistCompanyRepository.deleteByUserId(userId);
		assistRepository.deleteByUserId(userId);
		problemRepository.deleteByUserId(userId);
		journalUserRepository.deleteByUserId(userId);
		actualityLikeRepository.deleteByUserId(userId);
		actualityCommentRepository.deleteByUserId(userId);
		commentLikeRepository.deleteByUserId(userId);
		easylistCompanyRepository.deleteByUserId(userId);
		easylistDocumentRepository.deleteByUserId(userId);
		if(!topicsUUID.isEmpty()) {
			final List<UUID> topicComments = topicCommentRepository.findAllTopicCommentByTopicIds(topicsUUID);
			if(!topicComments.isEmpty()) {
				topicLikeCommentRepository.deleteAllTopicLikeCommentByCommentIds(topicComments);
			}
			topicCommentRepository.deleteAllTopicCommentByTopicIds(topicsUUID);
			topicLikeRepository.deleteAllTopicLikeByTopicIds(topicsUUID);
			topicMarkRepository.deleteAllTopicMarkByTopicIds(topicsUUID);
			topicSignalRepository.deleteAllTopicSignalByTopicIds(topicsUUID);
			topicQuizRepository.deleteAllTopicQuizByTopicIds(topicsUUID);
			topicVoteRepository.deleteAllTopicVoteByTopicIds(topicsUUID);
			topicRepository.deleteTopics(topicsUUID);
		}
		if(!commentsUUID.isEmpty()) {
			commentLikeRepository.deleteCommentsLike(commentsUUID);
		}
		if(alertSettingRepository.existsById(userId)) {
			alertSettingRepository.deleteById(userId);
		}
		if(profileRepository.existsById(userId)) {
			final Profile profile = profileRepository.findById(userId).get();
			phoneTokenRepository.deleteByProfile(profile);
			profileRepository.delete(profile);
		}
		if(inboxRepository.existsById(userId)) {
			inboxRepository.deleteById(userId);
		}
		verificationTokenRepository.deleteByUser(user);
		passwordResetTokenRepository.deleteByUser(user);
		userRepository.delete(user);
		avatarService.deleteAvatar(userId, AvatarType.account);
	}
	
}
