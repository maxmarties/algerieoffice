package com.rinitec.algerieoffice.services.admins;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ChatbotRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.FollowCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.OutlookRepository;
import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.AppearanceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.MaindisplayRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.PreferencesRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.SettingsRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.StickyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.WidgetB2CRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignTargetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.company.newsletter.BudgetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.newsletter.MaintemplateRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainaboutRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MaincatalogRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainheaderRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainoverviewRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainsliderRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainthinkRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventCalendarRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.FaqRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.CategoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostPhotoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyCreditRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyIdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.GuestRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestPartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.CatalogItemRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.SliderItemRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.ThinkItemRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.TimelineRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.BriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.CreditTaxeRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.CreditTruckRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.DaySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.profile.LinkedWebsiteRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.TalkRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ContactRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ReportRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainslider;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainthink;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.services.medias.IImageService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;

@Service
public class DeleteCompanyService implements IDeleteCompanyService {

	private CompanyRepository companyRepository;
	private PremiumRepository premiumRepository;
	private OutlookRepository outlookRepository;
	private FollowCompanyRepository followCompanyRepository;
	private AccessCompanyRepository accessCompanyRepository;
	private BlacklistCompanyRepository blacklistCompanyRepository;
	private AppearanceRepository appearanceRepository;
	private MaindisplayRepository maindisplayRepository;
	private PreferencesRepository preferencesRepository;
	private SettingsRepository settingsRepository;
	private StickyRepository stickyRepository;
	private WidgetB2CRepository widgetB2CRepository;
	private AnnonceRepository annonceRepository;
	private AnnonceDetailRepository annonceDetailRepository;
	private AnnonceActivityRepository annonceActivityRepository;
	private AnnonceWilayaRepository annonceWilayaRepository;
	private CampaignRepository campaignRepository;
	private CampaignTargetRepository campaignTargetRepository;
	private EmployeRepository employeRepository;
	private EmployeDetailRepository employeDetailRepository;
	private EmployeLocationRepository employeLocationRepository;
	private PromoteRepository promoteRepository;
	private PromoteActivityRepository promoteActivityRepository;
	private PromoteWilayaRepository promoteWilayaRepository;
	private MainaboutRepository mainaboutRepository;
	private MaincatalogRepository maincatalogRepository;
	private CatalogItemRepository catalogItemRepository;
	private MainheaderRepository mainheaderRepository;
	private MainoverviewRepository mainoverviewRepository;
	private MainsliderRepository mainsliderRepository;
	private SliderItemRepository sliderItemRepository;
	private MainthinkRepository mainthinkRepository;
	private ThinkItemRepository thinkItemRepository;
	private TimelineRepository timelineRepository;
	private ActualityRepository actualityRepository;
	private EventRepository eventRepository;
	private EventDetailRepository eventDetailRepository;
	private EventCalendarRepository eventCalendarRepository;
	private FaqRepository faqRepository;
	private PartnerRepository partnerRepository;
	private WorkRepository workRepository;
	private WorkDetailRepository workDetailRepository;
	private CategoryRepository categoryRepository;
	private PostRepository postRepository;
	private PostDetailRepository postDetailRepository;
	private PostSearchRepository postSearchRepository;
	private PostPhotoRepository postPhotoRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	private BriefcaseRepository briefcaseRepository;
	private CompanyCreditRepository companyCreditRepository;
	private CreditTaxeRepository creditTaxeRepository;
	private CreditTruckRepository creditTruckRepository;
	private CompanyIdentityRepository companyIdentityRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private LinkedWebsiteRepository linkedWebsiteRepository;
	private CompanyLocationRepository companyLocationRepository;
	private CompanySheduleRepository companySheduleRepository;
	private DaySheduleRepository daySheduleRepository;
	private AgentRepository agentRepository;
	private GuestRepository guestRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private GuestPartnerRepository guestPartnerRepository;
	private TalkRepository talkRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private AppointmentRepository appointmentRepository;
	private CollaboratorRepository collaboratorRepository;
	private ContactRepository contactRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private ReportRepository reportRepository;
	private ChatbotRepository chatbotRepository;
	private MaintemplateRepository maintemplateRepository;
	private BudgetRepository budgetRepository;
	private CompanySeoRepository companySeoRepository;
	private CompanyAccountRepository companyAccountRepository;
	private CompanySearchRepository companySearchRepository;
	private IFilereaderService filereaderService;
	private IPhotoService photoService;
	private IImageService imageService;
	private IAvatarService avatarService;
	
