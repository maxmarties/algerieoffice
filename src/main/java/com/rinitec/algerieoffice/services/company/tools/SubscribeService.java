package com.rinitec.algerieoffice.services.company.tools;

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
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumFormuleRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.company.tools.OrderPremiumForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.tools.PremiumLine;

@Service
public class SubscribeService implements ISubscribeService {

	private PremiumRepository premiumRepository;
	private PremiumFormuleRepository premiumFormuleRepository;
	private DocumentOrderRepository documentOrderRepository;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public SubscribeService(PremiumRepository premiumRepository, PremiumFormuleRepository premiumFormuleRepository, 
			DocumentOrderRepository documentOrderRepository, IEnvelopeService envelopeService) {
		this.premiumRepository = premiumRepository;
		this.premiumFormuleRepository = premiumFormuleRepository;
		this.documentOrderRepository= documentOrderRepository;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPremiumList(final Long companyId, final Integer filter, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final Long countResult = premiumRepository.countAllPremiumCriteria(companyId, filter);
		final List<PremiumLine> lines = countResult == 0L ? new ArrayList<PremiumLine>() 
				: premiumRepository.findAllPremiumCriteria(companyId, filter, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deletePremium(final Long companyId, final String id) {
		try {
			final Optional<Premium> uOptional = premiumRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Premium premium = uOptional.get();
			if(premium.getEnabled()) {
				throw new AccessUploadException("message.error.premium");
			}
			premiumRepository.delete(premium);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deletePremiums(final Long companyId, final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) premiumRepository.countPremiums(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			premiumRepository.deletePremiums(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllPremiums(final Long companyId) {
		premiumRepository.deleteAllPremiums(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public OrderPremiumForm readOrderPremiumForm(final Long userId, final Long companyId) {
		if(documentOrderRepository.existsByUserIdAndType(userId, OrderType.premium)) {
			return null;
		}
		final Optional<PremiumFormule> uOptional = premiumFormuleRepository.findById(ConstraintesForm.PRICEPREMIUM_FORMULE);
		return new OrderPremiumForm(companyId, uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional
	private final DocumentOrder postDocumentOrder(final DocumentOrder documentOrder, final OrderPremiumForm orderForm, final Long userId, final UUID fileUUID) {
		documentOrder.setUserId(userId);
		documentOrder.setPack(orderForm.getPack());
		documentOrder.setMonthly(orderForm.getMonthly());
		documentOrder.setOrderDate(new DateTime(Date.from(Instant.now())));
		documentOrder.setFileUUID(fileUUID);
		return documentOrderRepository.save(documentOrder);
	}
	
	@Override
	@Transactional
	public DocumentOrder updateDocumentOrder(final OrderPremiumForm orderForm, final Long userId) {
		final Envelope envelope = envelopeService.addEnvelope(orderForm.getFile(), EnvelopeType.premium);
		return postDocumentOrder(new DocumentOrder(OrderType.premium), orderForm, userId, envelope.getId());
	}
	
}
