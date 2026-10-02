package com.rinitec.algerieoffice.task;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ChatbotRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicMarkRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicQuizRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicSignalRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicVoteRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.ChaterRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.MessageRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.NotificationRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.TalkRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalAdminRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalUserRepository;
import com.rinitec.algerieoffice.persistence.dao.token.PasswordResetTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.token.PhoneTokenRepository;
import com.rinitec.algerieoffice.persistence.dao.token.VerificationTokenRepository;
import com.rinitec.algerieoffice.services.inbox.ISupportService;
import com.rinitec.algerieoffice.utils.ParseUtil;

@Service
public class TokensPurgeTask {

	private PhoneTokenRepository phoneTokenRepository;
	private VerificationTokenRepository tokenRepository;
	private PasswordResetTokenRepository passwordTokenRepository;
	private PremiumRepository premiumRepository;
	private NotificationRepository notificationRepository;
	private TalkRepository talkRepository;
	private ChaterRepository chaterRepository;
	private MessageRepository messageRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private AccessCompanyRepository accessCompanyRepository;
	private JournalUserRepository journalUserRepository;
	private JournalAdminRepository journalAdminRepository;
	private ChatbotRepository chatbotRepository;
	private TopicRepository topicRepository;
	private TopicCommentRepository topicCommentRepository;
	private TopicLikeRepository topicLikeRepository;
	private TopicLikeCommentRepository topicLikeCommentRepository;
	private TopicMarkRepository topicMarkRepository;
	private TopicSignalRepository topicSignalRepository;
	private TopicQuizRepository topicQuizRepository;
	private TopicVoteRepository topicVoteRepository;
	private ISupportService supportService;
	
	@Autowired
	public TokensPurgeTask(PhoneTokenRepository phoneTokenRepository, VerificationTokenRepository tokenRepository, PasswordResetTokenRepository passwordTokenRepository, 
			PremiumRepository premiumRepository, NotificationRepository notificationRepository, TalkRepository talkRepository, ChaterRepository chaterRepository, 
			MessageRepository messageRepository, JournalCompanyRepository journalCompanyRepository, AccessCompanyRepository accessCompanyRepository, 
			JournalUserRepository journalUserRepository, JournalAdminRepository journalAdminRepository, ChatbotRepository chatbotRepository, TopicRepository topicRepository, 
			TopicCommentRepository topicCommentRepository, TopicLikeRepository topicLikeRepository, TopicLikeCommentRepository topicLikeCommentRepository, 
			TopicMarkRepository topicMarkRepository, TopicSignalRepository topicSignalRepository, TopicQuizRepository topicQuizRepository, 
			TopicVoteRepository topicVoteRepository, ISupportService supportService) {
		this.phoneTokenRepository = phoneTokenRepository;
		this.tokenRepository = tokenRepository;
		this.passwordTokenRepository = passwordTokenRepository;
		this.premiumRepository = premiumRepository;
		this.notificationRepository = notificationRepository;
		this.talkRepository = talkRepository;
		this.chaterRepository = chaterRepository;
		this.messageRepository = messageRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.accessCompanyRepository = accessCompanyRepository;
		this.journalUserRepository = journalUserRepository;
		this.journalAdminRepository = journalAdminRepository;
		this.chatbotRepository = chatbotRepository;
		this.topicRepository = topicRepository;
		this.topicCommentRepository = topicCommentRepository;
		this.topicLikeRepository = topicLikeRepository;
		this.topicLikeCommentRepository = topicLikeCommentRepository;
		this.topicMarkRepository = topicMarkRepository;
		this.topicSignalRepository = topicSignalRepository;
		this.topicQuizRepository = topicQuizRepository;
		this.topicVoteRepository = topicVoteRepository;
		this.supportService = supportService;
	}
	
	@Scheduled(cron = "${purge.cron.expression}", zone = "Europe/Paris")
	@Transactional
	public void purgeExpired() {
		final DateTime now = ParseUtil.getYesterdayFromNow();
		final DateTime sixsub = ParseUtil.getSubSixmonthForNow();
		final DateTime yearsub = ParseUtil.getSubYearForNow();
		//-1 day
		phoneTokenRepository.deleteAllExpiredSince(now);
		tokenRepository.deleteAllExpiredSince(now);
		passwordTokenRepository.deleteAllExpiredSince(now);
		premiumRepository.disabledAllExpiredSince(now);
		chaterRepository.deleteAllExpiredSince(now);
		//-6 month
		accessCompanyRepository.deleteAllExpiredSince(sixsub);
		journalCompanyRepository.deleteAllExpiredSince(sixsub);
		journalUserRepository.deleteAllExpiredSince(sixsub);
		journalAdminRepository.deleteAllExpiredSince(sixsub);
		chatbotRepository.deleteAllExpiredSince(sixsub);
		supportService.deleteAllExpiredSince(sixsub);
		//-12 month
		messageRepository.deleteAllExpiredSince(yearsub);
		notificationRepository.deleteAllExpiredSince(yearsub);
		talkRepository.deleteAllExpiredSince(yearsub);
		final List<UUID> topicsId = topicRepository.findAllExpiredTopicId(yearsub);
		if(!topicsId.isEmpty()) {
			final List<UUID> commentsId = topicCommentRepository.findAllTopicCommentByTopicIds(topicsId);
			if(!commentsId.isEmpty()) {
				topicLikeCommentRepository.deleteAllTopicLikeCommentByCommentIds(commentsId);
			}
			topicCommentRepository.deleteAllTopicCommentByTopicIds(topicsId);
			topicLikeRepository.deleteAllTopicLikeByTopicIds(topicsId);
			topicMarkRepository.deleteAllTopicMarkByTopicIds(topicsId);
			topicSignalRepository.deleteAllTopicSignalByTopicIds(topicsId);
			topicQuizRepository.deleteAllTopicQuizByTopicIds(topicsId);
			topicVoteRepository.deleteAllTopicVoteByTopicIds(topicsId);
			topicRepository.deleteTopics(topicsId);
		}
	}

}
