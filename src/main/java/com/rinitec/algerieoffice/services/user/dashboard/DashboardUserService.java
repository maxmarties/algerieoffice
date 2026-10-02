package com.rinitec.algerieoffice.services.user.dashboard;

import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.SocialProvider;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.TestimonialRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.CommentLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.ChaterRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.MessageRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.SupportRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalUserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.IdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.users.alerts.AlertPostRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.IdentityID;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardAnalytic;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardComment;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardMessage;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardUser;

@Service
public class DashboardUserService implements IDashboardUserService {

	private AccountRepository accountRepository;
	private ProfileRepository profileRepository;
	private IdentityRepository identityRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private AlertPostRepository alertPostRepository;
	private AppointmentRepository appointmentRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private BlogLikeRepository blogLikeRepository;
	private TestimonialRepository testimonialRepository;
	private AccessCompanyRepository accessCompanyRepository;
	private ChaterRepository chaterRepository;
	private MessageRepository messageRepository;
	private SupportRepository supportRepository;
	private CommentLikeRepository commentLikeRepository;
	private ActualityLikeRepository actualityLikeRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private JournalUserRepository journalUserRepository;
	
	@Autowired
	public DashboardUserService(AccountRepository accountRepository, ProfileRepository profileRepository, IdentityRepository identityRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, FavoriteAccountRepository favoriteAccountRepository, FavoriteDocumentRepository favoriteDocumentRepository, 
			AlertPostRepository alertPostRepository, AppointmentRepository appointmentRepository, EvaluationRepository evaluationRepository, NoticeRepository noticeRepository, 
			BlogLikeRepository blogLikeRepository, TestimonialRepository testimonialRepository, AccessCompanyRepository accessCompanyRepository, ChaterRepository chaterRepository, 
			MessageRepository messageRepository, SupportRepository supportRepository, CommentLikeRepository commentLikeRepository, ActualityLikeRepository actualityLikeRepository, 
			ActualityCommentRepository actualityCommentRepository, JournalUserRepository journalUserRepository) {
		this.accountRepository = accountRepository;
		this.profileRepository = profileRepository;
		this.identityRepository = identityRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.alertPostRepository = alertPostRepository;
		this.appointmentRepository = appointmentRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.blogLikeRepository = blogLikeRepository;
		this.testimonialRepository = testimonialRepository;
		this.accessCompanyRepository = accessCompanyRepository;
		this.chaterRepository = chaterRepository;
		this.messageRepository = messageRepository;
		this.supportRepository = supportRepository;
		this.commentLikeRepository = commentLikeRepository;
		this.actualityLikeRepository = actualityLikeRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.journalUserRepository = journalUserRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DashboardUser readDashboardUser(final Long userId) {
		final Long[] countGlobe = new Long[2];
		final Long[] countFavorite = new Long[4];
		final Long[][] countAlert = new Long[4][2];
		final Long countPopularity = favoriteAccountRepository.countByAccountId(userId);
		countGlobe[0] = favoriteCompanyRepository.countByUserId(userId);
		countGlobe[1] = favoriteAccountRepository.countByUserId(userId);
		for(int i = 0; i < 4; i++) {
			countFavorite[i] = favoriteDocumentRepository.countFavoriteDocumentUserByType(userId, i + 1);
			final Object[] countAlertObject = alertPostRepository.countAlertPostUserByType(userId, i + 1);
			countAlert[i][0] = (Long) countAlertObject[0];
			countAlert[i][1] = (Long) countAlertObject[1];
		}
		return new DashboardUser(countPopularity, countGlobe, countFavorite, countAlert);
	}
	
	@Transactional(readOnly = true)
	private final Profile readProfile(final Long userId) {
		final Optional<Profile> uOptional = profileRepository.findById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countCompletedProfile(final User user) {
		int completed = 0;
		final Long userId = user.getId();
		final Profile profile = readProfile(userId);
		if(user.getHasAvatar()) completed += 2;
		if(user.isEnabled()) completed++;
		if(user.getCompanyId() != null) completed += 3;
		if(accountRepository.findHasAccepteById(userId).get()) completed++;
		if(profile != null) {
			completed++;
			if(!StringUtils.isEmpty(profile.getFunction())) completed++;
			if(!StringUtils.isEmpty(profile.getBiography())) completed++;
			if(profile.getHasPhone()) completed++;
			if(profile.isEnabled()) completed += 2;
		}
		if(identityRepository.existsById(new IdentityID(userId, SocialProvider.FACEBOOK.getProviderType()))) completed += 3;
		if(identityRepository.existsById(new IdentityID(userId, SocialProvider.GOOGLE.getProviderType()))) completed += 3;
		if(identityRepository.existsById(new IdentityID(userId, SocialProvider.LINKEDIN.getProviderType()))) completed += 3;
		return (completed * 100) / 22;
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countPerformProfile(final Long userId) {
		int completed = 0;
		if(appointmentRepository.existsByUserId(userId)) completed += 2;
		if(evaluationRepository.existsByUserId(userId)) completed++;
		if(noticeRepository.existsByUserId(userId)) completed += 3;
		if(favoriteAccountRepository.existsByUserId(userId)) completed++;
		if(favoriteCompanyRepository.existsByUserId(userId)) completed++;
		if(favoriteDocumentRepository.existsByUserId(userId)) completed++;
		if(alertPostRepository.existsByUserId(userId)) completed++;
		if(blogLikeRepository.existsByUserId(userId)) completed += 3;
		if(testimonialRepository.existsByUserId(userId)) completed += 5;
		if(accessCompanyRepository.existsByUserId(userId)) completed++;
		if(chaterRepository.existsByUserId(userId)) completed++;
		if(messageRepository.existsBySenderId(userId)) completed++;
		if(messageRepository.existsByRecepientId(userId)) completed++;
		if(supportRepository.existsByUserId(userId)) completed += 3;
		if(actualityLikeRepository.existsByUserId(userId)) completed += 3;
		if(actualityCommentRepository.existsByUserId(userId)) completed += 5;
		if(commentLikeRepository.existsByUserId(userId)) completed += 2;
		return (completed * 100) / 35;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DashboardAnalytic readDashboardAnalytic(final Long userId) {
		final Long[] countAccess = new Long[7];
		final Long[] countFavorites = new Long[7];
		final Long[] countHistories = new Long[7];
		for (int i = 0; i < 7; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromDay(6 - i);
			final DateTime end = ParseUtil.getEndDateFromDay(6 - i);
			countAccess[i] = accessCompanyRepository.countAccessUser(userId, begin, end);
			countFavorites[i] = favoriteDocumentRepository.countFavoriteDocumentUser(userId, begin, end);
			countHistories[i] = journalUserRepository.countJournalUser(userId, begin, end);
		}
		return new DashboardAnalytic(countAccess, countFavorites, countHistories);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardMessage> findLastDashboardMessage(final Long userId, final int limit) {
		return messageRepository.findLastDashboardMessage(userId, limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardComment> findLastDashboardComment(final Long userId, final int limit) {
		return actualityCommentRepository.findLastDashboardComment(userId, limit);
	}
	
}
