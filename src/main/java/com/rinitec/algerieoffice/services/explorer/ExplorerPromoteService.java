package com.rinitec.algerieoffice.services.explorer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.admins.ads.SponsoreRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.feedback.CampaignFeedback;
import com.rinitec.algerieoffice.web.modal.feedback.PromoteFeedback;
import com.rinitec.algerieoffice.web.modal.feedback.SponsoreFeedback;

@Service
public class ExplorerPromoteService implements IExplorerPromoteService {

	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private PromoteRepository promoteRepository;
	private SponsoreRepository sponsoreRepository;
	private CampaignRepository campaignRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	
	@Autowired
	public ExplorerPromoteService(CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, PromoteRepository promoteRepository, 
			SponsoreRepository sponsoreRepository, CampaignRepository campaignRepository, PostRepository postRepository, 
			AnnonceRepository annonceRepository, EventRepository eventRepository) {
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.promoteRepository = promoteRepository;
		this.sponsoreRepository = sponsoreRepository;
		this.campaignRepository = campaignRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
	}
	
	@Transactional
	private final Promote findPromoteAndUpdateView(final Long companyId) {
		try {
			final Promote promote = companyId != null ? promoteRepository.findPromoteExplorer(companyId) : promoteRepository.findPromoteHome();
			if(promote != null) {
				promote.setViewCount(promote.getViewCount() + 1);
				return promoteRepository.save(promote);
			}
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional
	public PromoteFeedback readPromoteFeedback(final Long companyId) {
		final Promote promote = findPromoteAndUpdateView(companyId);
		if(promote != null) {
			final String companyURL = StringUtils.isEmpty(promote.getUrl()) ? companySeoRepository.findUrlById(promote.getCompanyId()).get() : null;
			return new PromoteFeedback(promote, companyURL);
		}
		return null;
	}
	
	@Override
	@Transactional
	public PromoteFeedback readPromoteFeedbackHome() {
		final Promote promote = findPromoteAndUpdateView(null);
		if(promote != null) {
			final String companyURL = StringUtils.isEmpty(promote.getUrl()) ? companySeoRepository.findUrlById(promote.getCompanyId()).get() : null;
			return new PromoteFeedback(promote, companyURL);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void incrementClickPromote(final String id) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Promote promote = uOptional.get();
				promote.setClickCount(promote.getClickCount() + 1);
				promoteRepository.save(promote);
			}
		} catch (IllegalArgumentException e) {}
	}
	
	@Transactional
	private final Sponsore findSponsoreAndUpdateView(final Integer type) {
		try {
			final Sponsore sponsore = sponsoreRepository.findSponsoreExplorer(type);
			if(sponsore != null) {
				sponsore.setViewCount(sponsore.getViewCount() + 1);
				return sponsoreRepository.save(sponsore);
			}
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional
	public SponsoreFeedback readSponsoreFeedback(final Integer type) {
		final Sponsore sponsore = findSponsoreAndUpdateView(type);
		if(sponsore != null) {
			return new SponsoreFeedback(sponsore);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void incrementClickSponsore(final String id) {
		try {
			final Optional<Sponsore> uOptional = sponsoreRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Sponsore sponsore = uOptional.get();
				sponsore.setClickCount(sponsore.getClickCount() + 1);
				sponsoreRepository.save(sponsore);
			}
		} catch (IllegalArgumentException e) {}
	}
	
	@Transactional
	private final Campaign findCampaignAndUpdateView(final Long companyId) {
		try {
			final Campaign campaign = campaignRepository.findCampaignDashboard(companyId);
			if(campaign != null) {
				campaign.setViewCount(campaign.getViewCount() + 1);
				return campaignRepository.save(campaign);
			}
		} catch (Exception e) {}
		return null;
	}
	
	@Transactional
	private final String readCompanyAvatarURL(final Long companyId) {
		final Optional<Boolean> uOptional = companyRepository.findHasAvatarById(companyId);
		return uOptional.isPresent() && uOptional.get() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.company 
				: "/static/picts/avatars/company-min.jpg";
	}
	
	@Override
	@Transactional
	public CampaignFeedback readCampaignFeedback(final Long companyId) {
		final Campaign campaign = findCampaignAndUpdateView(companyId);
		if(campaign != null) {
			Optional<?> uOptional;
			final String urlAvatar = readCompanyAvatarURL(campaign.getCompanyId());
			final String url = companySeoRepository.findUrlById(campaign.getCompanyId()).get();
			switch(campaign.getType()) {
			case post: 
				uOptional = postRepository.findById(campaign.getDocumentId());
				return uOptional.isPresent() ? new CampaignFeedback(campaign, urlAvatar, url, (Post) uOptional.get()) : null;
			case annonce: 
				uOptional = annonceRepository.findById(campaign.getDocumentId());
				return uOptional.isPresent() ? new CampaignFeedback(campaign, urlAvatar, url, (Annonce) uOptional.get()) : null;
			default: 
				uOptional = eventRepository.findById(campaign.getDocumentId());
				return uOptional.isPresent() ? new CampaignFeedback(campaign, urlAvatar, url, (Event) uOptional.get()) : null;
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public void incrementClickCampaign(final String id) {
		try {
			final Optional<Campaign> uOptional = campaignRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Campaign campaign = uOptional.get();
				campaign.setClickCount(campaign.getClickCount() + 1);
				campaignRepository.save(campaign);
			}
		} catch (IllegalArgumentException e) {}
	}
	
}
