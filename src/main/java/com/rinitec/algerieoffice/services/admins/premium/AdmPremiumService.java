package com.rinitec.algerieoffice.services.admins.premium;

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
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.newsletter.BudgetRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.DocumentOrderRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.form.admins.premium.AdmBudgetForm;
import com.rinitec.algerieoffice.web.form.admins.premium.PremiumForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmBudgetLine;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumLine;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumOrderLine;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterBudget;

@Service
public class AdmPremiumService implements IAdmPremiumService {

	private PremiumRepository premiumRepository;
	private CompanyRepository companyRepository;
	private DocumentOrderRepository documentOrderRepository;
	private BudgetRepository budgetRepository;
	private OrderPostalRepository orderPostalRepository;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public AdmPremiumService(PremiumRepository premiumRepository, CompanyRepository companyRepository, DocumentOrderRepository documentOrderRepository, 
			BudgetRepository budgetRepository, OrderPostalRepository orderPostalRepository, IEnvelopeService envelopeService) {
		this.premiumRepository = premiumRepository;
		this.companyRepository = companyRepository;
		this.documentOrderRepository = documentOrderRepository;
		this.budgetRepository = budgetRepository;
		this.orderPostalRepository = orderPostalRepository;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmPremiumList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = premiumRepository.countAllPremiumAdmin(filter, search);
		final List<AdmPremiumLine> lines = countResult == 0L ? new ArrayList<AdmPremiumLine>() 
				: premiumRepository.findAllPremiumAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PremiumForm readPremiumForm(final String id) {
		try {
			final Optional<Premium> uOptional = premiumRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Premium premium = uOptional.get();
				return new PremiumForm(premium);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Premium updatePremium(final PremiumForm premiumForm) {
		final Optional<Premium> uOptional = premiumRepository.findById(UUID.fromString(premiumForm.getPremiumId()));
		if(uOptional.isPresent()) {
			final Premium premium = uOptional.get();
			if(premiumForm.isEnabled()) {
				premiumRepository.disabledAllByCompanyId(premiumForm.getCompanyId());
			}
			premium.setPass(premiumForm.getPass());
			premium.setCreateDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(premiumForm.getCreateDate()));
			premium.setExpiryDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(premiumForm.getExpiryDate()));
			premium.setEnabled(premiumForm.isEnabled());
			return premiumRepository.save(premium);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deletePremium(final String id) {
		try {
			final Optional<Premium> uOptional = premiumRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Premium premium = uOptional.get();
			premiumRepository.delete(premium);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deletePremiums(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) premiumRepository.countPremiums(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			premiumRepository.deleteAdminPremiums(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmPremiumOrderList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc, final OrderType type) {
		final Long countResult = documentOrderRepository.countAllPremiumOrderAdmin(filter, search, type);
		final List<AdmPremiumOrderLine> lines = countResult == 0L ? new ArrayList<AdmPremiumOrderLine>() 
				: documentOrderRepository.findAllPremiumOrderAdmin(filter, search, sort, rows, page, hasDesc, type);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public AdmPremiumOrderLine readAdmPremiumOrderLine(final String id, final OrderType type) {
		try {
			final UUID orderId = UUID.fromString(id);
			final AdmPremiumOrderLine admPremiumOrderLine = documentOrderRepository.findOneAdmPremiumOrderLine(orderId, type);
			documentOrderRepository.updateConsultedById(orderId);
			return admPremiumOrderLine;
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdmPremiumLine> findAdmPremiumCompanyList(final Long companyId) {
		return premiumRepository.findPremiumAdminCompany(companyId);
	}
	
	@Transactional
	private final Premium addPremium(final PremiumForm premiumForm) {
		final Premium premium = new Premium();
		premium.setCompanyId(premiumForm.getCompanyId());
		premium.setPass(premiumForm.getPass());
		premium.setCreateDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(premiumForm.getCreateDate()));
		premium.setExpiryDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(premiumForm.getExpiryDate()));
		premium.setEnabled(true);
		return premiumRepository.save(premium);
	}
	
	@Transactional
	private final void deleteDocumentOrder(final DocumentOrder documentOrder) {
		envelopeService.deleteEnvelope(documentOrder.getFileUUID());
		documentOrderRepository.delete(documentOrder);
	}
	
	@Override
	@Transactional
	public Long validateOrder(final PremiumForm premiumForm) {
		final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(premiumForm.getPremiumId()));
		if(uOptional.isPresent() && uOptional.get().getType().equals(OrderType.premium)) {
			final DocumentOrder documentOrder = uOptional.get();
			if(premiumForm.isEnabled()) {
				premiumRepository.disabledAllByCompanyId(premiumForm.getCompanyId());
				addPremium(premiumForm);
			}
			deleteDocumentOrder(documentOrder);
			return documentOrder.getUserId();
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteOrder(final String id, final OrderType type) {
		try {
			final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !uOptional.get().getType().equals(type)) {
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
	public void deleteOrders(final List<String> lines, final OrderType type) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) documentOrderRepository.countDocumentsOrder(linesUUID, type)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> filesUUID = documentOrderRepository.findAllFileUUIDById(linesUUID, type);
			documentOrderRepository.deleteDocumentsOrder(linesUUID, type);
			envelopeService.deleteAll(filesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public NewsletterBudget readNewsletterBudget(final Long companyId) {
		final Optional<Budget> uOptional = budgetRepository.findById(companyId);
		return new NewsletterBudget(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Transactional
	private final Budget updateNewsletterBudget(final Long companyId, final int pack) {
		final Optional<Budget> uOptional = budgetRepository.findById(companyId);
		final Budget budget = uOptional.isPresent() ? uOptional.get() : new Budget(companyId);
		budget.incrementEmails(ConstraintesForm.BUDGET_EMAILS[pack - 1]);
		budget.incrementSendings(ConstraintesForm.BUDGET_SENDING[pack - 1]);
		return budgetRepository.save(budget);
	}
	
	@Override
	@Transactional
	public Object[] validateEmailing(final AdmOrderForm admOrderForm) {
		if(!StringUtils.isEmpty(admOrderForm.getSerial()) && orderPostalRepository.existsBySerial(admOrderForm.getSerial())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Object[] result = new Object[2];
		final Optional<DocumentOrder> uOptional = documentOrderRepository.findById(UUID.fromString(admOrderForm.getId()));
		if(uOptional.isPresent() && uOptional.get().getType().equals(OrderType.emailing)) {
			final DocumentOrder documentOrder = uOptional.get();
			if(admOrderForm.isResponse()) {
				updateNewsletterBudget(admOrderForm.getCompanyId(), documentOrder.getPack());
			}
			deleteDocumentOrder(documentOrder);
			result[0] = documentOrder.getUserId();
			result[1] = companyRepository.findTradenameById(admOrderForm.getCompanyId()).get();
			return result;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmBudgetList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = budgetRepository.countAllBudgetAdmin(search);
		final List<AdmBudgetLine> lines = countResult == 0L ? new ArrayList<AdmBudgetLine>() 
				: budgetRepository.findAllBudgetAdmin(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmBudgetForm readAdmBudgetForm(final Long id) {
		final Optional<Budget> uOptional = budgetRepository.findById(id);
		if(uOptional.isPresent()) {
			return new AdmBudgetForm(uOptional.get());
		}
		return null;
	}
	
	@Transactional
	private final Budget postOrUpdateBudget(final Budget budget, final AdmBudgetForm admBudgetForm) {
		budget.setEmails(admBudgetForm.getEmails().longValue());
		budget.setSendings(admBudgetForm.getSendings());
		return budgetRepository.save(budget);
	}
	
	@Override
	@Transactional
	public String updateBudget(final AdmBudgetForm admBudgetForm) {
		final Optional<Budget> uOptional = budgetRepository.findById(admBudgetForm.getCompanyId());
		if(uOptional.isPresent()) {
			postOrUpdateBudget(uOptional.get(), admBudgetForm);
			return companyRepository.findTradenameById(admBudgetForm.getCompanyId()).get();
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteBudget(final Long id) {
		final Optional<Budget> uOptional = budgetRepository.findById(id);
		if(!uOptional.isPresent()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Budget budget = uOptional.get();
		budgetRepository.delete(budget);
	}
	
	@Override
	@Transactional
	public void deleteBudgets(final List<Long> lines) {
		if(lines.isEmpty() || lines.size() != (int) budgetRepository.countBudget(lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		budgetRepository.deleteBudgets(lines);
	}
	
}
