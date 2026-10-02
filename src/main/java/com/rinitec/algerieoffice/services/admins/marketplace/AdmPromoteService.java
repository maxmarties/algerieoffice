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
import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.admins.data.OrderPostalRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.OrderPostal;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmPromoteForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmOrderPromote;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmPromoteLine;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmPromoteOrderLine;

@Service
public class AdmPromoteService implements IAdmPromoteService {

	private PromoteRepository promoteRepository;
	private PromoteActivityRepository promoteActivityRepository;
	private PromoteWilayaRepository promoteWilayaRepository;
	private DocumentOrderRepository documentOrderRepository;
	private OrderPostalRepository orderPostalRepository;
	private IPhotoService photoService;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public AdmPromoteService(PromoteRepository promoteRepository, PromoteActivityRepository promoteActivityRepository, 
			PromoteWilayaRepository promoteWilayaRepository, DocumentOrderRepository documentOrderRepository, 
			OrderPostalRepository orderPostalRepository, IPhotoService photoService, IEnvelopeService envelopeService) {
		this.promoteRepository = promoteRepository;
		this.promoteActivityRepository = promoteActivityRepository;
		this.promoteWilayaRepository = promoteWilayaRepository;
		this.documentOrderRepository = documentOrderRepository;
		this.orderPostalRepository = orderPostalRepository;
		this.photoService = photoService;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmPromotesList(final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = promoteRepository.countAllPromoteAdmin(filter, search);
		final List<AdmPromoteLine> lines = countResult == 0L ? new ArrayList<AdmPromoteLine>() 
				: promoteRepository.findAllPromoteAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	private final AdmPromoteForm parseAdmPromoteForm(final Promote promote, final List<Integer> sectors, final List<Integer> wilayas) {
		final AdmPromoteForm admPromoteForm = new AdmPromoteForm();
		admPromoteForm.setId(promote.getId().toString());
		admPromoteForm.setTitle(promote.getTitle());
		admPromoteForm.setDescription(promote.getDescription());
		admPromoteForm.setHasURL(!StringUtils.isEmpty(promote.getUrl()));
		admPromoteForm.setUrl(promote.getUrl());
		admPromoteForm.setEnabled(promote.isEnabled());
		admPromoteForm.setViewCount(promote.getViewCount());
		admPromoteForm.setCreditCount(promote.getCreditCount());
		admPromoteForm.setHasAvatar(promote.getPhotoUUID() != null);
		admPromoteForm.setUrlAvatar(promote.getPhotoUUID() != null ? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(promote.getPhotoUUID().toString()) 
				: "/static/picts/avatars/promote-min.jpg");
		admPromoteForm.setSectors(sectors);
		admPromoteForm.setWilayas(wilayas);
		return admPromoteForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmPromoteForm readAdmPromoteForm(final String id) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Promote promote = uOptional.get();
				final List<Integer> sectors = promoteActivityRepository.findSectorsByPromoteId(promote.getId());
				final List<Integer> wilayas = promoteWilayaRepository.findWilayasByPromoteId(promote.getId());
				return parseAdmPromoteForm(promote, sectors, wilayas);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Promote moderatePromote(final AdmPromoteForm admPromoteForm) {
		final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(admPromoteForm.getId()));
		if(uOptional.isPresent()) {
			final Promote promote = uOptional.get();
			UUID photoUUID = promote.getPhotoUUID();
			if(admPromoteForm.isHasFileChanged()) {
				if(admPromoteForm.isHasAvatar()) {
					if(photoUUID == null) {
						final Photo photo = photoService.addPhoto(admPromoteForm.getFile(), promote.getCompanyId(), PhotoType.prm);
						photoUUID = photo.getId();
					} else {
						photoService.updatePhoto(photoUUID, admPromoteForm.getFile());
					}
				} else if(photoUUID != null) {
					photoService.deletePhoto(photoUUID);
					photoUUID = null;
				}
				promote.setPhotoUUID(photoUUID);
			}
			promote.setTitle(admPromoteForm.getTitle());
			promote.setDescription(admPromoteForm.getDescription());
			promote.setUrl(admPromoteForm.isHasURL() && !StringUtils.isEmpty(admPromoteForm.getUrl()) ? admPromoteForm.getUrl() : null);
			promote.setViewCount(admPromoteForm.getViewCount());
			promote.setCreditCount(admPromoteForm.getCreditCount());
			promote.setEnabled(admPromoteForm.isEnabled());
			return promoteRepository.save(promote);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Promote deletePromote(final String id) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Promote promote = uOptional.get();
			if(promote.getPhotoUUID() != null) {
				photoService.deletePhoto(promote.getPhotoUUID());
			}
			documentOrderRepository.deleteByDocumentUUIDAndType(promote.getId(), OrderType.promote);
			promoteRepository.delete(promote);
			return promote;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deletePromotes(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) promoteRepository.countPromotes(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> photosUUID = promoteRepository.findAllPhotoUUIDById(linesUUID);
			promoteRepository.deletePromotes(linesUUID);
			documentOrderRepository.deleteOrderByDocuments(linesUUID, OrderType.promote);
			photoService.deleteAllPhotos(photosUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmPromoteOrdersList(final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = documentOrderRepository.countAllPromoteOrderAdmin(filter, search);
		final List<AdmPromoteOrderLine> lines = countResult == 0L ? new ArrayList<AdmPromoteOrderLine>() 
				: documentOrderRepository.findAllPromoteOrderAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmOrderPromote readAdmOrderPromote(final String id) {
		try {
			final Optional<DocumentOrder> orderOptional = documentOrderRepository.findById(UUID.fromString(id));
			if(orderOptional.isPresent() && orderOptional.get().getType().equals(OrderType.promote)) {
				final DocumentOrder documentOrder = orderOptional.get();
				final Optional<Promote> promoteOtional = promoteRepository.findById(documentOrder.getDocumentUUID());
				if(promoteOtional.isPresent()) {
					final Promote promote = promoteOtional.get();
					return new AdmOrderPromote(promote, documentOrder);
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
	private final Promote validatePromote(final DocumentOrder documentOrder, final AdmOrderForm admOrderForm) {
		final Promote promote = promoteRepository.findById(documentOrder.getDocumentUUID()).get();
		promote.setEnabled(true);
		if(admOrderForm.isResponse()) {
			final int credit = promote.getCreditCount() + ConstraintesForm.ORDERS_CREDIT[documentOrder.getPack() - 1];
			promote.setCreditCount(credit);
		}
		return promoteRepository.save(promote);
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
	public Object[] validateOrder(final Long adminId, final AdmOrderForm admOrderForm) {
		if(!StringUtils.isEmpty(admOrderForm.getSerial()) && orderPostalRepository.existsBySerial(admOrderForm.getSerial())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Object[] result = new Object[2];
		final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(admOrderForm.getId()));
		if(uOptional.isPresent() && uOptional.get().getType().equals(OrderType.promote)) {
			final DocumentOrder documentOrder = validateDocumentOrder(uOptional.get(), adminId, admOrderForm.isResponse());
			final Promote promote = validatePromote(documentOrder, admOrderForm);
			if(admOrderForm.isResponse()) {
				postOrderPostal(admOrderForm, promote.getCompanyId());
			}
			result[0] = documentOrder.getUserId();
			result[1] = promote.getTitle();
			return result;
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteOrder(final String id) {
		try {
			final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !uOptional.get().getType().equals(OrderType.promote)) {
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
			if(linesUUID.isEmpty() || linesUUID.size() != (int) documentOrderRepository.countDocumentsOrder(linesUUID, OrderType.promote)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> filesUUID = documentOrderRepository.findAllFileUUIDById(linesUUID, OrderType.promote);
			documentOrderRepository.deleteDocumentsOrder(linesUUID, OrderType.promote);
			envelopeService.deleteAll(filesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