	@Autowired
	public DeleteCompanyService(CompanyRepository companyRepository, PremiumRepository premiumRepository, OutlookRepository outlookRepository, 
			FollowCompanyRepository followCompanyRepository, AccessCompanyRepository accessCompanyRepository, BlacklistCompanyRepository blacklistCompanyRepository, 
			AppearanceRepository appearanceRepository, MaindisplayRepository maindisplayRepository, PreferencesRepository preferencesRepository, 
			SettingsRepository settingsRepository, StickyRepository stickyRepository, WidgetB2CRepository widgetB2CRepository, AnnonceRepository annonceRepository, 
			AnnonceDetailRepository annonceDetailRepository, AnnonceActivityRepository annonceActivityRepository, AnnonceWilayaRepository annonceWilayaRepository, 
			CampaignRepository campaignRepository, CampaignTargetRepository campaignTargetRepository, EmployeRepository employeRepository, 
			EmployeDetailRepository employeDetailRepository, EmployeLocationRepository employeLocationRepository, PromoteRepository promoteRepository, 
			PromoteActivityRepository promoteActivityRepository, PromoteWilayaRepository promoteWilayaRepository, MainaboutRepository mainaboutRepository, 
			MaincatalogRepository maincatalogRepository, CatalogItemRepository catalogItemRepository, MainheaderRepository mainheaderRepository, 
			MainoverviewRepository mainoverviewRepository, MainsliderRepository mainsliderRepository, SliderItemRepository sliderItemRepository, 
			MainthinkRepository mainthinkRepository, ThinkItemRepository thinkItemRepository, TimelineRepository timelineRepository, 
			ActualityRepository actualityRepository, EventRepository eventRepository, EventDetailRepository eventDetailRepository, 
			EventCalendarRepository eventCalendarRepository, FaqRepository faqRepository, PartnerRepository partnerRepository, 
			WorkRepository workRepository, WorkDetailRepository workDetailRepository, CategoryRepository categoryRepository, 
			PostRepository postRepository, PostDetailRepository postDetailRepository, PostSearchRepository postSearchRepository, 
			PostPhotoRepository postPhotoRepository, CompanyBriefcaseRepository companyBriefcaseRepository, BriefcaseRepository briefcaseRepository, 
			CompanyCreditRepository companyCreditRepository, CreditTaxeRepository creditTaxeRepository, CreditTruckRepository creditTruckRepository, 
			CompanyIdentityRepository companyIdentityRepository, CompanyLinkedRepository companyLinkedRepository, LinkedWebsiteRepository linkedWebsiteRepository, 
			CompanyLocationRepository companyLocationRepository, CompanySheduleRepository companySheduleRepository, DaySheduleRepository daySheduleRepository, 
			AgentRepository agentRepository, GuestRepository guestRepository, GuestDocumentRepository guestDocumentRepository, 
			GuestPartnerRepository guestPartnerRepository, TalkRepository talkRepository, JournalCompanyRepository journalCompanyRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, FavoriteDocumentRepository favoriteDocumentRepository, 
			AppointmentRepository appointmentRepository, CollaboratorRepository collaboratorRepository, ContactRepository contactRepository, 
			EvaluationRepository evaluationRepository, NoticeRepository noticeRepository, ReportRepository reportRepository, ChatbotRepository chatbotRepository,
			MaintemplateRepository maintemplateRepository, BudgetRepository budgetRepository, CompanySeoRepository companySeoRepository, 
			CompanyAccountRepository companyAccountRepository, CompanySearchRepository companySearchRepository, IFilereaderService filereaderService, 
			IPhotoService photoService, IImageService imageService, IAvatarService avatarService) {
		this.companyRepository = companyRepository;
		this.premiumRepository = premiumRepository;
		this.outlookRepository = outlookRepository;
		this.followCompanyRepository = followCompanyRepository;
		this.accessCompanyRepository = accessCompanyRepository;
		this.blacklistCompanyRepository = blacklistCompanyRepository;
		this.appearanceRepository = appearanceRepository;
		this.maindisplayRepository = maindisplayRepository;
		this.preferencesRepository = preferencesRepository;
		this.settingsRepository = settingsRepository;
		this.stickyRepository = stickyRepository;
		this.widgetB2CRepository = widgetB2CRepository;
		this.annonceRepository = annonceRepository;
		this.annonceDetailRepository = annonceDetailRepository;
		this.annonceActivityRepository = annonceActivityRepository;
		this.annonceWilayaRepository = annonceWilayaRepository;
		this.campaignRepository = campaignRepository;
		this.campaignTargetRepository = campaignTargetRepository;
		this.employeRepository = employeRepository;
		this.employeDetailRepository = employeDetailRepository;
		this.employeLocationRepository = employeLocationRepository;
		this.promoteRepository = promoteRepository;
		this.promoteActivityRepository = promoteActivityRepository;
		this.promoteWilayaRepository = promoteWilayaRepository;
		this.mainaboutRepository = mainaboutRepository;
		this.maincatalogRepository = maincatalogRepository;
		this.catalogItemRepository = catalogItemRepository;
		this.mainheaderRepository = mainheaderRepository;
		this.mainoverviewRepository = mainoverviewRepository;
		this.mainsliderRepository = mainsliderRepository;
		this.sliderItemRepository = sliderItemRepository;
		this.mainthinkRepository = mainthinkRepository;
		this.thinkItemRepository = thinkItemRepository;
		this.timelineRepository = timelineRepository;
		this.actualityRepository = actualityRepository;
		this.eventRepository = eventRepository;
		this.eventDetailRepository = eventDetailRepository;
		this.eventCalendarRepository = eventCalendarRepository;
		this.faqRepository = faqRepository;
		this.partnerRepository = partnerRepository;
		this.workRepository = workRepository;
		this.workDetailRepository = workDetailRepository;
		this.categoryRepository = categoryRepository;
		this.postRepository = postRepository;
		this.postDetailRepository = postDetailRepository;
		this.postSearchRepository = postSearchRepository;
		this.postPhotoRepository = postPhotoRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
		this.briefcaseRepository = briefcaseRepository;
		this.companyCreditRepository = companyCreditRepository;
		this.creditTaxeRepository = creditTaxeRepository;
		this.creditTruckRepository = creditTruckRepository;
		this.companyIdentityRepository = companyIdentityRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.linkedWebsiteRepository = linkedWebsiteRepository;
		this.companyLocationRepository = companyLocationRepository;
		this.companySheduleRepository = companySheduleRepository;
		this.daySheduleRepository = daySheduleRepository;
		this.agentRepository = agentRepository;
		this.guestRepository = guestRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.guestPartnerRepository = guestPartnerRepository;
		this.talkRepository = talkRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.appointmentRepository = appointmentRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.contactRepository = contactRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.reportRepository = reportRepository;
		this.maintemplateRepository = maintemplateRepository;
		this.budgetRepository = budgetRepository;
		this.chatbotRepository = chatbotRepository;
		this.companySeoRepository = companySeoRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.companySearchRepository = companySearchRepository;
		this.filereaderService = filereaderService;
		this.photoService = photoService;
		this.imageService = imageService;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional
	public Company deleteCompany(final Long companyId) {
		final Optional<Company> uOptional = companyRepository.findById(companyId);
		if(!uOptional.isPresent()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Company company = uOptional.get();
		premiumRepository.deleteByCompanyId(companyId);
		accessCompanyRepository.deleteByCompanyId(companyId);
		blacklistCompanyRepository.deleteByCompanyId(companyId);
		timelineRepository.deleteByCompanyId(companyId);
		actualityRepository.deleteByCompanyId(companyId);
		faqRepository.deleteByCompanyId(companyId);
		partnerRepository.deleteByCompanyId(companyId);
		categoryRepository.deleteByCompanyId(companyId);
		guestRepository.deleteByCompanyId(companyId);
		guestDocumentRepository.deleteByCompanyId(companyId);
		guestPartnerRepository.deleteByCompanyId(companyId);
		talkRepository.deleteByCompanyId(companyId);
		journalCompanyRepository.deleteByCompanyId(companyId);
		favoriteCompanyRepository.deleteByCompanyId(companyId);
		appointmentRepository.deleteByCompanyId(companyId);
		collaboratorRepository.deleteByCompanyId(companyId);
		contactRepository.deleteByCompanyId(companyId);
		evaluationRepository.deleteByCompanyId(companyId);
		noticeRepository.deleteByCompanyId(companyId);
		reportRepository.deleteByCompanyId(companyId);
		chatbotRepository.deleteByCompanyId(companyId);
		if(outlookRepository.existsById(companyId)) {
			outlookRepository.deleteById(companyId);
		}
		if(followCompanyRepository.existsById(companyId)) {
			followCompanyRepository.deleteById(companyId);
		}
		if(appearanceRepository.existsById(companyId)) {
			appearanceRepository.deleteById(companyId);
		}
		if(maindisplayRepository.existsById(companyId)) {
			maindisplayRepository.deleteById(companyId);
		}
		if(preferencesRepository.existsById(companyId)) {
			preferencesRepository.deleteById(companyId);
		}
		if(settingsRepository.existsById(companyId)) {
			settingsRepository.deleteById(companyId);
		}
		if(stickyRepository.existsById(companyId)) {
			stickyRepository.deleteById(companyId);
		}
		if(widgetB2CRepository.existsById(companyId)) {
			widgetB2CRepository.deleteById(companyId);
		}
		if(mainaboutRepository.existsById(companyId)) {
			mainaboutRepository.deleteById(companyId);
		}
		if(mainheaderRepository.existsById(companyId)) {
			mainheaderRepository.deleteById(companyId);
		}
		if(mainoverviewRepository.existsById(companyId)) {
			mainoverviewRepository.deleteById(companyId);
		}
		if(companyIdentityRepository.existsById(companyId)) {
			companyIdentityRepository.deleteById(companyId);
		}
		if(companyLocationRepository.existsById(companyId)) {
			companyLocationRepository.deleteById(companyId);
		}
		if(maintemplateRepository.existsById(companyId)) {
			maintemplateRepository.deleteById(companyId);
		}
		if(budgetRepository.existsById(companyId)) {
			budgetRepository.deleteById(companyId);
		}
		final Optional<Maincatalog> uMaincatalog = maincatalogRepository.findById(companyId);
		if(uMaincatalog.isPresent()) {
			final Maincatalog maincatalog = uMaincatalog.get();
			catalogItemRepository.deleteAll(maincatalog.getItems());
			maincatalogRepository.delete(maincatalog);
		}
		final Optional<Mainslider> uMainslider = mainsliderRepository.findById(companyId);
		if(uMainslider.isPresent()) {
			final Mainslider mainslider = uMainslider.get();
			sliderItemRepository.deleteAll(mainslider.getItems());
			mainsliderRepository.delete(mainslider);
		}
		final Optional<Mainthink> uMainthink = mainthinkRepository.findById(companyId);
		if(uMainthink.isPresent()) {
			final Mainthink mainthink = uMainthink.get();
			thinkItemRepository.deleteAll(mainthink.getItems());
			mainthinkRepository.delete(mainthink);
		}
		final Optional<CompanyBriefcase> uCompanyBriefcase = companyBriefcaseRepository.findById(companyId);
		if(uCompanyBriefcase.isPresent()) {
			final CompanyBriefcase companyBriefcase = uCompanyBriefcase.get();
			briefcaseRepository.deleteAll(companyBriefcase.getBriefcases());
			companyBriefcaseRepository.delete(companyBriefcase);
		}
		final Optional<CompanyCredit> uCompanyCredit = companyCreditRepository.findById(companyId);
		if(uCompanyCredit.isPresent()) {
			final CompanyCredit companyCredit = uCompanyCredit.get();
			creditTaxeRepository.deleteAll(companyCredit.getTaxes());
			creditTruckRepository.deleteAll(companyCredit.getTrucks());
			companyCreditRepository.delete(companyCredit);
		}
		final Optional<CompanyLinked> uCompanyLinked = companyLinkedRepository.findById(companyId);
		if(uCompanyLinked.isPresent()) {
			final CompanyLinked companyLinked = uCompanyLinked.get();
			linkedWebsiteRepository.deleteAll(companyLinked.getWebsites());
			companyLinkedRepository.delete(companyLinked);
		}
		final Optional<CompanyShedule> uCompanyShedule = companySheduleRepository.findById(companyId);
		if(uCompanyShedule.isPresent()) {
			final CompanyShedule companyShedule = uCompanyShedule.get();
			daySheduleRepository.deleteAll(companyShedule.getDays());
			companySheduleRepository.delete(companyShedule);
		}
		final List<UUID> annonceIds = annonceRepository.findAllAnnoncesIdsByCompanyId(companyId);
		if(!annonceIds.isEmpty()) {
			annonceDetailRepository.deleteAnnonceDetails(annonceIds);
			annonceActivityRepository.deleteAnnonceActivityByAnnonceIds(annonceIds);
			annonceWilayaRepository.deleteAnnonceWilayaByAnnonceIds(annonceIds);
			annonceRepository.deleteByCompanyId(companyId);
			favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(annonceIds, DocumentType.annonce);
		}
		final List<UUID> campaignIds = campaignRepository.findAllIdByCompanyId(companyId);
		if(!campaignIds.isEmpty()) {
			campaignTargetRepository.deleteCampaignsTarget(campaignIds);
			campaignRepository.deleteByCompanyId(companyId);
		}
		final List<UUID> employeIds = employeRepository.findAllEmployeIdsByCompanyId(companyId);
		if(!employeIds.isEmpty()) {
			employeDetailRepository.deleteEmployeDetails(employeIds);
			employeLocationRepository.deleteEmployeLocationByEmployeIds(employeIds);
			favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(employeIds, DocumentType.employe);
			employeRepository.deleteByCompanyId(companyId);
		}
		final List<UUID> promoteIds = promoteRepository.findAllPromoteIdsByCompanyId(companyId);
		if(!promoteIds.isEmpty()) {
			promoteActivityRepository.deletePromoteActivityByPromoteIds(promoteIds);
			promoteWilayaRepository.deletePromoteWilayaByPromoteIds(promoteIds);
		}
		final List<UUID> eventIds = eventRepository.findAllEventIdsByCompanyId(companyId);
		if(!eventIds.isEmpty()) {
			eventDetailRepository.deleteEventsDetail(eventIds);
			eventCalendarRepository.deleteEventCalendarByEventIds(eventIds);
			eventRepository.deleteByCompanyId(companyId);
			favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(eventIds, DocumentType.event);
		}
		final List<UUID> workIds = workRepository.findAllIdByCompanyId(companyId);
		if(!workIds.isEmpty()) {
			workDetailRepository.deleteWorksDetail(workIds);
			workRepository.deleteByCompanyId(companyId);
		}
		final List<UUID> postIds = postRepository.findAllPostIdsByCompanyId(companyId);
		if(!postIds.isEmpty()) {
			postDetailRepository.deletePostDetails(postIds);
			postSearchRepository.deletePostSearchs(postIds);
			postPhotoRepository.deletePostPhotoByPosts(postIds);
			postRepository.deleteByCompanyId(companyId);
			favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(postIds, DocumentType.post);
		}
		final List<Long> agentIds = agentRepository.findAllIdByCompanyId(companyId);
		if(!agentIds.isEmpty()) {
			agentRepository.deleteByCompanyId(companyId);
		}
		companySeoRepository.deleteById(companyId);
		companyAccountRepository.deleteById(companyId);
		companySearchRepository.deleteById(companyId);
		companyRepository.delete(company);
		filereaderService.deleteCompanyDir(companyId);
		photoService.deleteCompanyDir(companyId);
		imageService.deleteCompanyDir(companyId);
		avatarService.deleteAllAvatarCompany(companyId, agentIds);
		return company;
	}
	
}
