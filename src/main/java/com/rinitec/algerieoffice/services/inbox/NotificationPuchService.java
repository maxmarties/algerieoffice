package com.rinitec.algerieoffice.services.inbox;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.enums.LiveType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;
import com.rinitec.algerieoffice.persistence.result.CompanyAvatar;
import com.rinitec.algerieoffice.persistence.result.CompanyLive;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnCommentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReplyEvent;
import com.rinitec.algerieoffice.web.modal.inbox.NotificationPush;

@Service
public class NotificationPuchService implements INotificationPuchService {
	
	private static final String HREF_ADMIN_COMPANY_DETAIL = "/admin/companies/detail?id=";
	private static final String HREF_ADMIN_IDENTITY = "/admin/data/identities/edit?id=";
	private static final String HREF_ADMIN_ORDER = "/admin/marketplace/orders/edit?id=";
	private static final String HREF_ADMIN_PROMOTE = "/admin/marketplace/promotes/edit?id=";
	private static final String HREF_ADMIN_CAMPAIGN = "/admin/marketplace/audiances/edit?id=";
	private static final String HREF_ADMIN_REPORTS = "/admin/feedback/reports";
	private static final String HREF_ADMIN_RATES = "/admin/feedback/rates";
	private static final String HREF_ADMIN_PREMIUM = "/admin/premium/orders/edit?id=";
	private static final String HREF_ADMIN_DEACTIVATE_COMPANY = "/admin/realtime/companies";
	private static final String HREF_ADMIN_DEACTIVATE_USER = "/admin/realtime/users";
	private static final String HREF_ADMIN_TESTIMONIAL = "/admin/realtime/testimonials/edit?id=";
	private static final String HREF_ADMIN_ASSIST = "/admin/realtime/assists";
	private static final String HREF_ADMIN_PROBLEM = "/admin/realtime/problems";
	private static final String HREF_ADMIN_CONTACT = "/admin/realtime/contacts/edit?id=";
	private static final String HREF_ADMIN_CHATBOT = "/admin/realtime/chatbots/edit?id=";
	private static final String HREF_ADMIN_USERS = "/admin/team/users";
	private static final String HREF_ADMIN_COMPANIES = "/admin/companies/all";
	private static final String HREF_ADMIN_EMAILING = "/admin/premium/emailings/edit?id=";
	
	private static final String HREF_COMPANY_IDENTITY = "/company/profile/identity";
	private static final String HREF_COMPANY_MARKETPLACE = "/company/marketplace/promotes";
	private static final String HREF_COMPANY_CAMPAIGNS = "/company/marketplace/campaigns";
	private static final String HREF_COMPANY_USERS = "/company/team/users";
	private static final String HREF_COMPANY_PARTNERS = "/company/portfolio/partners";
	private static final String HREF_COMPANY_SUBSCRIBES = "/company/tools/subscribes";
	private static final String HREF_COMPANY_DETECT = "/company/dashboard/detect";
	private static final String HREF_COMPANY_BUDGET = "/company/newsletter/budget";
	
	private static final String HREF_USER_DASHBOARD = "/user/dashboard";
	private static final String HREF_USER_NOTICE = "/user/communication/notices";
	private static final String HREF_USER_APPOINTMENT = "/user/communication/appointments";
	private static final String HREF_USER_COLLABORATOR = "/user/account/profile";
	
	private static final String HREF_GUEST_COMPANY = "/guest/communication/contributors";
	private static final String HREF_HOME_ACTUALITIES = "/marketplace/actualites/";
	private static final String HREF_TOPIC = "/forums/topic/";
	private static final String HREF_TOPIC_REPLIES = "/forums/comment/";
	

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private TopicCommentRepository topicCommentRepository;
	
