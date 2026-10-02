package com.rinitec.algerieoffice.services.admins.marketplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.dao.admins.data.OrderPostalRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignTargetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.OrderPostal;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.CampaignTarget;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmCampaignForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmCampaignLine;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmCampaignOrderLine;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmOrderCampagne;

@Service
public class AdmCampaignService implements IAdmCampaignService {

	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private CampaignRepository campagneRepository;
	private CampaignTargetRepository campaignTargetRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private DocumentOrderRepository documentOrderRepository;
	private OrderPostalRepository orderPostalRepository;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public AdmCampaignService(CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, CampaignRepository campagneRepository, 
			CampaignTargetRepository campaignTargetRepository, PostRepository postRepository, AnnonceRepository annonceRepository, EventRepository eventRepository, 
			DocumentOrderRepository documentOrderRepository, OrderPostalRepository orderPostalRepository, IEnvelopeService envelopeService) {
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.campagneRepository = campagneRepository;
		this.campaignTargetRepository = campaignTargetRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.documentOrderRepository = documentOrderRepository;
		this.orderPostalRepository = orderPostalRepository;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmCampaignsList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = campagneRepository.countAllCampaignAdmin(filter, search);
		final List<AdmCampaignLine> lines = countResult == 0L ? new ArrayList<AdmCampaignLine>() 
				: campagneRepository.findAllCampaignAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional(readOnly = true)
	private final String readCampaignTitle(final Campaign campaign) {
		Optional<String> uOptional;
		switch(campaign.getType()) {
		case post: uOptional = postRepository.findTitleById(campaign.getDocumentId(), campaign.getCompanyId()); break;
		case annonce: uOptional = annonceRepository.findTitleById(campaign.getDocumentId(), campaign.getCompanyId()); break;
		default: uOptional = eventRepository.findTitleById(campaign.getDocumentId(), campaign.getCompanyId());
		}
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	private final AdmCampaignForm parseAdmCampaignForm(final Campaign campaign, final CampaignTarget campaignTarget, final String title) {
		final AdmCampaignForm admCampaignForm = new AdmCampaignForm();
		admCampaignForm.setId(campaign.getId().toString());
		admCampaignForm.setTitle(!StringUtils.isEmpty(title) ? title : "--");
		admCampaignForm.setType(ParseUtil.parseTypeDocument(campaign.getType()));
		admCampaignForm.setSector(campaignTarget.getSector() == null ? 0 : campaignTarget.getSector());
		admCampaignForm.setWilaya(campaignTarget.getWilaya() == null ? 0 : campaignTarget.getWilaya());
		admCampaignForm.setViewCount(campaign.getViewCount());
		admCampaignForm.setClicCount(campaign.getClickCount());
		admCampaignForm.setCreditCount(campaign.getCreditCount());
		admCampaignForm.setEnabled(campaign.isEnabled());
		return admCampaignForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmCampaignForm readAdmCampaignForm(final String id) {
		try {
			final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Campaign campaign = uOptional.get();
				final CampaignTarget campaignTarget = campaignTargetRepository.findById(campaign.getId()).get();
				return parseAdmCampaignForm(campaign, campaignTarget, readCampaignTitle(campaign));
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Campaign updateCampaign(final AdmCampaignForm admCampaignForm) {
		try {
			final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(admCampaignForm.getId()));
			if(uOptional.isPresent()) {
				final Campaign campaign = uOptional.get();
				campaign.setViewCount(admCampaignForm.getViewCount());
				campaign.setClickCount(admCampaignForm.getClicCount());
				campaign.setCreditCount(admCampaignForm.getCreditCount());
				campaign.setEnabled(admCampaignForm.isEnabled());
				return campagneRepository.save(campaign);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Campaign deleteCampaign(final String id) {
		try {
			final Optional<Campaign> uOptional = campagneRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Campaign campaign = uOptional.get();
			documentOrderRepository.deleteByDocumentUUIDAndType(campaign.getId(), OrderType.campaign);
			campagneRepository.delete(campaign);
			return campaign;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteCampaigns(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) campagneRepository.countCampaigns(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			campagneRepository.deleteCampaigns(linesUUID);
			documentOrderRepository.deleteOrderByDocuments(linesUUID, OrderType.campaign);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmCampaignOrdersList(final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = documentOrderRepository.countAllCampaignOrderAdmin(filter, search);
		final List<AdmCampaignOrderLine> lines = countResult == 0L ? new ArrayList<AdmCampaignOrderLine>() 
				: documentOrderRepository.findAllCampaignOrderAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional(readOnly = true)
	private final String readTradename(final Long companyId) {
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final String readCompanyURL(final Long companyId) {
		final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
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
	
	@Override
	@Transactional(readOnly = true)
	public AdmOrderCampagne readAdmOrderCampagne(final String id) {
		try {
			final Optional<DocumentOrder> orderOptional = documentOrderRepository.findById(UUID.fromString(id));
			if(orderOptional.isPresent() && orderOptional.get().getType().equals(OrderType.campaign)) {
				final DocumentOrder documentOrder = orderOptional.get();
				final Optional<Campaign> campaignOtional = campagneRepository.findById(documentOrder.getDocumentUUID());
				if(campaignOtional.isPresent()) {
					final Campaign campaign = campaignOtional.get();
					final CampaignTarget campaignTarget = campaignTargetRepository.findById(campaign.getId()).get();
					final String tradename = readTradename(campaign.getCompanyId());
					final String companyURL = readCompanyURL(campaign.getCompanyId());
					switch(campaign.getType()) {
					case post: return new AdmOrderCampagne(campaign, campaignTarget, documentOrder, tradename, companyURL, readCampaignPost(campaign.getDocumentId()));
					case annonce: return new AdmOrderCampagne(campaign, campaignTarget, documentOrder, tradename, companyURL, readCampaignAnnonce(campaign.getDocumentId()));
					default: return new AdmOrderCampagne(campaign, campaignTarget, documentOrder, tradename, companyURL, readCampaignEvent(campaign.getDocumentId()));
					}
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final DocumentOrder validateDocumentOrder(final DocumentOrder documentOrder, final Long adminId, final boolean response) {
		documentOrder.setConsulted(true);
		documentOrder.setValidated(response);
		documentOrder.setValidateById(adminId);
		return documentOrderRepository.save(documentOrder);
	}
	
	@Transactional
	private final Campaign validateCampaign(final DocumentOrder documentOrder, final AdmOrderForm admOrderForm) {
		final Campaign campaign = campagneRepository.findById(documentOrder.getDocumentUUID()).get();
		campaign.setEnabled(true);
		if(admOrderForm.isResponse()) {
			final int credit = campaign.getCreditCount() + ConstraintesForm.CAMPAIGNS_CREDIT[documentOrder.getPack() - 1];
			campaign.setCreditCount(credit);
		}
		return campagneRepository.save(campaign);
	}
	
	@Transactional
	private final OrderPostal postOrderPostal(final AdmOrderForm admOrderForm, final Long companyId) {
		final OrderPostal orderPostal = new OrderPostal();
		orderPostal.setCompanyId(companyId);
		orderPostal.setAmount(admOrderForm.getAmount());
		orderPostal.setOrderDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(admOrderForm.getOrderDate()));
		orderPostal.setSerial(!StringUtils.isEmpty(admOrderForm.getSerial()) ? admOrderForm.getSerial() : null);
		return orderPostalRepository.save(orderPostal);
	}
	
	@Override
	@Transactional
	public Long validateOrder(final Long adminId, final AdmOrderForm admOrderForm) {
		if(!StringUtils.isEmpty(admOrderForm.getSerial()) && orderPostalRepository.existsBySerial(admOrderForm.getSerial())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(admOrderForm.getId()));
		if(uOptional.isPresent() && uOptional.get().getType().equals(OrderType.campaign)) {
			final DocumentOrder documentOrder = validateDocumentOrder(uOptional.get(), adminId, admOrderForm.isResponse());
			final Campaign campaign = validateCampaign(documentOrder, admOrderForm);
			if(admOrderForm.isResponse()) {
				postOrderPostal(admOrderForm, campaign.getCompanyId());
			}
			return documentOrder.getUserId();
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteOrder(final String id) {
		try {
			final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !uOptional.get().getType().equals(OrderType.campaign)) {
				throw new NotFoundException("message.error.notfound");
			}
			final DocumentOrder documentOrder = uOptional.get();
			envelopeService.deleteEnvelope(documentOrder.getFileUUID());
			documentOrderRepository.delete(documentOrder);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteOrders(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) documentOrderRepository.countDocumentsOrder(linesUUID, OrderType.campaign)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> filesUUID = documentOrderRepository.findAllFileUUIDById(linesUUID, OrderType.campaign);
			documentOrderRepository.deleteDocumentsOrder(linesUUID, OrderType.campaign);
			envelopeService.deleteAll(filesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
