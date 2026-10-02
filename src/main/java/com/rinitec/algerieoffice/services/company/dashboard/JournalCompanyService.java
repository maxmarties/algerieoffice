package com.rinitec.algerieoffice.services.company.dashboard;

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

import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalLine;

@Service
public class JournalCompanyService implements IJournalCompanyService {

	private JournalCompanyRepository journalCompanyRepository;
	private CompanyAccountRepository companyAccountRepository;
	
	@Autowired
	public JournalCompanyService(JournalCompanyRepository journalCompanyRepository, CompanyAccountRepository companyAccountRepository) {
		this.journalCompanyRepository = journalCompanyRepository;
		this.companyAccountRepository = companyAccountRepository;
	}
	
	@Transactional
	private final void updateModifiedDateCompany(final Long companyId) {
		companyAccountRepository.updateModifiedDate(new DateTime(Date.from(Instant.now())), companyId);
	}
	
	@Transactional
	private final JournalCompany postJournalCompany(final OnJournalCompanyEvent event) {
		final JournalCompany journalCompany = new JournalCompany();
		journalCompany.setCompanyId(event.getCompanyId());
		journalCompany.setUserId(event.getUserId());
		journalCompany.setAction(event.getAction());
		journalCompany.setElement(event.getElement());
		journalCompany.setPostedDate(new DateTime(Date.from(Instant.now())));
		return journalCompanyRepository.save(journalCompany);
	}
	
	@Override
	@Transactional
	public JournalCompany addJournalCompany(final OnJournalCompanyEvent event) {
		final JournalCompany journalCompany = postJournalCompany(event);
		updateModifiedDateCompany(event.getCompanyId());
		return journalCompany;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findJournalList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = journalCompanyRepository.countAllJournalCompanyCriteria(companyId, filter, search);
		final List<JournalLine> lines = countResult == 0L ? new ArrayList<JournalLine>() 
				: journalCompanyRepository.findAllJouranlCompanyCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteJournal(final String id, final Long companyId) {
		try {
			final Optional<JournalCompany> uOptional = journalCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final JournalCompany journalCompany = uOptional.get();
			journalCompanyRepository.delete(journalCompany);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteJournals(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) journalCompanyRepository.countJournalCompany(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			journalCompanyRepository.deleteJournalCompanies(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllJournals(final Long companyId) {
		journalCompanyRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JournalAccess> findJournalAccessList(final Long companyId) {
		return journalCompanyRepository.findJournalAccessListCriteria(companyId, ConstraintesJournal.COMPANY_LOGIN_USER);
	}
	
}
