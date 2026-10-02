package com.rinitec.algerieoffice.services.company.portfolio;

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

import com.rinitec.algerieoffice.persistence.dao.company.portfolio.FaqRepository;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Faq;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.portfolio.FaqForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.portfolio.FaqLine;

@Service
public class FaqService implements IFaqService {

	private FaqRepository faqRepository;
	
	@Autowired
	public FaqService(FaqRepository faqRepository) {
		this.faqRepository = faqRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countFaq(final Long companyId) {
		return faqRepository.countByCompanyId(companyId);
	}
	
	private final FaqForm parseFaqForm(final Faq faq) {
		final FaqForm faqForm = new FaqForm();
		faqForm.setId(faq.getId().toString());
		faqForm.setCompanyId(faq.getCompanyId());
		faqForm.setQuestion(faq.getQuestion());
		faqForm.setDetail(new String(faq.getDetail()));
		faqForm.setUrlExtern(faq.getUrlExtern());
		faqForm.setHasPublished(faq.getHasPublished());
		return faqForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public FaqForm readFaqForm(final String id, final Long companyId) {
		try {
			final Optional<Faq> uOptional = faqRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Faq faq = uOptional.get();
				return parseFaqForm(faq);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	private final Faq postFaq(final Faq faq, final FaqForm faqForm, final Long userId) {
		faq.setQuestion(faqForm.getQuestion());
		faq.setDetail(faqForm.getDetail().getBytes());
		faq.setUrlExtern(!StringUtils.isEmpty(faqForm.getUrlExtern()) ? faqForm.getUrlExtern() : null);
		faq.setModifiedDate(new DateTime(Date.from(Instant.now())));
		faq.setAutorId(userId);
		faq.setHasPublished(faqForm.getHasPublished());
		return faqRepository.save(faq);
	}
	
	@Override
	@Transactional
	public Faq addFaq(final FaqForm faqForm, final Long userId) {
		if(!StringUtils.isEmpty(faqForm.getUrlExtern()) && faqRepository.existsByUrlExtern(faqForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		return postFaq(new Faq(faqForm.getCompanyId()), faqForm, userId);
	}
	
	@Override
	@Transactional
	public Faq updateFaq(final FaqForm faqForm, final Long userId) {
		final Optional<Faq> uOptional = faqRepository.findById(UUID.fromString(faqForm.getId()));
		if(uOptional.isPresent() && faqForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Faq faq = uOptional.get();
			if(!StringUtils.isEmpty(faqForm.getUrlExtern()) && !faqForm.getUrlExtern().equalsIgnoreCase(faq.getUrlExtern()) 
					&& faqRepository.existsByUrlExtern(faqForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			return postFaq(faq, faqForm, userId);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFaqsList(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = faqRepository.countAllFaqCriteria(companyId, filter, search);
		final List<FaqLine> lines = countResult == 0L ? new ArrayList<FaqLine>() 
				: faqRepository.findAllFaqCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllAutorFaq(final Long companyId) {
		return faqRepository.findAllAutorCriteria(companyId);
	}
	
	@Override
	@Transactional
	public Faq deleteFaq(final String id, final Long companyId) {
		try {
			final Optional<Faq> uOptional = faqRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Faq faq = uOptional.get();
			faqRepository.delete(faq);
			return faq;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteFaqs(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) faqRepository.countFaqs(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			faqRepository.deleteFaqs(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllFaqs(final Long companyId) {
		faqRepository.deleteByCompanyId(companyId);
	}
	
}
