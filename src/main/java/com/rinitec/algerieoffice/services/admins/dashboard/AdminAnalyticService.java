package com.rinitec.algerieoffice.services.admins.dashboard;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.ads.NewsletterRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.ads.SponsoreRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.data.ActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.TestimonialRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.FollowCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.FaqRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.CategoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyCreditRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalAdminRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalUserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ContactRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ReportRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticAccess;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticJournal;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticMarket;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticPremium;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticVisit;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmDashboardLinked;

@Service
public class AdminAnalyticService implements IAdminAnalyticService {
	
	private AnnonceRepository annonceRepository;
	private CampaignRepository campaignRepository;
	private EmployeRepository employeRepository;
	private PromoteRepository promoteRepository;
	private EventRepository eventRepository;
	private PostRepository postRepository;
	private AppointmentRepository appointmentRepository;
	private CollaboratorRepository collaboratorRepository;
	private ContactRepository contactRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private ReportRepository reportRepository;
	private ActualityRepository actualityRepository;
	private FaqRepository faqRepository;
	private WorkRepository workRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	private CompanyCreditRepository companyCreditRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private CompanyLocationRepository companyLocationRepository;
	private CompanySheduleRepository companySheduleRepository;
	private CompanyRepository companyRepository;
	private NewsletterRepository newsletterRepository;
	private SponsoreRepository sponsoreRepository;
	private ActivityRepository activityRepository;
	private BlogRepository blogRepository;
	private TestimonialRepository testimonialRepository;
	private CategoryRepository categoryRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private AccessCompanyRepository accessCompanyRepository;
	private FollowCompanyRepository followCompanyRepository;
	private AccountRepository accountRepository;
	private PremiumRepository premiumRepository;
	private JournalUserRepository journalUserRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private JournalAdminRepository journalAdminRepository;

