package com.rinitec.algerieoffice.services.admins.dashboard;

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

import com.rinitec.algerieoffice.persistence.dao.journal.JournalAdminRepository;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalAdmin;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmJournalLine;

@Service
public class JournalAdminService implements IJournalAdminService {

	private JournalAdminRepository journalAdminRepository;
	
	@Autowired
	public JournalAdminService(JournalAdminRepository journalAdminRepository) {
		this.journalAdminRepository = journalAdminRepository;
	}
	
	@Override
	@Transactional
	public JournalAdmin addJournalAdmin(final OnJournalAdminEvent event) {
		final JournalAdmin journalAdmin = new JournalAdmin();
		journalAdmin.setUserId(event.getUserId());
		journalAdmin.setAction(event.getAction());
		journalAdmin.setElement(event.getElement());
		journalAdmin.setPostedDate(new DateTime(Date.from(Instant.now())));
		return journalAdminRepository.save(journalAdmin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findJournalList(final Integer filter, final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = journalAdminRepository.countAllJournalAdminCriteria(filter, search);
		final List<AdmJournalLine> lines = countResult == 0L ? new ArrayList<AdmJournalLine>() 
				: journalAdminRepository.findAllJouranlAdminCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteJournal(final String id) {
		try {
			final Optional<JournalAdmin> uOptional = journalAdminRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final JournalAdmin journalAdmin = uOptional.get();
			journalAdminRepository.delete(journalAdmin);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteJournals(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) journalAdminRepository.countJournalAdmin(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			journalAdminRepository.deleteJournalAdmins(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllJournals() {
		journalAdminRepository.deleteAll();
	}
	
}
