package com.rinitec.algerieoffice.services.explorer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.AppearanceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.MaindisplayRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.PreferencesRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.StickyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainaboutRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MaincatalogRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainheaderRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainoverviewRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainsliderRepository;
import com.rinitec.algerieoffice.persistence.dao.company.overview.MainthinkRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.FaqRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.CategoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.TimelineRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Appearance;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Maindisplay;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Preferences;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Sticky;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainabout;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainheader;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainslider;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainthink;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.Timeline;
import com.rinitec.algerieoffice.persistence.result.ActualityMini;
import com.rinitec.algerieoffice.persistence.result.CategoryMini;
import com.rinitec.algerieoffice.persistence.result.EventMini;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCompany;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCurrent;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerElementsList;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxContact;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxtHome;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPage404;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageAnnonces;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageContact;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageElements;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageHome;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePosts;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePresentation;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageTimeline;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyAppearance;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyCategories;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyFooter;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyHeader;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyMenu;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyMessenger;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyProfile;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyShedule;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetAbout;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetActuality;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetAgent;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetAnnonce;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetBriefcase;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetCatalog;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetEmploye;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetEvent;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetFaq;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetLinked;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetNotice;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetPartner;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetPost;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSlider;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSticky;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetThink;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetTimeline;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetWork;

@Service
public class ExplorerService implements IExplorerService {

	private PremiumRepository premiumRepository;
	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private CompanyAccountRepository companyAccountRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	private CompanySheduleRepository companySheduleRepository;
	private CompanyLocationRepository companyLocationRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private MainheaderRepository mainheaderRepository;
	private MaindisplayRepository maindisplayRepository;
	private MainoverviewRepository mainoverviewRepository;
	private MaincatalogRepository maincatalogRepository;
	private MainsliderRepository mainsliderRepository;
	private MainaboutRepository mainaboutRepository;
	private TimelineRepository timelineRepository;
	private StickyRepository stickyRepository;
	private PostRepository postRepository;
	private CategoryRepository categoryRepository;
	private AgentRepository agentRepository;
	private ActualityRepository actualityRepository;
	private WorkRepository workRepository;
	private EventRepository eventRepository;
	private FaqRepository faqRepository;
	private PartnerRepository partnerRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private AnnonceRepository annonceRepository;
	private EmployeRepository employeRepository;
	private MainthinkRepository mainthinkRepository;
	private AppearanceRepository appearanceRepository;
	private PreferencesRepository preferencesRepository;
	
