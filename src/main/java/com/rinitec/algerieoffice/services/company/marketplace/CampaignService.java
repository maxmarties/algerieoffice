package com.rinitec.algerieoffice.services.company.marketplace;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignTargetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.CampaignTarget;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.marketplace.CampaignForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.OrderForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.marketplace.CampaignLine;

@Service
public class CampaignService implements ICampaignService {

	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private CampaignRepository campagneRepository;
	private CampaignTargetRepository campaignTargetRepository;
	private DocumentOrderRepository documentOrderRepository;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public CampaignService(PostRepository postRepository, AnnonceRepository annonceRepository, 
			EventRepository eventRepository, CampaignRepository campagneRepository, 
			CampaignTargetRepository campaignTargetRepository, DocumentOrderRepository documentOrderRepository, 
			IEnvelopeService envelopeService) {
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.campagneRepository = campagneRepository;
		this.campaignTargetRepository = campaignTargetRepository;
		this.documentOrderRepository = documentOrderRepository;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllPostCampaignMini(final Long companyId) {
		return postRepository.findAllPostCampaignMini(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllAnnonceCampaignMini(final Long companyId) {
		return annonceRepository.findAllAnnonceCampaignMini(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllEventCampaignMini(final Long companyId) {
		return eventRepository.findAllEventCampaignMini(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String readCampaignTitle(final Campaign campaign) {
		Optional<String> uOptional;
		switch(campaign.getType()) {
		case post: uOptional = postRepository.findTitleById(campaign.getDocumentId(), campaign.getCompanyId()); break;
		case annonce: uOptional = annonceRepository.findTitleById(campaign.getDocumentId(), campaign.getCompanyId()); break;
		default: uOptional = eventRepository.findTitleById(campaign.getDocumentId(), campaign.getCompanyId());
		}
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	private final CampaignForm parseCampaignForm(final Campaign campaign, final CampaignTarget campaignTarget, final String title) {
		final CampaignForm campaignForm = new CampaignForm();
		campaignForm.setId(campaign.getId().toString());
		campaignForm.setCompanyId(campaign.getCompanyId());
		campaignForm.setDocumentId(campaign.getDocumentId().toString());
		campaignForm.setDocumentTitle(title);
		campaignForm.setType(ParseUtil.parseTypeDocument(campaign.getType()));
		campaignForm.setSector(campaignTarget.getSector() == null ? 32 : campaignTarget.getSector());
		campaignForm.setWilaya(campaignTarget.getWilaya() == null ? 49 : campaignTarget.getWilaya());
		return campaignForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CampaignForm readCampaignForm(final String id, final Long companyId) {
		try {
			final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Campaign campaign = uOptional.get();
				final CampaignTarget campaignTarget = campaignTargetRepository.findById(campaign.getId()).get();
				final String title = readCampaignTitle(campaign);
				if(title != null) {
					return parseCampaignForm(campaign, campaignTarget, title);
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Campaign postCampaign(final Campaign campaign, final CampaignForm campaignForm, final Long userId, final boolean hasNew) {
		if(hasNew) {
			campaign.setDocumentId(UUID.fromString(campaignForm.getDocumentId()));
			campaign.setType(ParseUtil.parseDocumentType(campaignForm.getType()));
		}
		campaign.setAutorId(userId);
		return campagneRepository.save(campaign);
	}
	
	@Transactional
	private final CampaignTarget postCampaignTarget(final CampaignTarget campaignTarget, final CampaignForm campaignForm) {
		campaignTarget.setSector(campaignForm.getSector() == 32 ? null : campaignForm.getSector());
		campaignTarget.setWilaya(campaignForm.getWilaya() == 49 ? null : campaignForm.getWilaya());
		return campaignTargetRepository.save(campaignTarget);
	}
	
	@Override
	@Transactional
	public Campaign addCampaign(final CampaignForm campaignForm, final Long userId) {
		final Campaign campaign = postCampaign(new Campaign(campaignForm.getCompanyId()), campaignForm, userId, true);
		postCampaignTarget(new CampaignTarget(campaign), campaignForm);
		return campaign;
	}
	
	@Override
	@Transactional
	public Campaign updateCampaign(final CampaignForm campaignForm, final Long userId) {
		final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(campaignForm.getId()));
		if(uOptional.isPresent() && campaignForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Campaign campaign = uOptional.get();
			final CampaignTarget campaignTarget = campaignTargetRepository.findById(campaign.getId()).get();
			postCampaign(campaign, campaignForm, userId, false);
			postCampaignTarget(campaignTarget, campaignForm);
			return campaign;
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final Post readCampaignPost(final UUID postId) {
		final Optional<Post> uOptional = postRepository.findById(postId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final Annonce readCampaignAnnonce(final UUID annonceId) {
		final Optional<Annonce> uOptional = annonceRepository.findById(annonceId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final Event readCampaignEvent(final UUID eventId) {
		final Optional<Event> uOptional = eventRepository.findById(eventId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final CampaignLine parseCampaignLine(final Campaign campaign, final String autor, final Long orderCount) {
		switch(campaign.getType()) {
		case post: return new CampaignLine(campaign, autor, orderCount, readCampaignPost(campaign.getDocumentId()));
		case annonce: return new CampaignLine(campaign, autor, orderCount, readCampaignAnnonce(campaign.getDocumentId()));
		default: return new CampaignLine(campaign, autor, orderCount, readCampaignEvent(campaign.getDocumentId()));
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPromotesList(final Long companyId, final Integer filter, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final List<CampaignLine> lines = new ArrayList<CampaignLine>();
		final Long countResult = campagneRepository.countAllCampaignCriteria(companyId, filter);
		if(countResult != 0L) {
			final List<Object[]> results = campagneRepository.findAllCampaignCriteria(companyId, filter, sort, rows, page, hasDesc);
			for (final Object[] result : results) {
				lines.add(parseCampaignLine((Campaign) result[0], (String) result[1], (Long) result[2]));
			}
		}
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public String deleteCampaign(final String id, final Long companyId) {
		try {
			final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Campaign campaign = uOptional.get();
			final String title = readCampaignTitle(campaign);
			campaignTargetRepository.deleteById(campaign.getId());
			campagneRepository.delete(campaign);
			return title;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteCampaigns(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) campagneRepository.countCampaigns(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			campaignTargetRepository.deleteCampaignsTarget(linesUUID);
			campagneRepository.deleteCampaigns(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllCampaigns(final Long companyId) {
		final List<UUID> linesUUID = campagneRepository.findAllIdByCompanyId(companyId);
		if(!linesUUID.isEmpty()) {
			campaignTargetRepository.deleteCampaignsTarget(linesUUID);
		}
		campagneRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public OrderForm readOrderForm(final String id, final Long companyId) {
		try {
			final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				return new OrderForm(id, companyId, true);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final DocumentOrder postDocumentOrder(final DocumentOrder documentOrder, final OrderForm orderForm, final Long userId, final UUID fileUUID) {
		documentOrder.setDocumentUUID(UUID.fromString(orderForm.getId()));
		documentOrder.setUserId(userId);
		documentOrder.setPack(orderForm.getPack());
		documentOrder.setOrderDate(new DateTime(Date.from(Instant.now())));
		documentOrder.setFileUUID(fileUUID);
		return documentOrderRepository.save(documentOrder);
	}
	
	@Override
	@Transactional
	public DocumentOrder updateDocumentOrder(final OrderForm orderForm, final Long userId) {
		final Envelope envelope = envelopeService.addEnvelope(orderForm.getFile(), EnvelopeType.campaign);
		return postDocumentOrder(new DocumentOrder(OrderType.campaign), orderForm, userId, envelope.getId());
	}
	
}