	@Autowired
	public AdminAnalyticService(AnnonceRepository annonceRepository, CampaignRepository campaignRepository, EmployeRepository employeRepository, 
			PromoteRepository promoteRepository, EventRepository eventRepository, PostRepository postRepository, AppointmentRepository appointmentRepository, 
			CollaboratorRepository collaboratorRepository, ContactRepository contactRepository, EvaluationRepository evaluationRepository, NoticeRepository noticeRepository, 
			ReportRepository reportRepository, ActualityRepository actualityRepository, FaqRepository faqRepository, WorkRepository workRepository, 
			CompanyBriefcaseRepository companyBriefcaseRepository, CompanyCreditRepository companyCreditRepository, CompanyLinkedRepository companyLinkedRepository, 
			CompanyLocationRepository companyLocationRepository, CompanySheduleRepository companySheduleRepository, CompanyRepository companyRepository, 
			NewsletterRepository newsletterRepository, SponsoreRepository sponsoreRepository, ActivityRepository activityRepository, BlogRepository blogRepository, 
			TestimonialRepository testimonialRepository, CategoryRepository categoryRepository, FavoriteAccountRepository favoriteAccountRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, FavoriteDocumentRepository favoriteDocumentRepository, GuestDocumentRepository guestDocumentRepository, 
			AccessCompanyRepository accessCompanyRepository, FollowCompanyRepository followCompanyRepository, AccountRepository accountRepository, 
			PremiumRepository premiumRepository, JournalUserRepository journalUserRepository, JournalCompanyRepository journalCompanyRepository, 
			JournalAdminRepository journalAdminRepository) {
		this.annonceRepository = annonceRepository;
		this.campaignRepository = campaignRepository;
		this.employeRepository = employeRepository;
		this.promoteRepository = promoteRepository;
		this.eventRepository = eventRepository;
		this.postRepository = postRepository;
		this.appointmentRepository = appointmentRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.contactRepository = contactRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.reportRepository = reportRepository;
		this.actualityRepository = actualityRepository;
		this.faqRepository = faqRepository;
		this.workRepository = workRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
		this.companyCreditRepository = companyCreditRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.companyLocationRepository = companyLocationRepository;
		this.companySheduleRepository = companySheduleRepository;
		this.companyRepository = companyRepository;
		this.newsletterRepository = newsletterRepository;
		this.sponsoreRepository = sponsoreRepository;
		this.activityRepository = activityRepository;
		this.blogRepository = blogRepository;
		this.testimonialRepository = testimonialRepository;
		this.categoryRepository = categoryRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.accessCompanyRepository = accessCompanyRepository;
		this.followCompanyRepository = followCompanyRepository;
		this.accountRepository = accountRepository;
		this.premiumRepository = premiumRepository;
		this.journalUserRepository = journalUserRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.journalAdminRepository = journalAdminRepository;
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[][] findDashboardMarketplace() {
		final String[][] dashboardMarketplace = new String[7][4];
		final Long annonce = annonceRepository.count();
		final Long annonceTrashed = annonceRepository.countByHasTrashed(true);
		final Long annoncePublished = annonceRepository.countByHasPublished(true);
		final Long annonceActive = annonceRepository.countAllActiveAnnonce();
		final Long campaign = campaignRepository.count();
		final Long campaignPublished = campaignRepository.countByPublished(true);
		final Long campaignActive = campaignRepository.countAllActiveCampaign();
		final Long employe = employeRepository.count();
		final Long employeTrashed = employeRepository.countByHasTrashed(true);
		final Long employePublished = employeRepository.countByHasPublished(true);
		final Long employeActive = employeRepository.countAllActiveEmploye();
		final Long promote = promoteRepository.count();
		final Long promoteTrashed = promoteRepository.countByHasTrashed(true);
		final Long promoteActive = promoteRepository.countAllActivePromote();
		final Long event = eventRepository.count();
		final Long eventPublished = eventRepository.countByHasPublished(true);
		final Long eventActive = eventRepository.countAllActiveEvent();
		final Long post = postRepository.count();
		final Long postTrashed = postRepository.countByHasTrashed(true);
		final Long postPublished = postRepository.countByHasPublished(true);
		final Long postActive = postRepository.countAllActivePost();
		dashboardMarketplace[0][0] = ParseUtil.getFormattedCount(annonce);
		dashboardMarketplace[0][1] = ParseUtil.getFormattedCount(annonceTrashed);
		dashboardMarketplace[0][2] = ParseUtil.getFormattedCount(annoncePublished);
		dashboardMarketplace[0][3] = ParseUtil.getFormattedCount(annonceActive);
		dashboardMarketplace[1][0] = ParseUtil.getFormattedCount(campaign);
		dashboardMarketplace[1][1] = "--";
		dashboardMarketplace[1][2] = ParseUtil.getFormattedCount(campaignPublished);
		dashboardMarketplace[1][3] = ParseUtil.getFormattedCount(campaignActive);
		dashboardMarketplace[2][0] = ParseUtil.getFormattedCount(employe);
		dashboardMarketplace[2][1] = ParseUtil.getFormattedCount(employeTrashed);
		dashboardMarketplace[2][2] = ParseUtil.getFormattedCount(employePublished);
		dashboardMarketplace[2][3] = ParseUtil.getFormattedCount(employeActive);
		dashboardMarketplace[3][0] = ParseUtil.getFormattedCount(promote);
		dashboardMarketplace[3][1] = ParseUtil.getFormattedCount(promoteTrashed);
		dashboardMarketplace[3][2] = "--";
		dashboardMarketplace[3][3] = ParseUtil.getFormattedCount(promoteActive);
		dashboardMarketplace[4][0] = ParseUtil.getFormattedCount(event);
		dashboardMarketplace[4][1] = "--";
		dashboardMarketplace[4][2] = ParseUtil.getFormattedCount(eventPublished);
		dashboardMarketplace[4][3] = ParseUtil.getFormattedCount(eventActive);
		dashboardMarketplace[5][0] = ParseUtil.getFormattedCount(post);
		dashboardMarketplace[5][1] = ParseUtil.getFormattedCount(postTrashed);
		dashboardMarketplace[5][2] = ParseUtil.getFormattedCount(postPublished);
		dashboardMarketplace[5][3] = ParseUtil.getFormattedCount(postActive);
		dashboardMarketplace[6][0] = ParseUtil.getFormattedCount(annonce + campaign + employe + promote + event + post);
		dashboardMarketplace[6][1] = ParseUtil.getFormattedCount(annonceTrashed + employeTrashed + promoteTrashed + postTrashed);
		dashboardMarketplace[6][2] = ParseUtil.getFormattedCount(annoncePublished + campaignPublished + employePublished + promoteActive + eventPublished + postPublished);
		dashboardMarketplace[6][3] = ParseUtil.getFormattedCount(annonceActive + campaignActive + employeActive + promoteActive + eventActive + postActive);
		return dashboardMarketplace;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardFeedback() {
		final String[] dashboardFeedback = new String[6];
		dashboardFeedback[0] = ParseUtil.getFormattedCount(appointmentRepository.count());
		dashboardFeedback[1] = ParseUtil.getFormattedCount(collaboratorRepository.count());
		dashboardFeedback[2] = ParseUtil.getFormattedCount(contactRepository.count());
		dashboardFeedback[3] = ParseUtil.getFormattedCount(evaluationRepository.count());
		dashboardFeedback[4] = ParseUtil.getFormattedCount(noticeRepository.count());
		dashboardFeedback[5] = ParseUtil.getFormattedCount(reportRepository.count());
		return dashboardFeedback;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[][] findDashboardContent() {
		final String[][] dashboardContent = new String[4][3];
		final Long actus = actualityRepository.count();
		final Long actusPublished = actualityRepository.countByHasPublished(true);
		final Long actusActive = actualityRepository.countAllActyalityMapsite();
		final Long faq = faqRepository.count();
		final Long faqPublished = faqRepository.countByHasPublished(true);
		final Long faqActive = faqRepository.countAllActiveFaq();
		final Long work = workRepository.count();
		final Long workPublished = workRepository.countByHasPublished(true);
		final Long workActive = workRepository.countAllActiveWork();
		dashboardContent[0][0] = ParseUtil.getFormattedCount(actus);
		dashboardContent[0][1] = ParseUtil.getFormattedCount(actusPublished);
		dashboardContent[0][2] = ParseUtil.getFormattedCount(actusActive);
		dashboardContent[1][0] = ParseUtil.getFormattedCount(faq);
		dashboardContent[1][1] = ParseUtil.getFormattedCount(faqPublished);
		dashboardContent[1][2] = ParseUtil.getFormattedCount(faqActive);
		dashboardContent[2][0] = ParseUtil.getFormattedCount(work);
		dashboardContent[2][1] = ParseUtil.getFormattedCount(workPublished);
		dashboardContent[2][2] = ParseUtil.getFormattedCount(workActive);
		dashboardContent[3][0] = ParseUtil.getFormattedCount(actus + faq + work);
		dashboardContent[3][1] = ParseUtil.getFormattedCount(actusPublished + faqPublished + workPublished);
		dashboardContent[3][2] = ParseUtil.getFormattedCount(actusActive + faqActive + workActive);
		return dashboardContent;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmDashboardLinked findAdmDashboardLinked() {
		final Long[] inbox = new Long[5];
		inbox[0] = companyBriefcaseRepository.count();
		inbox[1] = companyCreditRepository.count();
		inbox[2] = companyLinkedRepository.count();
		inbox[3] = companyLocationRepository.count();
		inbox[4] = companySheduleRepository.count();
		return new AdmDashboardLinked(inbox, companyRepository.count());
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardAdmin() {
		final String[] dashboardAdmin = new String[6];
		dashboardAdmin[0] = ParseUtil.getFormattedCount(newsletterRepository.count());
		dashboardAdmin[1] = ParseUtil.getFormattedCount(sponsoreRepository.count());
		dashboardAdmin[2] = ParseUtil.getFormattedCount(activityRepository.count());
		dashboardAdmin[3] = ParseUtil.getFormattedCount(blogRepository.count());
		dashboardAdmin[4] = ParseUtil.getFormattedCount(testimonialRepository.count());
		dashboardAdmin[5] = ParseUtil.getFormattedCount(categoryRepository.count());
		return dashboardAdmin;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String[] findDashboardFavorite() {
		final String[] dashboardFavorite = new String[6];
		dashboardFavorite[0] = ParseUtil.getFormattedCount(favoriteAccountRepository.count());
		dashboardFavorite[1] = ParseUtil.getFormattedCount(favoriteCompanyRepository.count());
		dashboardFavorite[2] = ParseUtil.getFormattedCount(favoriteDocumentRepository.count());
		dashboardFavorite[3] = ParseUtil.getFormattedCount(guestDocumentRepository.count());
		dashboardFavorite[4] = ParseUtil.getFormattedCount(accessCompanyRepository.count());
		dashboardFavorite[5] = ParseUtil.getFormattedCount(followCompanyRepository.count());
		return dashboardFavorite;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticAccess readAdmAnalyticAccess() {
		final Long[] countPro = new Long[7];
		final Long[] countIndividualy = new Long[7];
		for (int i = 0; i < 7; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromDay(6 - i);
			final DateTime end = ParseUtil.getEndDateFromDay(6 - i);
			countPro[i] = accountRepository.countAllLogin(true, begin, end);
			countIndividualy[i] = accountRepository.countAllLogin(false, begin, end);
		}
		return new AdmAnalyticAccess(countPro, countIndividualy);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticPremium readAdmAnalyticPremium() {
		final Long[] countPass = new Long[4];
		final Long[] countCreate = new Long[6];
		final Long[] countExpire = new Long[6];
		final Long countActive = premiumRepository.countAllActivePremium(null);
		for (int i = 0; i < 4; i++) {
			countPass[i] = premiumRepository.countAllActivePremium(i + 1);
		}
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countCreate[i] = premiumRepository.countAllPremium(null, begin, end, true);
			countExpire[i] = premiumRepository.countAllPremium(null, begin, end, false);
		}
		return new AdmAnalyticPremium(countActive, countPass, countCreate, countExpire);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticVisit readAdmAnalyticVisit() {
		final Long[] countAuthentified = new Long[6];
		final Long[] countAnonyme = new Long[6];
		final Long[] countWeb = new Long[2];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countAuthentified[i] = accessCompanyRepository.countAllAccess(begin, end, true);
			countAnonyme[i] = accessCompanyRepository.countAllAccess(begin, end, false);
		}
		for (int i = 0; i < 2; i++) {
			countWeb[i] = accessCompanyRepository.countAccessCompanyByWeb(null, i == 0, null);
		}
		return new AdmAnalyticVisit(countAuthentified, countAnonyme, countWeb);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticMarket readAdmAnalyticMarket(final Integer filter) {
		final Integer currFilter = filter != null && filter >= 1 && filter <= 6 ? filter : null;
		final Long[] countAll = new Long[5];
		final Long[] countPublished = new Long[5];
		countAll[0] = postRepository.countAllPost(currFilter, false);
		countAll[1] = annonceRepository.countAllAnnonce(currFilter, false);
		countAll[2] = eventRepository.countAllEvent(currFilter, false);
		countAll[3] = employeRepository.countAllEmploye(currFilter, false);
		countAll[4] = actualityRepository.countAllActuality(currFilter, false);
		countPublished[0] = postRepository.countAllPost(currFilter, true);
		countPublished[1] = annonceRepository.countAllAnnonce(currFilter, true);
		countPublished[2] = eventRepository.countAllEvent(currFilter, true);
		countPublished[3] = employeRepository.countAllEmploye(currFilter, true);
		countPublished[4] = actualityRepository.countAllActuality(currFilter, true);
		return new AdmAnalyticMarket(countAll, countPublished);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticAccess readAdmAnalyticAnalyse() {
		final Long[] countCreated = new Long[6];
		final Long[] countModified = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countCreated[i] = companyRepository.countAllCompany(begin, end, true);
			countModified[i] = companyRepository.countAllCompany(begin, end, false);
		}
		return new AdmAnalyticAccess(countCreated, countModified);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticJournal readAdmAnalyticJournal() {
		final Long[] countUser = new Long[6];
		final Long[] countCompany = new Long[6];
		final Long[] countAdmin = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countUser[i] = journalUserRepository.countJournalUser(null, begin, end);
			countCompany[i] = journalCompanyRepository.countJournalCompany(null, null, begin, end);
			countAdmin[i] = journalAdminRepository.countJournalAdmin(begin, end);
		}
		return new AdmAnalyticJournal(countUser, countCompany, countAdmin, null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticJournal readAdmAnalyticGuest() {
		final Long[] countPost = new Long[6];
		final Long[] countAnnonce = new Long[6];
		final Long[] countEvent = new Long[6];
		final Long[] countEmploye = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countPost[i] = guestDocumentRepository.countAllGuest(begin, end, 1);
			countAnnonce[i] = guestDocumentRepository.countAllGuest(begin, end, 2);
			countEvent[i] = guestDocumentRepository.countAllGuest(begin, end, 3);
			countEmploye[i] = guestDocumentRepository.countAllGuest(begin, end, 4);
		}
		return new AdmAnalyticJournal(countPost, countAnnonce, countEvent, countEmploye);
	}
	
}
