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
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.PromoteActivity;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.PromoteWilaya;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.marketplace.OrderForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.PromoteForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.marketplace.PromoteLine;

@Service
public class PromoteService implements IPromoteService {
	
	private PromoteRepository promoteRepository;
	private PromoteActivityRepository promoteActivityRepository;
	private PromoteWilayaRepository promoteWilayaRepository;
	private DocumentOrderRepository documentOrderRepository;
	private IPhotoService photoService;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public PromoteService(PromoteRepository promoteRepository, PromoteActivityRepository promoteActivityRepository, 
			PromoteWilayaRepository promoteWilayaRepository, DocumentOrderRepository documentOrderRepository, 
			IPhotoService photoService, IEnvelopeService envelopeService) {
		this.promoteRepository = promoteRepository;
		this.promoteActivityRepository = promoteActivityRepository;
		this.promoteWilayaRepository = promoteWilayaRepository;
		this.documentOrderRepository = documentOrderRepository;
		this.photoService = photoService;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countTrashedPromote(final Long companyId) {
		return promoteRepository.countByCompanyIdAndHasTrashed(companyId, true);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean checkOrder(final Long userId, final OrderType type) {
		return documentOrderRepository.existsByUserIdAndTypeAndConsulted(userId, type, false);
	}
	
	private final PromoteForm parsePromoteForm(final Promote promote, final List<Integer> sectors, final List<Integer> wilayas) {
		final PromoteForm promoteForm = new PromoteForm();
		promoteForm.setId(promote.getId().toString());
		promoteForm.setCompanyId(promote.getCompanyId());
		promoteForm.setTitle(promote.getTitle());
		promoteForm.setDescription(promote.getDescription());
		promoteForm.setLabel(promote.getLabel());
		promoteForm.setHasPageonly(promote.getHasPageonly());
		promoteForm.setHasURL(!StringUtils.isEmpty(promote.getUrl()));
		promoteForm.setUrl(promote.getUrl());
		promoteForm.setSectors(sectors);
		promoteForm.setWilayas(wilayas);
		promoteForm.setHasAvatar(promote.getPhotoUUID() != null);
		promoteForm.setUrlAvatar(promote.getPhotoUUID() != null ? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(promote.getPhotoUUID().toString()) 
				: "/static/picts/avatars/promote-min.jpg");
		return promoteForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PromoteForm readPromoteForm(final String id, final Long companyId) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Promote promote = uOptional.get();
				final List<Integer> sectors = promoteActivityRepository.findSectorsByPromoteId(promote.getId());
				final List<Integer> wilayas = promoteWilayaRepository.findWilayasByPromoteId(promote.getId());
				return parsePromoteForm(promote, sectors, wilayas);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Promote postPromote(final Promote promote, final PromoteForm promoteForm, final Long userId, final UUID photoUUID) {
		promote.setTitle(promoteForm.getTitle());
		promote.setDescription(promoteForm.getDescription());
		promote.setLabel(promoteForm.getLabel());
		promote.setUrl(promoteForm.isHasURL() && !StringUtils.isEmpty(promoteForm.getUrl()) ? promoteForm.getUrl() : null);
		promote.setHasPageonly(promoteForm.isHasPageonly());
		promote.setPhotoUUID(photoUUID);
		promote.setAutorId(userId);
		return promoteRepository.save(promote);
	}
	
	@Transactional
	private final void createPromoteActivities(final UUID promoteUUID, final List<Integer> sectors) {
		final List<PromoteActivity> promoteActivities = new ArrayList<PromoteActivity>();
		for (final Integer sector : sectors) {
			final PromoteActivity promoteActivity = new PromoteActivity();
			promoteActivity.setPromoteUUID(promoteUUID);
			promoteActivity.setSector(sector);
			promoteActivities.add(promoteActivity);
		}
		if(!promoteActivities.isEmpty()) {
			promoteActivityRepository.saveAll(promoteActivities);
		}
	}
	
	@Transactional
	private final void createPromoteWilayas(final UUID promoteUUID, final List<Integer> wilayas) {
		if(wilayas.isEmpty()) {
			final PromoteWilaya promoteWilaya = new PromoteWilaya();
			promoteWilaya.setPromoteUUID(promoteUUID);
			promoteWilayaRepository.save(promoteWilaya);
		} else {
			final List<PromoteWilaya> promoteWilayas = new ArrayList<PromoteWilaya>();
			for (final Integer wilaya : wilayas) {
				final PromoteWilaya promoteWilaya = new PromoteWilaya();
				promoteWilaya.setPromoteUUID(promoteUUID);
				promoteWilaya.setWilaya(wilaya);
				promoteWilayas.add(promoteWilaya);
			}
			promoteWilayaRepository.saveAll(promoteWilayas);
		}
	}
	
	@Override
	@Transactional
	public Promote addPromote(final PromoteForm promoteForm, final Long userId) {
		UUID photoUUID = null;
		if(promoteForm.isHasAvatar()) {
			final Photo photo = photoService.addPhoto(promoteForm.getFile(), promoteForm.getCompanyId(), PhotoType.prm);
			photoUUID = photo.getId();
		}
		final Promote promote = postPromote(new Promote(promoteForm.getCompanyId()), promoteForm, userId, photoUUID);
		createPromoteActivities(promote.getId(), promoteForm.getSectors());
		createPromoteWilayas(promote.getId(), promoteForm.getWilayas());
		return promote;
	}
	
	@Transactional
	private final void updatePromoteActivities(final UUID promoteUUID, final List<Integer> sectors) {
		promoteActivityRepository.deleteByPromoteUUID(promoteUUID);
		createPromoteActivities(promoteUUID, sectors);
	}
	
	@Transactional
	private final void updatePromoteWilayas(final UUID promoteUUID, final List<Integer> wilayas) {
		promoteWilayaRepository.deleteByPromoteUUID(promoteUUID);
		createPromoteWilayas(promoteUUID, wilayas);
	}
	
	@Override
	@Transactional
	public Promote updatePromote(final PromoteForm promoteForm, final Long userId) {
		final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(promoteForm.getId()));
		if(uOptional.isPresent() && promoteForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Promote promote = uOptional.get();
			UUID photoUUID = promote.getPhotoUUID();
			if(promoteForm.isHasFileChanged()) {
				if(promoteForm.isHasAvatar()) {
					if(photoUUID == null) {
						final Photo photo = photoService.addPhoto(promoteForm.getFile(), promoteForm.getCompanyId(), PhotoType.prm);
						photoUUID = photo.getId();
					} else {
						photoService.updatePhoto(photoUUID, promoteForm.getFile());
					}
				} else if(photoUUID != null) {
					photoService.deletePhoto(photoUUID);
					photoUUID = null;
				}
			}
			if(promote.getCreditCount() > 0) {
				promote.setEnabled(false);
			}
			postPromote(promote, promoteForm, userId, photoUUID);
			if(promoteForm.isUpdateSectors()) {
				updatePromoteActivities(promote.getId(), promoteForm.getSectors());
			}
			if(promoteForm.isUpdateWilayas()) {
				updatePromoteWilayas(promote.getId(), promoteForm.getWilayas());
			}
			return promote;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllAutorPromote(final Long companyId) {
		return promoteRepository.findAllAutorCriteria(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPromotesList(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = promoteRepository.countAllPromoteCriteria(companyId, filter, search);
		final List<PromoteLine> lines = countResult == 0L ? new ArrayList<PromoteLine>() 
				: promoteRepository.findAllPromoteCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Promote trashPromote(final String id, final Long companyId) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Promote promote = uOptional.get();
			promote.setHasTrashed(true);
			return promoteRepository.save(promote);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashPromotes(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) promoteRepository.countPromotes(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			promoteRepository.trashPromotes(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashAllPromotes(final Long companyId) {
		promoteRepository.trashAllPromotes(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public OrderForm readOrderForm(final String id, final Long companyId) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				return new OrderForm(id, companyId, false);
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
		final Envelope envelope = envelopeService.addEnvelope(orderForm.getFile(), EnvelopeType.order);
		return postDocumentOrder(new DocumentOrder(OrderType.promote), orderForm, userId, envelope.getId());
	}
	
}