	@Autowired
	public NotificationPuchService(UserRepository userRepository, CompanyRepository companyRepository, FavoriteAccountRepository favoriteAccountRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, ActualityCommentRepository actualityCommentRepository, TopicCommentRepository topicCommentRepository) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.topicCommentRepository = topicCommentRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompanyLive findCompanyLive(final Long companyId) {
		try {
			return companyRepository.readCompanyLive(companyId);
		} catch (Exception e) {}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final List<UserMini> findAllAdminMini() {
		return userRepository.findAllAdminMini();
	}
	
	@Transactional(readOnly = true)
	private final NotificationPush readNotificationAdmin(final Long companyId, final String message, final String href, final String cmsms) {
		final Notification notification = new Notification();
		final List<UserMini> admins = findAllAdminMini();
		final CompanyAvatar companyAvatar = companyRepository.readCompanyAvatar(companyId);
		final String urlAvatar = companyAvatar.isHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.company + "&width=40&height=40" 
				: "/static/picts/avatars/company_mini-min.jpg";
		notification.setHasIcon(false);
		notification.setIconimage(urlAvatar);
		notification.setNotifiedname(companyAvatar.getTradename());
		notification.setMessage(message);
		notification.setLink(href);
		notification.setCmsms(cmsms);
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(admins, notification);
	}
	
	@Transactional(readOnly = true)
	private final NotificationPush readNotificationAdminFromUser(final Long userId, final String message, final String href, final String cmsms) {
		final Notification notification = new Notification();
		final List<UserMini> admins = findAllAdminMini();
		final CompanyAvatar userAvatar = userRepository.readUserAvatar(userId);
		final String urlAvatar = userAvatar.isHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + userId + "&type=" + AvatarType.account + "&width=40&height=40" 
				: "/static/picts/avatars/account_mini-min.jpg";
		notification.setHasIcon(false);
		notification.setIconimage(urlAvatar);
		notification.setNotifiedname(userAvatar.getTradename());
		notification.setMessage(message);
		notification.setLink(href);
		notification.setCmsms(cmsms);
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(admins, notification);
	}
	
	private final NotificationPush readNotificationToAdmin(final String message, final String href, final String cmsms) {
		final Notification notification = new Notification();
		final List<UserMini> admins = findAllAdminMini();
		notification.setHasIcon(true);
		notification.setIconimage("icon-notification icon-quote");
		notification.setMessage(message);
		notification.setLink(href);
		notification.setCmsms(cmsms);
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(admins, notification);
	}
	
	@Transactional(readOnly = true)
	private final NotificationPush readNotificationCompany(final Long userId, final String message, final String href, final String cmsms, final boolean response) {
		final Notification notification = new Notification();
		final String email = userRepository.findEmailById(userId).get();
		notification.setHasIcon(true);
		notification.setIconimage("icon-notification icon-".concat(response ? "info" : "attention"));
		notification.setMessage(message);
		notification.setLink(href);
		notification.setCmsms(cmsms);
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(Arrays.asList(new UserMini(userId, email)), notification);
	}
	
	@Transactional(readOnly = true)
	private final NotificationPush readNotificationFromCompany(final Long companyId, final Long userId, final String message, final String href, final String cmsms) {
		final Notification notification = new Notification();
		final String email = userRepository.findEmailById(userId).get();
		final CompanyAvatar companyAvatar = companyRepository.readCompanyAvatar(companyId);
		final String urlAvatar = companyAvatar.isHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.company + "&width=40&height=40" 
				: "/static/picts/avatars/company_mini-min.jpg";
		notification.setHasIcon(false);
		notification.setIconimage(urlAvatar);
		notification.setNotifiedname(companyAvatar.getTradename());
		notification.setMessage(message);
		notification.setLink(href);
		notification.setCmsms(cmsms);
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(Arrays.asList(new UserMini(userId, email)), notification);
	}
	
	@Transactional(readOnly = true)
	private final NotificationPush readNotificationFromUser(final Long fromId, final Long toId, final String message, final String href, final String cmsms) {
		final Notification notification = new Notification();
		final String email = userRepository.findEmailById(toId).get();
		final CompanyAvatar userAvatar = userRepository.readUserAvatar(fromId);
		final String urlAvatar = userAvatar.isHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + fromId + "&type=" + AvatarType.account + "&width=40&height=40" 
				: "/static/picts/avatars/account_mini-min.jpg";
		notification.setHasIcon(false);
		notification.setIconimage(urlAvatar);
		notification.setNotifiedname(userAvatar.getTradename());
		notification.setMessage(message);
		notification.setLink(href);
		notification.setCmsms(cmsms);
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(Arrays.asList(new UserMini(toId, email)), notification);
	}
	
	@Override
	@Transactional(readOnly = true)
	public NotificationPush readNotificationPush(final Long fromId, final Long toId, final UUID uuid, final NotificationType type) {
		switch(type) {
		// company >> admin
		case identity: return readNotificationAdmin(fromId, "txt.inbox.admin.identity", HREF_ADMIN_IDENTITY.concat(fromId.toString()), "building-filled");
		case order: return readNotificationAdmin(fromId, "txt.inbox.admin.promote1", HREF_ADMIN_ORDER.concat(uuid.toString()), "dollar");
		case promoteUpdated: return readNotificationAdmin(fromId, "txt.inbox.admin.promote2", HREF_ADMIN_PROMOTE.concat(uuid.toString()), "pin-1");
		case campaign: return readNotificationAdmin(fromId, "txt.inbox.admin.campaign", HREF_ADMIN_CAMPAIGN.concat(uuid.toString()), "dollar");
		case premium: return readNotificationAdmin(fromId, "txt.inbox.admin.premium", HREF_ADMIN_PREMIUM.concat(uuid.toString()), "bookmark");
		case deactivateCompany: return readNotificationAdmin(fromId, "txt.inbox.admin.deactivate", HREF_ADMIN_DEACTIVATE_COMPANY, "trash-7");
		case registerCompany: return readNotificationAdmin(fromId, "txt.inbox.admin.register", HREF_ADMIN_COMPANIES, "folder-4");
		case emailing: return readNotificationAdmin(fromId, "txt.inbox.admin.emailing", HREF_ADMIN_EMAILING.concat(uuid.toString()), "email");
		
		// user >> admin
		case report: return readNotificationAdmin(fromId, "txt.inbox.admin.report", HREF_ADMIN_REPORTS, "umbrella");
		case rate: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.rate", HREF_ADMIN_RATES, "umbrella");
		case deleteCompany: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.delete", HREF_ADMIN_COMPANY_DETAIL.concat(toId.toString()), "trash-7");
		case deactivateUser: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.deactivate", HREF_ADMIN_DEACTIVATE_USER, "trash-7");
		case testimonial: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.testimonial", HREF_ADMIN_TESTIMONIAL.concat(uuid.toString()), "bug");
		case assist: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.assist", HREF_ADMIN_ASSIST, "bug");
		case problem: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.problem", HREF_ADMIN_PROBLEM, "bug");
		case registerUser: return readNotificationAdminFromUser(fromId, "txt.inbox.admin.register", HREF_ADMIN_USERS, "folder-4");
		
		// >> admin
		case contact: return readNotificationToAdmin("txt.inbox.admin.contact", HREF_ADMIN_CONTACT.concat(uuid.toString()), "mail-alt");
		case chatbot: return readNotificationToAdmin("txt.inbox.admin.chatbot", HREF_ADMIN_CHATBOT.concat(uuid.toString()), "comment-alt-1");
		
		// admin >> company
		case identityAccepted: return readNotificationCompany(toId, "txt.inbox.company.identity1", HREF_COMPANY_IDENTITY, "building-filled", true);
		case identityRejected: return readNotificationCompany(toId, "txt.inbox.company.identity2", HREF_COMPANY_IDENTITY, "building-filled", false);
		case orderAccepted: return readNotificationCompany(toId, "txt.inbox.company.promote1", HREF_COMPANY_MARKETPLACE, "credit-card", true);
		case orderRejected: return readNotificationCompany(toId, "txt.inbox.company.promote2", HREF_COMPANY_MARKETPLACE, "credit-card", false);
		case campaignAccepted: return readNotificationCompany(toId, "txt.inbox.company.campaign1", HREF_COMPANY_CAMPAIGNS, "credit-card", true);
		case campaignRejected: return readNotificationCompany(toId, "txt.inbox.company.campaign2", HREF_COMPANY_CAMPAIGNS, "credit-card", false);
		case premiumAccepted: return readNotificationCompany(toId, "txt.inbox.company.subscribe1", HREF_COMPANY_SUBSCRIBES, "bookmark", true);
		case premiumRejected: return readNotificationCompany(toId, "txt.inbox.company.subscribe2", HREF_COMPANY_SUBSCRIBES, "bookmark", false);
		case emailingAccepted: return readNotificationCompany(toId, "txt.inbox.company.budget1", HREF_COMPANY_BUDGET, "email", true);
		case emailingRejected: return readNotificationCompany(toId, "txt.inbox.company.budget2", HREF_COMPANY_BUDGET, "email", false);
		
		// company >> company
		case partnerAccepted: return readNotificationFromCompany(fromId, toId, "txt.inbox.company.partner1", HREF_COMPANY_PARTNERS, "briefcase");
		case partnerRejected: return readNotificationFromCompany(fromId, toId, "txt.inbox.company.partner2", HREF_COMPANY_PARTNERS, "briefcase");
		
		// user >> company
		case guestAccepted: return readNotificationFromUser(fromId, toId, "txt.inbox.company.guest", HREF_COMPANY_USERS, "user-male");
		case detectVisit: return readNotificationFromUser(fromId, toId, "txt.inbox.user.detect", HREF_COMPANY_DETECT, "home-2");
		
		// company >> user
		case noticeAccepted: return readNotificationFromCompany(fromId, toId, "txt.inbox.user.notice1", HREF_USER_NOTICE, "comment-alt-1");
		case noticeRejected: return readNotificationFromCompany(fromId, toId, "txt.inbox.user.notice2", HREF_USER_NOTICE, "comment-alt-1");
		case appointment: return readNotificationFromCompany(fromId, toId, "txt.inbox.user.appoint", HREF_USER_APPOINTMENT, "calendar");
		case collaborator: return readNotificationFromCompany(fromId, toId, "txt.inbox.user.collaborator", HREF_USER_COLLABORATOR, "guest");
		case guest: return readNotificationFromCompany(fromId, toId, "txt.inbox.user.guest", HREF_GUEST_COMPANY, "building-filled");
		case userRemoved: return readNotificationFromCompany(fromId, toId, "txt.inbox.user.remove", HREF_USER_DASHBOARD, "building-filled");
		
		// user >> user
		case favoriteAccount: return readNotificationFromUser(fromId, toId, "txt.inbox.user.favorite", HREF_USER_DASHBOARD, "globe-4");
		case likeActu: return readNotificationFromUser(fromId, toId, "txt.inbox.user.actulike", HREF_HOME_ACTUALITIES.concat(uuid.toString()), "thumbs-up-2");
		case likeComment: return readNotificationFromUser(fromId, toId, "txt.inbox.user.cmntlike", HREF_HOME_ACTUALITIES.concat(uuid.toString()).concat("?info=comments"), "thumbs-up-2");
		case likedComment: return readNotificationFromUser(fromId, toId, "txt.inbox.user.cmntliked", HREF_HOME_ACTUALITIES.concat(uuid.toString()).concat("?info=comments"), "thumbs-up-2");
		case commentActu: return readNotificationFromUser(fromId, toId, "txt.inbox.user.cmntact", HREF_HOME_ACTUALITIES.concat(uuid.toString()).concat("?info=comments"), "comment");
		case commentRemove: return readNotificationFromUser(fromId, toId, "txt.inbox.user.cmntremove", HREF_HOME_ACTUALITIES.concat(uuid.toString()).concat("?info=comments"), "trash-7");
		case accessProfile: return readNotificationFromUser(fromId, toId, "txt.inbox.user.access", HREF_USER_DASHBOARD, "user-1");
		
		case likeTopic: return readNotificationFromUser(fromId, toId, "txt.inbox.user.topiclike", HREF_TOPIC.concat(uuid.toString()), "thumbs-up-2");
		case likeTopicReply: return readNotificationFromUser(fromId, toId, "txt.inbox.user.replylike", HREF_TOPIC_REPLIES.concat(uuid.toString()), "thumbs-up-2");
		case likedTopicReply: return readNotificationFromUser(fromId, toId, "txt.inbox.user.replyliked", HREF_TOPIC_REPLIES.concat(uuid.toString()), "thumbs-up-2");
		case replyTopic: return readNotificationFromUser(fromId, toId, "txt.inbox.user.replyTopic", HREF_TOPIC.concat(uuid.toString()), "comment");
		case replyCommentTopic: return readNotificationFromUser(fromId, toId, "txt.inbox.user.replyCmnTopic", HREF_TOPIC.concat(uuid.toString()), "comment");
		
		default: return null;
		}
	}
	
	private final Notification readNotificationComment(final OnCommentEvent event, final String message) {
		final Notification notification = new Notification();
		notification.setHasIcon(false);
		notification.setIconimage(event.getUrlAvatar());
		notification.setNotifiedname(event.getUsername());
		notification.setMessage(message);
		notification.setLink(HREF_HOME_ACTUALITIES.concat(event.getActualityId().toString()).concat("?info=comments"));
		notification.setCmsms("comment");
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return notification;
	}
	
	@Override
	@Transactional(readOnly = true)
	public NotificationPush readNotificationPushComment(final OnCommentEvent event) {
		// user >> user
		final List<UserMini> users = actualityCommentRepository.findAllUsersComment(event.getUserId(), event.getActualityId());
		if(!users.isEmpty()) {
			final Notification notification = readNotificationComment(event, "txt.inbox.user.cmntequ");
			return new NotificationPush(users, notification);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public NotificationPush readNotificationPushCommentLive(final OnCommentEvent event) {
		// user >> user
		final List<UserMini> users = favoriteAccountRepository.findAllFavoriteUser(event.getUserId());
		if(!users.isEmpty()) {
			final Notification notification = readNotificationComment(event, "txt.inbox.user.cmntfav");
			return new NotificationPush(users, notification);
		}
		return null;
	}
	
	private final Notification readNotificationReply(final OnReplyEvent event, final String message) {
		final Notification notification = new Notification();
		notification.setHasIcon(false);
		notification.setIconimage(event.getUrlAvatar());
		notification.setNotifiedname(event.getUser().getDisplayName());
		notification.setMessage(message);
		notification.setLink(HREF_TOPIC_REPLIES.concat(event.getTopicComment().getId().toString()));
		notification.setCmsms("comment");
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return notification;
	}
	
	@Override
	public NotificationPush readNotificationPushReply(final OnReplyEvent event) {
		final TopicComment topicComment = event.getTopicComment();
		if(topicComment.getParentUUID() != null && !topicComment.getUserId().equals(event.getUser().getId())) {
			final String email = userRepository.findEmailById(topicComment.getUserId()).get();
			final Notification notification = readNotificationReply(event, "txt.inbox.user.replyCmnt");
			return new NotificationPush(Arrays.asList(new UserMini(topicComment.getUserId(), email)), notification);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public NotificationPush readNotificationPushReplies(final OnReplyEvent event) {
		// user >> user
		final TopicComment topicComment = event.getTopicComment();
		if(topicComment.getParentUUID() == null) {
			final List<UserMini> users = topicCommentRepository.findAllUsersCommentTopic(event.getUser().getId(), event.getTopic().getId());
			if(!users.isEmpty()) {
				final Notification notification = readNotificationReply(event, "txt.inbox.user.replyCmnts");
				return new NotificationPush(users, notification);
			}
		}  else {
			final List<UserMini> users = topicCommentRepository.findAllUsersCommentReply(event.getUser().getId(), topicComment.getParentUUID());
			if(!users.isEmpty()) {
				final Notification notification = readNotificationReply(event, "txt.inbox.user.replySubcmnts");
				return new NotificationPush(users, notification);
			}
		}
		return null;
	}
	
	private final String readLinkLive(final String companyURL, final String identify, final LiveType type) {
		switch(type) {
		case addPost: return ConstraintesURL.getMarketplacePostURL(identify, companyURL);
		case addAnnonce: return ConstraintesURL.getMarketplaceAnnonceURL(identify, companyURL);
		case addEmploye: return ConstraintesURL.getMarketplaceEmployeURL(identify, companyURL);
		case addWork: return ConstraintesURL.getWorkFavoriteURL(companyURL, identify);
		case addActu: case updateActu:return ConstraintesURL.getActualiteFavoriteURL(identify);
		case addEvent: case updateEvent: return ConstraintesURL.getEventFavoriteURL(companyURL, identify);
		default: return null;
		}
	}
	
	private final String readMessageLive(final LiveType type) {
		switch(type) {
		case addPost: return "txt.inbox.live.post1";
		case addAnnonce: return "txt.inbox.live.annonce1";
		case addEmploye: return "txt.inbox.live.employe1";
		case addWork: return "txt.inbox.live.work1";
		case addActu: return "txt.inbox.live.actu1";
		case updateActu: return "txt.inbox.live.actu2";
		case addEvent: return "txt.inbox.live.event1";
		case updateEvent: return "txt.inbox.live.event2";
		default: return null;
		}
	}
	
	private final String readIconLive(final LiveType type) {
		switch(type) {
		case addPost: return "bag";
		case addAnnonce: case addEmploye: return "pin-1";
		case addWork: case addActu: case updateActu: case addEvent: case updateEvent: return "book";
		default: return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public NotificationPush readNotificationPushLive(final CompanyLive companyLive, final String identify, final LiveType type) {
		// company >> users
		final List<UserMini> users = favoriteCompanyRepository.findAllFavoriteUserCompany(companyLive.getComanyId());
		if(!users.isEmpty()) {
			final Notification notification = new Notification();
			notification.setHasIcon(false);
			notification.setIconimage(companyLive.getAvatarURL());
			notification.setNotifiedname(companyLive.getTradename());
			notification.setMessage(readMessageLive(type));
			notification.setLink(readLinkLive(companyLive.getUrl(), identify, type));
			notification.setCmsms(readIconLive(type));
			notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
			return new NotificationPush(users, notification);
		}
		return null;
	}
	
	@Override
	public NotificationPush readNotificationPushAlert(final OnAlertEvent event) {
		// admin >> users
		final Notification notification = new Notification();
		notification.setHasIcon(true);
		notification.setIconimage("icon-notification icon-info");
		notification.setNotifiedname(event.getAlert().getName());
		notification.setMessage("txt.inbox.user.alert");
		notification.setLink(event.getAlert().getResultURL());
		notification.setCmsms("bell-1");
		notification.setNotifiedDate(new DateTime(Date.from(Instant.now())));
		return new NotificationPush(Arrays.asList(new UserMini(event.getAlert().getUserId(), event.getAlert().getEmail())), notification);
	}
	
}
