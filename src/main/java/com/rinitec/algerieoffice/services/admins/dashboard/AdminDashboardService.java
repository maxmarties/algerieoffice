package com.rinitec.algerieoffice.services.admins.dashboard;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistMemberRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.ChaterRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.MessageRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.NotificationRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.SupportRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.TalkRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalUserRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.AvatarRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.BannerRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.EnvelopeRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.FilereaderRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.ImageRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.PhotoRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.ScreenshotRepository;
import com.rinitec.algerieoffice.persistence.dao.users.IdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.alerts.AlertPostRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticCompany;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticLogin;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticUser;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmDashboardInbox;

@Service
public class AdminDashboardService implements IAdminDashboardService {

	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private ProfileRepository profileRepository;
	private IdentityRepository identityRepository;
	private AgentRepository agentRepository;
	private CompanyRepository companyRepository;
	private PremiumRepository premiumRepository;
	private AvatarRepository avatarRepository;
	private BannerRepository bannerRepository;
	private EnvelopeRepository envelopeRepository;
	private FilereaderRepository filereaderRepository;
	private ImageRepository imageRepository;
	private PhotoRepository photoRepository;
	private ScreenshotRepository screenshotRepository;
	private AlertPostRepository alertPostRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private JournalUserRepository journalUserRepository;
	private PostRepository postRepository;
	private PartnerRepository partnerRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private BlacklistCompanyRepository blacklistCompanyRepository;
	private BlacklistMemberRepository blacklistMemberRepository;
	private ActiveUserStore activeUserStore;
	private ChaterRepository chaterRepository;
	private MessageRepository messageRepository;
	private NotificationRepository notificationRepository;
	private SupportRepository supportRepository;
	private TalkRepository talkRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	
	@Autowired
	public AdminDashboardService(UserRepository userRepository, AccountRepository accountRepository, ProfileRepository profileRepository, IdentityRepository identityRepository, 
			AgentRepository agentRepository, CompanyRepository companyRepository, PremiumRepository premiumRepository, AvatarRepository avatarRepository, 
			BannerRepository bannerRepository, EnvelopeRepository envelopeRepository, FilereaderRepository filereaderRepository, ImageRepository imageRepository, 
			PhotoRepository photoRepository, ScreenshotRepository screenshotRepository, AlertPostRepository alertPostRepository, JournalCompanyRepository journalCompanyRepository, 
			JournalUserRepository journalUserRepository, PostRepository postRepository, PartnerRepository partnerRepository, ActualityCommentRepository actualityCommentRepository, 
			BlacklistCompanyRepository blacklistCompanyRepository, BlacklistMemberRepository blacklistMemberRepository, ActiveUserStore activeUserStore, ChaterRepository chaterRepository, 
			MessageRepository messageRepository, NotificationRepository notificationRepository, SupportRepository supportRepository, TalkRepository talkRepository, 
			CompanyBriefcaseRepository companyBriefcaseRepository) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.profileRepository = profileRepository;
		this.identityRepository = identityRepository;
		this.agentRepository = agentRepository;
		this.companyRepository = companyRepository;
		this.premiumRepository = premiumRepository;
		this.avatarRepository = avatarRepository;
		this.bannerRepository = bannerRepository;
		this.envelopeRepository = envelopeRepository;
		this.filereaderRepository = filereaderRepository;
		this.imageRepository = imageRepository;
		this.photoRepository = photoRepository;
		this.screenshotRepository = screenshotRepository;
		this.alertPostRepository = alertPostRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.journalUserRepository = journalUserRepository;
		this.postRepository = postRepository;
		this.partnerRepository = partnerRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.blacklistCompanyRepository = blacklistCompanyRepository;
		this.blacklistMemberRepository = blacklistMemberRepository;
		this.activeUserStore = activeUserStore;
		this.chaterRepository = chaterRepository;
		this.messageRepository = messageRepository;
		this.notificationRepository = notificationRepository;
		this.supportRepository = supportRepository;
		this.talkRepository = talkRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[][] findDashboardData() {
		final String[][] dashboardData = new String[6][3];
		final Long userPro = userRepository.countUserByType(true);
		final Long userIndividualy = userRepository.countUserByType(false);
		final Long agentMale = agentRepository.countAgentsForSector(null, null, true);
		final Long agentWoman = agentRepository.countAgentsForSector(null, null, false);
		dashboardData[0][0] = ParseUtil.getFormattedCount(userPro + userIndividualy);
		dashboardData[0][1] = ParseUtil.getFormattedCount(userPro);
		dashboardData[0][2] = ParseUtil.getFormattedCount(userIndividualy);
		dashboardData[1][0] = ParseUtil.getFormattedCount(userRepository.countByEnabled(true));
		dashboardData[1][1] = ParseUtil.getFormattedCount(userRepository.countByLocked(true));
		dashboardData[1][2] = ParseUtil.getFormattedCount(userRepository.countByExpired(true));
		dashboardData[2][0] = ParseUtil.getFormattedOrder(activeUserStore.countLogged());
		dashboardData[2][1] = ParseUtil.getFormattedCount(profileRepository.count());
		dashboardData[2][2] = ParseUtil.getFormattedCount(identityRepository.count());
		dashboardData[3][0] = ParseUtil.getFormattedCount(agentMale + agentWoman);
		dashboardData[3][1] = ParseUtil.getFormattedCount(agentMale);
		dashboardData[3][2] = ParseUtil.getFormattedCount(agentWoman);
		dashboardData[4][0] = ParseUtil.getFormattedCount(companyRepository.count());
		dashboardData[4][1] = ParseUtil.getFormattedCount(companyRepository.countCompaniesForSector(null, null));
		dashboardData[4][2] = ParseUtil.getFormattedCount(companyRepository.countByEnabled(true));
		dashboardData[5][0] = ParseUtil.getFormattedCount(companyRepository.countByLang("fr"));
		dashboardData[5][1] = ParseUtil.getFormattedCount(companyRepository.countByLang("ar"));
		dashboardData[5][2] = ParseUtil.getFormattedCount(companyRepository.countByLang("en"));
		return dashboardData;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardPremium() {
		final String[] dashboardPremium = new String[5];
		for (int i = 0; i < 5; i++) {
			dashboardPremium[i] = ParseUtil.getFormattedCount(premiumRepository.countAllActivePremium(i));
		}
		return dashboardPremium;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardMedia() {
		final String[] dashboardMedia = new String[7];
		dashboardMedia[0] = ParseUtil.getFormattedCount(avatarRepository.count());
		dashboardMedia[1] = ParseUtil.getFormattedCount(bannerRepository.count());
		dashboardMedia[2] = ParseUtil.getFormattedCount(envelopeRepository.count());
		dashboardMedia[3] = ParseUtil.getFormattedCount(filereaderRepository.count());
		dashboardMedia[4] = ParseUtil.getFormattedCount(imageRepository.count());
		dashboardMedia[5] = ParseUtil.getFormattedCount(photoRepository.count());
		dashboardMedia[6] = ParseUtil.getFormattedCount(screenshotRepository.count());
		return dashboardMedia;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardAlert() {
		final String[] dashboardAlert = new String[2];
		dashboardAlert[0] = ParseUtil.getFormattedCount(alertPostRepository.count());
		dashboardAlert[1] = ParseUtil.getFormattedCount(alertPostRepository.countByEnabled(true));
		return dashboardAlert;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardJournal() {
		final String[] dashboardJournal = new String[2];
		dashboardJournal[0] = ParseUtil.getFormattedCount(journalCompanyRepository.count());
		dashboardJournal[1] = ParseUtil.getFormattedCount(journalUserRepository.count());
		return dashboardJournal;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardDatakey() {
		final String[] dashboardDatakey = new String[6];
		dashboardDatakey[0] = ParseUtil.getFormattedCount(postRepository.countByService(false));
		dashboardDatakey[1] = ParseUtil.getFormattedCount(postRepository.countByService(true));
		dashboardDatakey[2] = ParseUtil.getFormattedCount(partnerRepository.count());
		dashboardDatakey[3] = ParseUtil.getFormattedCount(actualityCommentRepository.count());
		dashboardDatakey[4] = ParseUtil.getFormattedCount(blacklistCompanyRepository.count());
		dashboardDatakey[5] = ParseUtil.getFormattedCount(blacklistMemberRepository.count());
		return dashboardDatakey;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmDashboardInbox findAdmDashboardInbox() {
		final Long[] inbox = new Long[5];
		inbox[0] = chaterRepository.count();
		inbox[1] = messageRepository.count();
		inbox[2] = notificationRepository.count();
		inbox[3] = supportRepository.count();
		inbox[4] = talkRepository.count();
		return new AdmDashboardInbox(inbox);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticLogin findAdmAnalyticLogin() {
		final Long[] logins = new Long[2];
		final Long[] counts = new Long[2];
		final DateTime begin = ParseUtil.getBeginDate(ParseUtil.TODAY);
		final DateTime end = ParseUtil.getEndDate(ParseUtil.TODAY);
		final Long countUser = userRepository.countAllActiveUser();
		final int countLogin = activeUserStore.countLogged();
		logins[0] = accountRepository.countAllLogin(true, begin, end);
		logins[1] = accountRepository.countAllLogin(false, begin, end);
		counts[0] = userRepository.countAllAdmUserCriteria(true, null);
		counts[1] = userRepository.countAllAdmUserCriteria(false, null);
		return new AdmAnalyticLogin(countUser, countLogin, logins, counts);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticCompany findAdmAnalyticCompany() {
		final Long countAll = companyRepository.count();
		final Long[] countAttribut = new Long[2];
		final Long[] countType = new Long[4];
		countAttribut[0] = companyRepository.countAllCompanyForMapsite();
		countAttribut[1] = companyRepository.countAllActiveCompanyCompleted(75);
		for (int i = 0; i < 4; i++) {
			countType[i] = companyBriefcaseRepository.countByTypeForSector(null, null, i + 1);
		}
		return new AdmAnalyticCompany(countAll, countAttribut, countType);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticUser findAdmAnalyticUser() {
		final Long[] countPro = new Long[2];
		final Long[] countSexe = new Long[2];
		final Long[] countAgent = new Long[2];
		final Long countAll = userRepository.count();
		final Long countPingled = agentRepository.countByHasPingled(true);
		for (int i = 0; i < 2; i++) {
			countPro[i] = userRepository.countUserByType(i == 0);
			countSexe[i] = profileRepository.countBySexe(i == 0);
			countAgent[i] = agentRepository.countBySexe(i == 0);
		}
		return new AdmAnalyticUser(countAll, countPro, countSexe, countAgent, countPingled);
	}
	
}