	@Autowired
	public ExplorerService(PremiumRepository premiumRepository, UserRepository userRepository, CompanyRepository companyRepository, 
			CompanySeoRepository companySeoRepository, CompanyAccountRepository companyAccountRepository, CompanyBriefcaseRepository companyBriefcaseRepository, 
			CompanySheduleRepository companySheduleRepository, CompanyLocationRepository companyLocationRepository, CompanyLinkedRepository companyLinkedRepository, 
			MainheaderRepository mainheaderRepository, MaindisplayRepository maindisplayRepository, MainoverviewRepository mainoverviewRepository, 
			MaincatalogRepository maincatalogRepository, MainsliderRepository mainsliderRepository, MainaboutRepository mainaboutRepository, 
			TimelineRepository timelineRepository, StickyRepository stickyRepository, PostRepository postRepository, CategoryRepository categoryRepository, 
			AgentRepository agentRepository, ActualityRepository actualityRepository, WorkRepository workRepository, EventRepository eventRepository, 
			FaqRepository faqRepository, PartnerRepository partnerRepository, EvaluationRepository evaluationRepository, NoticeRepository noticeRepository, 
			AnnonceRepository annonceRepository, EmployeRepository employeRepository, MainthinkRepository mainthinkRepository, AppearanceRepository appearanceRepository, 
			PreferencesRepository preferencesRepository) {
		this.premiumRepository = premiumRepository;
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
		this.companySheduleRepository = companySheduleRepository;
		this.companyLocationRepository = companyLocationRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.mainheaderRepository = mainheaderRepository;
		this.maindisplayRepository = maindisplayRepository;
		this.mainoverviewRepository = mainoverviewRepository;
		this.maincatalogRepository = maincatalogRepository;
		this.mainsliderRepository = mainsliderRepository;
		this.mainaboutRepository = mainaboutRepository;
		this.timelineRepository = timelineRepository;
		this.stickyRepository = stickyRepository;
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
		this.agentRepository = agentRepository;
		this.actualityRepository = actualityRepository;
		this.workRepository = workRepository;
		this.eventRepository = eventRepository;
		this.faqRepository = faqRepository;
		this.partnerRepository = partnerRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.annonceRepository = annonceRepository;
		this.employeRepository = employeRepository;
		this.mainthinkRepository = mainthinkRepository;
		this.appearanceRepository = appearanceRepository;
		this.preferencesRepository = preferencesRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long findExplorerCompanyId(final String companyURL) {
		try {
			return companyRepository.getCompanyIdExplorer(companyURL);
		} catch (Exception e) {}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final int readPassPremium(final Long companyId) {
		final Optional<Integer> uOptional = premiumRepository.findPremiumPassByCompanyId(companyId, new DateTime(Date.from(Instant.now())));
		return uOptional.isPresent() ? uOptional.get() : 0;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerCurrent readExplorerCurrent(final Long companyId, final String url, final boolean hasPreview) {
		final int premium = readPassPremium(companyId);
		final String companyURL = hasPreview ? ConstraintesURL.URL_PREVIEW : ConstraintesURL.getCompanyExplorerURL(url);
		return new ExplorerCurrent(companyId, companyURL, premium, hasPreview);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerCompanyAppearance readExplorerCompanyAppearance(final Long companyId) {
		final Optional<Appearance> uOptional = appearanceRepository.findById(companyId);
		return new ExplorerCompanyAppearance(uOptional.isPresent() ? uOptional.get(): null);
	}
	
	@Transactional(readOnly = true)
	private final Mainheader readMainheader(final Long companyId) {
		final Optional<Mainheader> uOptional = mainheaderRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final ExplorerCompanyHeader readExplorerCompanyHeader(final Long companyId, final boolean hasLogo) {
		final String urlLogo = hasLogo ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.company 
				: "/static/picts/avatars/company-min.jpg";
		final DateTime modifiedDate = companyAccountRepository.findModifiedDateById(companyId).get();
		final Mainheader mainheader = readMainheader(companyId);
		final long countEvaluation = evaluationRepository.countByCompanyId(companyId);
		Integer evaluation = null;
		Long countLiked = null;
		if(countEvaluation != 0L) {
			evaluation = (int) evaluationRepository.findNoteByCompanyId(companyId);
			countLiked = evaluationRepository.countByCompanyIdAndLiked(companyId, true);
		}
		return new ExplorerCompanyHeader(mainheader, modifiedDate, urlLogo, evaluation, countEvaluation, countLiked);
	}
	
	@Transactional(readOnly = true)
	private final Maindisplay readMaindisplay(final Long companyId) {
		final Optional<Maindisplay> uOptional = maindisplayRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final ExplorerCompanyMenu readExplorerCompanyMenu(final Long companyId) {
		final Maindisplay maindisplay = readMaindisplay(companyId);
		final List<PostMini> categories = categoryRepository.findAllPingledCategoryMini(companyId);
		return new ExplorerCompanyMenu(maindisplay, categories);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerCompanyShedule readExplorerCompanyShedule(final Long companyId) {
		final Optional<CompanyShedule> uOptional = companySheduleRepository.findById(companyId);
		return new ExplorerCompanyShedule(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerCompanyFooter readExplorerCompanyFooter(final Long companyId, final boolean hasActu) {
		final Optional<String> uOptional = companySeoRepository.findTagelineById(companyId);
		final List<ActualityMini> actus = hasActu ? actualityRepository.findLastExplorerActuality(companyId, 2) : null;
		return new ExplorerCompanyFooter(uOptional.isPresent() ? uOptional.get() : null, actus);
	}
	
	@Transactional(readOnly = true)
	private final Long readCompanyAdmin(final Long companyId) {
		final Optional<Long> uOptional = companyAccountRepository.findCreatedById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final ExplorerCompanyMessenger readExplorerCompanyMessenger(final Long companyId) {
		final Optional<Preferences> uOptional = preferencesRepository.findById(companyId);
		final Long messengerId = uOptional.isPresent() ? uOptional.get().getMessengerId() : readCompanyAdmin(companyId);
		try {
			return userRepository.findExplorerCompanyMessenger(messengerId);
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerCompany readExplorerCompany(final ExplorerCurrent explorerCurrent) {
		final Long companyId = explorerCurrent.getCompanyId();
		final Company company = companyRepository.findById(companyId).get();
		final ExplorerCompanyAppearance appearance = explorerCurrent.isHasPreview() || explorerCurrent.hasPremium() 
				? readExplorerCompanyAppearance(companyId) : new ExplorerCompanyAppearance(null);
		final ExplorerCompanyProfile profile = new ExplorerCompanyProfile(company);
		final ExplorerCompanyHeader header = readExplorerCompanyHeader(companyId, company.getHasAvatar());
		final ExplorerCompanyMenu menu = readExplorerCompanyMenu(companyId);
		final ExplorerCompanyShedule shedule = readExplorerCompanyShedule(companyId);
		final ExplorerCompanyFooter footer = readExplorerCompanyFooter(companyId, menu.hasActusFooter());
		final ExplorerCompanyMessenger messenger = !explorerCurrent.isHasPreview() && explorerCurrent.hasPremium() ? readExplorerCompanyMessenger(companyId) : null;
		return new ExplorerCompany(appearance, profile, header, menu, shedule, footer, messenger);
	}
	
	@Transactional(readOnly = true)
	private final boolean readHasOverview(final Long companyId) {
		final Optional<Boolean> uOptional = mainoverviewRepository.findHasOverviewByCompanyId(companyId);
		return uOptional.isPresent() && uOptional.get();
	}
	
	@Transactional(readOnly = true)
	private final ExplorerMeta readExplorerMeta(final Long companyId) {
		final boolean hasOverview = readHasOverview(companyId);
		final CompanySeo companySeo = companySeoRepository.findById(companyId).get();
		return new ExplorerMeta(companySeo, companyId, hasOverview);
	}
	
	@Transactional(readOnly = true)
	private final String readPresentation(final Long companyId) {
		final byte[] presentation = mainoverviewRepository.findPresentationByCompanyId(companyId);
		return presentation != null ? new String(presentation) : null;
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetBriefcase readExplorerWidgetBriefcase(final Long companyId) {
		final Optional<CompanyBriefcase> uOptional = companyBriefcaseRepository.findById(companyId);
		return new ExplorerWidgetBriefcase(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetLinked readExplorerWidgetLinked(final Long companyId) {
		final Optional<CompanyLocation> uLocation = companyLocationRepository.findById(companyId);
		final Optional<CompanyLinked> uLinked = companyLinkedRepository.findById(companyId);
		return new ExplorerWidgetLinked(uLocation.isPresent() ? uLocation.get() : null, uLinked.isPresent() ? uLinked.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetPost> findExplorerWidgetPostList(final ExplorerCurrent explorerCurrent, final String filter, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final List<ExplorerWidgetPost> lines = new ArrayList<ExplorerWidgetPost>();
		final String urlPosts = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_POSTS);
		final String urlCategories = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_CATEGORIES);
		final List<Object[]> results = postRepository.findAllExplorerPostCriteria(explorerCurrent.getCompanyId(), filter, search, sort, rows, page, hasDesc);
		for (final Object[] result : results) {
			lines.add(new ExplorerWidgetPost((Post) result[0], (PostDetail) result[1], (PostPhoto) result[2], (Category) result[3], urlPosts, urlCategories));
		}
		return lines;
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetCatalog readExplorerWidgetCatalog(final Long companyId) {
		final Optional<Maincatalog> uOptional = maincatalogRepository.findById(companyId);
		return new ExplorerWidgetCatalog(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetSlider readExplorerWidgetSlider(final Long companyId) {
		final Optional<Mainslider> uOptional = mainsliderRepository.findById(companyId);
		return new ExplorerWidgetSlider(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final String readHistory(final Long companyId) {
		final Optional<String> uOptional = mainoverviewRepository.findHistoryByCompanyId(companyId);
		return uOptional.isPresent() ? uOptional.get() : "";
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetTimeline readExplorerWidgetTimeline(final Long companyId) {
		final String history = readHistory(companyId);
		final List<Timeline> timesLine = timelineRepository.findAllTimeline(companyId);
		return new ExplorerWidgetTimeline(history, timesLine);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetThink readExplorerWidgetThink(final Long companyId) {
		final Optional<Mainthink> uOptional = mainthinkRepository.findById(companyId);
		return new ExplorerWidgetThink(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerInboxtHome readExplorerInboxtHome(final Long companyId, final String display) {
		final ExplorerWidgetSlider slider = display.charAt(2) == '1' ? readExplorerWidgetSlider(companyId) : null;
		final ExplorerWidgetTimeline timeline = display.charAt(4) == '1' ? readExplorerWidgetTimeline(companyId) : null;
		final ExplorerWidgetThink think = display.charAt(11) == '1' ? readExplorerWidgetThink(companyId) : null;
		return new ExplorerInboxtHome(slider, timeline, think);
	}
	
	@Transactional(readOnly = true)
	private final Long readCountAgent(final Long companyId) {
		return agentRepository.countByCompanyId(companyId);
	}
	
	@Transactional(readOnly = true)
	private final Long readCountNotice(final Long companyId) {
		return noticeRepository.countApprouvedNotice(companyId);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetActuality> findExplorerWidgetActualityList(final Long userId, final Long companyId, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		return actualityRepository.findAllExplorerActualityCriteria(userId, companyId, search, sort, rows, page, hasDesc);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetWork> findExplorerWidgetWorkList(final ExplorerCurrent explorerCurrent, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final List<ExplorerWidgetWork> lines = new ArrayList<ExplorerWidgetWork>();
		final String urlWorks = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_WORKS);
		final List<Object[]> results = workRepository.findAllExplorerWorkCriteria(explorerCurrent.getCompanyId(), search, sort, rows, page, hasDesc);
		for (final Object[] result : results) {
			lines.add(new ExplorerWidgetWork((Work) result[0], (UUID) result[1], urlWorks));
		}
		return lines;
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetPartner> findExplorerWidgetPartnerList(final Long companyId, final boolean hasPingled, 
			final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		return partnerRepository.findAllExplorerPartnerCriteria(companyId, hasPingled, search, sort, rows, page, hasDesc);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageHome readExplorerPageHome(final ExplorerCurrent explorerCurrent, final String display) {
		final Long companyId = explorerCurrent.getCompanyId();
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final String presentation = readPresentation(companyId);
		final ExplorerWidgetBriefcase briefcase = readExplorerWidgetBriefcase(companyId);
		final ExplorerWidgetLinked linked = readExplorerWidgetLinked(companyId);
		final ExplorerInboxtHome inbox = readExplorerInboxtHome(companyId, display);
		final Long countAgent = readCountAgent(companyId);
		final Long countNotice = readCountNotice(companyId);
		final List<ExplorerWidgetPost> posts = display.charAt(3) == '1' ? findExplorerWidgetPostList(explorerCurrent, null, null, 2, 6, 1, true) : null;
		final List<ExplorerWidgetActuality> actus = display.charAt(2) == '1' ? findExplorerWidgetActualityList(null, companyId, null, 1, 3, 1, true) : null;
		final List<ExplorerWidgetWork> works = display.charAt(5) == '1' ? findExplorerWidgetWorkList(explorerCurrent, null, 1, 3, 1, true) : null;
		final List<ExplorerWidgetPartner> partners = findExplorerWidgetPartnerList(companyId, true, null, 2, 10, 1, true);
		return new ExplorerPageHome(meta, presentation, briefcase, linked, inbox, countAgent, countNotice, posts, actus, works, partners);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ExplorerWidgetAgent> findExplorerWidgetAgentList(final Long companyId, final int rows, final int page) {
		final List<ExplorerWidgetAgent> lines = new ArrayList<ExplorerWidgetAgent>();
		final List<Agent> agents = agentRepository.findAllExplorerAgent(companyId, PageRequest.of(page - 1, rows));
		for (final Agent agent : agents) {
			lines.add(new ExplorerWidgetAgent(agent, true));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ExplorerWidgetNotice> findExplorerWidgetNoticeList(final Long companyId, final int rows, final int page) {
		return noticeRepository.findAllApprouvedNoticeCriteria(companyId, rows, page);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetAbout readExplorerWidgetAbout(final Long companyId) {
		final Optional<Mainabout> uOptional = mainaboutRepository.findById(companyId);
		return new ExplorerWidgetAbout(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final ExplorerWidgetSticky readExplorerWidgetSticky(final Long companyId) {
		final Optional<Sticky> uOptional = stickyRepository.findById(companyId);
		return new ExplorerWidgetSticky(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPagePresentation readExplorerPagePresentation(final Long companyId, final String display) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final String presentation = readPresentation(companyId);
		final ExplorerWidgetSticky sticky = display.charAt(8) == '1' ? readExplorerWidgetSticky(companyId) : null;
		final ExplorerWidgetAbout about = display.charAt(9) == '1' ? readExplorerWidgetAbout(companyId) : null;
		final ExplorerWidgetCatalog catalog = display.charAt(10) == '1' ? readExplorerWidgetCatalog(companyId) : null;
		return new ExplorerPagePresentation(meta, presentation, sticky, about, catalog);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageTimeline readExplorerPageTimeline(final Long companyId, final String display) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final ExplorerWidgetTimeline timeline = readExplorerWidgetTimeline(companyId);
		final ExplorerWidgetSticky sticky = display.charAt(8) == '1' ? readExplorerWidgetSticky(companyId) : null;
		return new ExplorerPageTimeline(meta, timeline, sticky);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageElements readExplorerPageElements(final Long companyId, final String display) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final ExplorerWidgetSticky sticky = display.charAt(8) == '1' ? readExplorerWidgetSticky(companyId) : null;
		return new ExplorerPageElements(meta, sticky);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxActus(final Long userId, final ExplorerCurrent explorerCurrent, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final int max = ConstraintesForm.MAX_COUNT_POST[explorerCurrent.getPremium()];
		final Long countActus = actualityRepository.countAllExplorerActualityCriteria(explorerCurrent.getCompanyId(), search);
		final List<ExplorerWidgetActuality> lines = findExplorerWidgetActualityList(userId, explorerCurrent.getCompanyId(), search, sort, 
				explorerCurrent.isHasPreview() || rows <= max ? rows : max, page, hasDesc);
		return new ExplorerElementsList(explorerCurrent.isHasPreview() || countActus <= max ? countActus : max, lines);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetEvent> findExplorerWidgetEventList(final ExplorerCurrent explorerCurrent, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final List<ExplorerWidgetEvent> lines = new ArrayList<ExplorerWidgetEvent>();
		final String urlEvents = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_EVENTS);
		final List<EventMini> events = eventRepository.findAllExplorerEventCriteria(explorerCurrent.getCompanyId(), search, sort, rows, page, hasDesc);
		for (final EventMini event : events) {
			lines.add(new ExplorerWidgetEvent(event, urlEvents));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxEvents(final ExplorerCurrent explorerCurrent, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final int max = ConstraintesForm.MAX_COUNT_POST[explorerCurrent.getPremium()];
		final Long countEvents = eventRepository.countAllExplorerEventCriteria(explorerCurrent.getCompanyId(), search);
		final List<ExplorerWidgetEvent> lines = findExplorerWidgetEventList(explorerCurrent, search, sort, 
				explorerCurrent.isHasPreview() || rows <= max ? rows : max, page, hasDesc);
		return new ExplorerElementsList(explorerCurrent.isHasPreview() || countEvents <= max ? countEvents : max, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxWorks(final ExplorerCurrent explorerCurrent, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final int max = ConstraintesForm.MAX_COUNT_POST[explorerCurrent.getPremium()];
		final Long countWorks = workRepository.countAllExplorerWorkCriteria(explorerCurrent.getCompanyId(), search);
		final List<ExplorerWidgetWork> lines = findExplorerWidgetWorkList(explorerCurrent, search, sort, 
				explorerCurrent.isHasPreview() || rows <= max ? rows : max, page, hasDesc);
		return new ExplorerElementsList(explorerCurrent.isHasPreview() || countWorks <= max ? countWorks : max, lines);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetFaq> findExplorerWidgetFaqList(final Long companyId, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		return faqRepository.findAllExplorerFaqCriteria(companyId, search, sort, rows, page, hasDesc);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxFaqs(final ExplorerCurrent explorerCurrent, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final int max = ConstraintesForm.MAX_COUNT_POST[explorerCurrent.getPremium()];
		final Long countFaqs = faqRepository.countAllExplorerFaqCriteria(explorerCurrent.getCompanyId(), search);
		final List<ExplorerWidgetFaq> lines = findExplorerWidgetFaqList(explorerCurrent.getCompanyId(), search, sort, 
				explorerCurrent.isHasPreview() || rows <= max ? rows : max, page, hasDesc);
		return new ExplorerElementsList(explorerCurrent.isHasPreview() || countFaqs <= max ? countFaqs : max, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxPartners(final ExplorerCurrent explorerCurrent, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final int max = ConstraintesForm.MAX_COUNT_POST[explorerCurrent.getPremium()];
		final Long countPartners = partnerRepository.countAllPartnerCriteria(explorerCurrent.getCompanyId(), null, search);
		final List<ExplorerWidgetPartner> lines = findExplorerWidgetPartnerList(explorerCurrent.getCompanyId(), false, search, sort, 
				explorerCurrent.isHasPreview() || rows <= max ? rows : max, page, hasDesc);
		return new ExplorerElementsList(explorerCurrent.isHasPreview() || countPartners <= max ? countPartners : max, lines);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerCompanyCategories> findExplorerCompanyCategoriesList(final Long companyId) {
		final List<ExplorerCompanyCategories> lines = new ArrayList<ExplorerCompanyCategories>();
		final List<CategoryMini> categories = categoryRepository.findAllExplorerCategory(companyId, null);
		for (final CategoryMini category : categories) {
			lines.add(new ExplorerCompanyCategories(category, categoryRepository.findAllExplorerCategory(companyId, category.getId())));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPagePosts readExplorerPagePosts(final Long companyId, final String display) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final ExplorerWidgetSticky sticky = display.charAt(8) == '1' ? readExplorerWidgetSticky(companyId) : null;
		final List<ExplorerCompanyCategories> lines = findExplorerCompanyCategoriesList(companyId);
		return new ExplorerPagePosts(meta, sticky, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UUIDMini findExplorerCategory(final Long companyId, final String identify) {
		try {
			return categoryRepository.findExplorerCategory(companyId, identify);
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxPosts(final ExplorerCurrent explorerCurrent, final String filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final int max = ConstraintesForm.MAX_COUNT_POST[explorerCurrent.getPremium()];
		final Long countPosts = postRepository.countAllExplorerPostCriteria(explorerCurrent.getCompanyId(), filter, search);
		final List<ExplorerWidgetPost> lines = findExplorerWidgetPostList(explorerCurrent, filter, search, sort, 
				explorerCurrent.isHasPreview() || rows <= max ? rows : max, page, hasDesc);
		return new ExplorerElementsList(explorerCurrent.isHasPreview() || countPosts <= max ? countPosts : max, lines);
	}
	
	@Transactional(readOnly = true)
	private final Long[] readCountsMarketplaceType(final Long companyId) {
		final Long[] countsMarketplace = new Long[6];
		for(int i = 0; i < 5; i++) {
			countsMarketplace[i] = annonceRepository.countExplorerAnnonces(companyId, i + 1);
		}
		countsMarketplace[5] = employeRepository.countExplorerEmployes(companyId);
		return countsMarketplace;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageAnnonces readExplorerPageAnnonces(final Long companyId, final String display) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final ExplorerWidgetSticky sticky = display.charAt(8) == '1' ? readExplorerWidgetSticky(companyId) : null;
		final Long[] countsMarketplace = readCountsMarketplaceType(companyId);
		return new ExplorerPageAnnonces(meta, sticky, countsMarketplace);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetAnnonce> findExplorerWidgetAnnonceList(final ExplorerCurrent explorerCurrent, final Integer filter, 
			final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final List<ExplorerWidgetAnnonce> lines = new ArrayList<ExplorerWidgetAnnonce>();
		final String urlMarketplace = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_MARKETPLACE);
		final List<Object[]> results = annonceRepository.findAllExplorerAnnonceCriteria(explorerCurrent.getCompanyId(), filter, search, sort, rows, page, hasDesc);
		for (final Object[] result : results) {
			lines.add(new ExplorerWidgetAnnonce((Annonce) result[0], (Integer) result[1], urlMarketplace));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxAnnonces(final ExplorerCurrent explorerCurrent, final String type,
			final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		Integer filter = null;
		try {
			filter = StringUtils.isEmpty(type) ? null : Integer.valueOf(type);
		} catch (NumberFormatException e) {}
		final Long countAnnonces = annonceRepository.countAllExplorerAnnonceCriteria(explorerCurrent.getCompanyId(), filter, search);
		final List<ExplorerWidgetAnnonce> lines = findExplorerWidgetAnnonceList(explorerCurrent, filter, search, sort, rows, page, hasDesc);
		return new ExplorerElementsList(countAnnonces, lines);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetEmploye> findExplorerWidgetEmployeList(final ExplorerCurrent explorerCurrent, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final List<ExplorerWidgetEmploye> lines = new ArrayList<ExplorerWidgetEmploye>();
		final String urlEmployes = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_EMPLOYE);
		final List<Object[]> results = employeRepository.findAllExplorerEmployeCriteria(explorerCurrent.getCompanyId(), search, sort, rows, page, hasDesc);
		for (final Object[] result : results) {
			lines.add(new ExplorerWidgetEmploye((Employe) result[0], (Integer) result[1], (Integer) result[2], (String) result[3], urlEmployes));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerElementsList readExplorerInboxEmployes(final ExplorerCurrent explorerCurrent, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final Long countEmployes = employeRepository.countAllExplorerEmployeCriteria(explorerCurrent.getCompanyId(), search);
		final List<ExplorerWidgetEmploye> lines = findExplorerWidgetEmployeList(explorerCurrent, search, sort, rows, page, hasDesc);
		return new ExplorerElementsList(countEmployes, lines);
	}
	
	@Transactional(readOnly = true)
	private final Integer readBriefcaseByCompanyId(final Long companyId) {
		final Optional<Integer> uOptional = companyBriefcaseRepository.findBriefcaseByCompanyId(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final ExplorerInboxContact readExplorerInboxContact(final Long companyId) {
		final Optional<CompanyLocation> uLocation = companyLocationRepository.findById(companyId);
		final Integer briefcase = readBriefcaseByCompanyId(companyId);
		final Optional<CompanyLinked> uLinked = companyLinkedRepository.findById(companyId);
		return new ExplorerInboxContact(uLocation.isPresent() ? uLocation.get() : null, briefcase, uLinked.isPresent() ? uLinked.get() : null);
	}
	
	@Transactional(readOnly = true)
	private final List<ExplorerWidgetAgent> findExplorerContactAgentList(final Long companyId) {
		final List<ExplorerWidgetAgent> lines = new ArrayList<ExplorerWidgetAgent>();
		final List<Agent> agents = agentRepository.findExplorerContactAgent(companyId, PageRequest.of(0, 3));
		for (final Agent agent : agents) {
			lines.add(new ExplorerWidgetAgent(agent, false));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageContact readExplorerPageContact(final Long companyId) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		final ExplorerInboxContact inbox = readExplorerInboxContact(companyId);
		final List<ExplorerWidgetAgent> agents = findExplorerContactAgentList(companyId);
		return new ExplorerPageContact(meta, inbox, agents);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPage404 readExplorerPage404(final Long companyId) {
		final ExplorerMeta meta = readExplorerMeta(companyId);
		return new ExplorerPage404(meta);
	}
	
}
