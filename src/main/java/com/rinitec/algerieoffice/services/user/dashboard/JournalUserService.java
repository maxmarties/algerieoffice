package com.rinitec.algerieoffice.services.user.dashboard;

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

import com.rinitec.algerieoffice.persistence.dao.journal.JournalUserRepository;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalUser;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardHistory;
import com.rinitec.algerieoffice.web.modal.user.dashboard.HistoryLine;

@Service
public class JournalUserService implements IJournalUserService {

	private JournalUserRepository journalUserRepository;
	
	@Autowired
	public JournalUserService(JournalUserRepository journalUserRepository) {
		this.journalUserRepository = journalUserRepository;
	}
	
	@Override
	@Transactional
	public JournalUser addJournalUser(final OnJournalUserEvent event) {
		final JournalUser journalUser = new JournalUser();
		journalUser.setUserId(event.getUserId());
		journalUser.setAction(event.getAction());
		journalUser.setElement(event.getElement());
		journalUser.setPostedDate(new DateTime(Date.from(Instant.now())));
		return journalUserRepository.save(journalUser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findJournalList(final Long userId, final Integer filter, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = journalUserRepository.countAllJournalUserCriteria(userId, filter);
		final List<HistoryLine> lines = countResult == 0L ? new ArrayList<HistoryLine>() 
				: journalUserRepository.findAllJouranlUserCriteria(userId, filter, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteJournal(final String id, final Long userId) {
		try {
			final Optional<JournalUser> uOptional = journalUserRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final JournalUser journalUser = uOptional.get();
			journalUserRepository.delete(journalUser);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteJournals(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) journalUserRepository.countJournalUsers(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			journalUserRepository.deleteJournalUsers(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllJournals(final Long userId) {
		journalUserRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardHistory> findLastDashboardHistory(final Long userId, final int limit) {
		return journalUserRepository.findLastDashboardHistory(userId, limit);
	}
	
}
