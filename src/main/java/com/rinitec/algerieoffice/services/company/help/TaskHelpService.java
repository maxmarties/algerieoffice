package com.rinitec.algerieoffice.services.company.help;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.AppearanceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.StickyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
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
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyCreditRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLinkedRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanySheduleRepository;
import com.rinitec.algerieoffice.persistence.dao.company.team.AgentRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.overview.TimelineRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardTask;
import com.rinitec.algerieoffice.web.modal.company.help.BegginerTask;

@Service
public class TaskHelpService implements ITaskHelpService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private MainoverviewRepository mainoverviewRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	private CompanySheduleRepository companySheduleRepository;
	private CompanyLocationRepository companyLocationRepository;
	private CompanyLinkedRepository companyLinkedRepository;
	private MaincatalogRepository maincatalogRepository;
	private MainsliderRepository mainsliderRepository;
	private MainaboutRepository mainaboutRepository;
	private MainthinkRepository mainthinkRepository;
	private PostRepository postRepository;
	private CategoryRepository categoryRepository;
	private ActualityRepository actualityRepository;
	private TimelineRepository timelineRepository;
	private AgentRepository agentRepository;
	private PartnerRepository partnerRepository;
	private WorkRepository workRepository;
	private StickyRepository stickyRepository;
	private AnnonceRepository annonceRepository;
	private AppearanceRepository appearanceRepository;
	private MainheaderRepository mainheaderRepository;
	private CompanyCreditRepository companyCreditRepository;
	private PromoteRepository promoteRepository;
	private EmployeRepository employeRepository;
	private CampaignRepository campaignRepository;
	private EventRepository eventRepository;
	private FaqRepository faqRepository;
	private PremiumRepository premiumRepository;
	
	@Autowired
	public TaskHelpService(UserRepository userRepository, CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, MainoverviewRepository mainoverviewRepository, 
			CompanyBriefcaseRepository companyBriefcaseRepository, CompanySheduleRepository companySheduleRepository, CompanyLocationRepository companyLocationRepository, 
			CompanyLinkedRepository companyLinkedRepository, MaincatalogRepository maincatalogRepository, MainsliderRepository mainsliderRepository, 
			MainaboutRepository mainaboutRepository, MainthinkRepository mainthinkRepository, PostRepository postRepository, CategoryRepository categoryRepository, 
			ActualityRepository actualityRepository, TimelineRepository timelineRepository, AgentRepository agentRepository, PartnerRepository partnerRepository, 
			WorkRepository workRepository, StickyRepository stickyRepository, AnnonceRepository annonceRepository, AppearanceRepository appearanceRepository, 
			MainheaderRepository mainheaderRepository, CompanyCreditRepository companyCreditRepository, PromoteRepository promoteRepository, EmployeRepository employeRepository, 
			CampaignRepository campaignRepository, EventRepository eventRepository, FaqRepository faqRepository, PremiumRepository premiumRepository) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.mainoverviewRepository = mainoverviewRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
		this.companySheduleRepository = companySheduleRepository;
		this.companyLocationRepository = companyLocationRepository;
		this.companyLinkedRepository = companyLinkedRepository;
		this.maincatalogRepository = maincatalogRepository;
		this.mainsliderRepository = mainsliderRepository;
		this.mainaboutRepository = mainaboutRepository;
		this.mainthinkRepository = mainthinkRepository;
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
		this.actualityRepository = actualityRepository;
		this.timelineRepository = timelineRepository;
		this.agentRepository = agentRepository;
		this.partnerRepository = partnerRepository;
		this.workRepository = workRepository;
		this.stickyRepository = stickyRepository;
		this.annonceRepository = annonceRepository;
		this.appearanceRepository = appearanceRepository;
		this.mainheaderRepository = mainheaderRepository;
		this.companyCreditRepository = companyCreditRepository;
		this.promoteRepository = promoteRepository;
		this.employeRepository = employeRepository;
		this.campaignRepository = campaignRepository;
		this.eventRepository = eventRepository;
		this.faqRepository = faqRepository;
		this.premiumRepository = premiumRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BegginerTask readBegginerTask(final Long companyId) {
		final boolean[] tasks = new boolean[21];
		final CompanySeo companySeo = companySeoRepository.findById(companyId).get();
		final Optional<CompanyLinked> uLinked = companyLinkedRepository.findById(companyId);
		tasks[0] = companyRepository.findHasAvatarById(companyId).get();
		tasks[1] = mainoverviewRepository.findPresentationByCompanyId(companyId) != null;
		tasks[2] = companyBriefcaseRepository.existsById(companyId);
		tasks[3] = !StringUtils.isEmpty(companySeo.getKeysword());
		tasks[4] = companySheduleRepository.existsById(companyId);
		tasks[5] = companyLocationRepository.existsById(companyId);
		tasks[6] = uLinked.isPresent() && uLinked.get().getWebsites().size() > 0;
		tasks[7] = uLinked.isPresent() && uLinked.get().hasPresentSocialMedia();
		tasks[8] = maincatalogRepository.existsById(companyId);
		tasks[9] = mainsliderRepository.existsById(companyId);
		tasks[10] = mainaboutRepository.existsById(companyId);
		tasks[11] = mainthinkRepository.existsById(companyId);
		tasks[12] = postRepository.countByCompanyId(companyId) > 0L;
		tasks[13] = categoryRepository.countByCompanyId(companyId) > 0L;
		tasks[14] = actualityRepository.countByCompanyId(companyId) > 0L;
		tasks[15] = timelineRepository.countByCompanyId(companyId) > 0L;
		tasks[16] = agentRepository.countByCompanyId(companyId) > 0L;
		tasks[17] = partnerRepository.countByCompanyId(companyId) > 0L;
		tasks[18] = workRepository.countByCompanyId(companyId) > 0L;
		tasks[19] = stickyRepository.existsById(companyId);
		tasks[20] = !StringUtils.isEmpty(companySeo.getUrl());
		return new BegginerTask(tasks);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DashboardTask readDashboardTask(final Long companyId) {
		final boolean[][] tasks = new boolean[2][5];
		final CompanySeo companySeo = companySeoRepository.findById(companyId).get();
		tasks[0][0] = companyRepository.findHasAvatarById(companyId).get();
		tasks[0][1] = mainoverviewRepository.findPresentationByCompanyId(companyId) != null;
		tasks[0][2] = !StringUtils.isEmpty(companySeo.getKeysword());
		tasks[0][3] = timelineRepository.countByCompanyId(companyId) > 0L;
		tasks[0][4] = !StringUtils.isEmpty(companySeo.getUrl());
		tasks[1][0] = postRepository.countByCompanyId(companyId) > 0L;
		tasks[1][1] = userRepository.countByCompanyId(companyId) > 1L;
		tasks[1][2] = annonceRepository.countByCompanyId(companyId) > 0L;
		tasks[1][3] = actualityRepository.countByCompanyId(companyId) > 0L;
		tasks[1][4] = appearanceRepository.existsById(companyId);
		return new DashboardTask(tasks);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countPersentCompleted(final Long companyId) {
		int completed = 0;
		final CompanySeo companySeo = companySeoRepository.findById(companyId).get();
		final Optional<Boolean> uMainheader = mainheaderRepository.findHasCoverById(companyId);
		final Optional<Boolean> uMainoverview = mainoverviewRepository.findHasOverviewByCompanyId(companyId);
		final Optional<String> uHistory = mainoverviewRepository.findHistoryByCompanyId(companyId);
		final Optional<CompanyLinked> uLinked = companyLinkedRepository.findById(companyId);
		final Optional<CompanyShedule> uShedule = companySheduleRepository.findById(companyId);
		final Optional<CompanyCredit> uCredit = companyCreditRepository.findById(companyId);
		final Optional<Integer> uPremium = premiumRepository.findPremiumPassByCompanyId(companyId, new DateTime(Date.from(Instant.now())));
		if(companyRepository.findHasAvatarById(companyId).get()) completed++;
		if(uMainheader.isPresent() && uMainheader.get()) completed++;
		if(mainoverviewRepository.findPresentationByCompanyId(companyId) != null) completed++;
		if(uMainoverview.isPresent() && uMainoverview.get()) completed++;
		if(maincatalogRepository.existsById(companyId)) completed++;
		if(mainsliderRepository.existsById(companyId)) completed++;
		if(timelineRepository.countByCompanyId(companyId) > 0L) completed++;
		if(uHistory.isPresent() && !StringUtils.isEmpty(uHistory.get())) completed++;
		if(mainaboutRepository.existsById(companyId)) completed++;
		if(mainthinkRepository.existsById(companyId)) completed++;
		if(stickyRepository.existsById(companyId)) completed++;
		if(appearanceRepository.existsById(companyId)) completed++;
		if(!StringUtils.isEmpty(companySeo.getUrl())) completed++;
		if(companyBriefcaseRepository.existsById(companyId)) completed++;
		if(uLinked.isPresent()) {
			final CompanyLinked companyLinked = uLinked.get();
			if(companyLinked.getWebsites().size() > 0) completed++;
			if(!StringUtils.isEmpty(companyLinked.getFacebook())) completed++;
			if(!StringUtils.isEmpty(companyLinked.getTwitter())) completed++;
			if(!StringUtils.isEmpty(companyLinked.getLinkedin())) completed++;
			if(!StringUtils.isEmpty(companyLinked.getYoutube())) completed++;
			if(!StringUtils.isEmpty(companyLinked.getInstagram())) completed++;
		}
		if(uShedule.isPresent()) {
			final CompanyShedule companyShedule = uShedule.get();
			if(!StringUtils.isEmpty(companyShedule.getMobile())) completed++;
			if(!StringUtils.isEmpty(companyShedule.getFax())) completed++;
			if(companyShedule.getDays().size() > 0) completed++;
		}
		if(companyLocationRepository.existsById(companyId)) completed++;
		if(!StringUtils.isEmpty(companySeo.getTageline())) completed++;
		if(!StringUtils.isEmpty(companySeo.getKeysword())) {
			final int sizeKeys = companySeo.getKeysword().split(",").length;
			completed += (sizeKeys >= 16 ? 16 : sizeKeys);
		}
		if(uCredit.isPresent()) {
			completed++;
			final CompanyCredit companyCredit = uCredit.get();
			if(companyCredit.getTaxes().size() > 0) completed++;
			if(companyCredit.getTrucks().size() > 0) completed++;
		}
		final long countUsers = userRepository.countByCompanyId(companyId);
		completed += (countUsers >= 3L ? 3 : (int) countUsers);
		final long countAgents = agentRepository.countByCompanyId(companyId);
		completed += (countAgents >= 5L ? 5 : (int) countAgents);
		final long countAgentsEpingled = agentRepository.countByCompanyIdAndHasPingled(companyId, true);
		completed += (countAgentsEpingled >= 3L ? 3 : (int) countAgentsEpingled);
		final long countPosts = postRepository.countByCompanyIdAndHasTrashedAndHasPublished(companyId, false, true);
		completed += (countPosts >= 10L ? 10 : (int) countPosts);
		final long countPromotes = promoteRepository.countByCompanyIdAndHasTrashedAndEnabled(companyId, false, true);
		completed += (countPromotes >= 3L ? 3 : (int) countPromotes);
		final long countAnnonces = annonceRepository.countByCompanyIdAndHasTrashedAndHasPublished(companyId, false, true);
		completed += (countAnnonces >= 3L ? 3 : (int) countAnnonces);
		final long countEmployes = employeRepository.countByCompanyIdAndHasTrashedAndHasPublished(companyId, false, true);
		completed += (countEmployes >= 3L ? 3 : (int) countEmployes);
		final long countCampaigns = campaignRepository.countByCompanyIdAndEnabledAndPublished(companyId, true, true);
		completed += (countCampaigns >= 2L ? 2 : (int) countCampaigns);
		final long countWorks = workRepository.countByCompanyIdAndHasPublished(companyId, true);
		completed += (countWorks >= 3L ? 3 : (int) countWorks);
		final long countActus = actualityRepository.countByCompanyIdAndHasPublished(companyId, true);
		completed += (countActus >= 10L ? 10 : (int) countActus);
		final long countEvents = eventRepository.countByCompanyIdAndHasPublished(companyId, true);
		completed += (countEvents >= 3L ? 3 : (int) countEvents);
		final long countFaqs = faqRepository.countByCompanyIdAndHasPublished(companyId, true);
		completed += (countFaqs >= 10L ? 10 : (int) countFaqs);
		final long countPartners = partnerRepository.countByCompanyId(companyId);
		completed += (countPartners >= 3L ? 3 : (int) countPartners);
		if(uPremium.isPresent()) completed += uPremium.get();
		return (completed * 100) / 109;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllActiveCompanies() {
		return companyRepository.countCompaniesForSector(null, null);
	}
	
}
