package com.rinitec.algerieoffice.services.blacklist;

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

import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.tools.BlacklistForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.tools.BlacklistLine;

@Service
public class BlacklistCompanyService implements IBlacklistCompanyService {

	private UserRepository userRepository;
	private BlacklistCompanyRepository blacklistCompanyRepository;
	
	@Autowired
	public BlacklistCompanyService(UserRepository userRepository, BlacklistCompanyRepository blacklistCompanyRepository) {
		this.userRepository = userRepository;
		this.blacklistCompanyRepository = blacklistCompanyRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean HasUserBlocked(final Long userId, final Long companyId) {
		return blacklistCompanyRepository.existsByCompanyIdAndUserId(companyId, userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findBlackList(final Long companyId, final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = blacklistCompanyRepository.countAllBlacklistCriteria(companyId, search);
		final List<BlacklistLine> lines = countResult == 0L ? new ArrayList<BlacklistLine>() 
				: blacklistCompanyRepository.findAllBlacklistCriteria(companyId, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteBlacklist(final String id, final Long companyId) {
		try {
			final Optional<BlacklistCompany> uOptional = blacklistCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final BlacklistCompany blacklistCompany = uOptional.get();
			blacklistCompanyRepository.delete(blacklistCompany);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteBlacklists(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) blacklistCompanyRepository.countBlacklistCompanies(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			blacklistCompanyRepository.deleteBlacklists(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllBlacklists(final Long companyId) {
		blacklistCompanyRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BlacklistForm readBlacklistForm(final Long companyId, final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		if(uOptional.isPresent() && !companyId.equals(uOptional.get().getCompanyId())) {
			final User user = uOptional.get();
			final BlacklistForm blacklistForm = new BlacklistForm();
			blacklistForm.setCompanyId(companyId);
			blacklistForm.setUserId(userId);
			blacklistForm.setUsername(user.getDisplayName());
			return blacklistForm;
		}
		return null;
	}
	
	@Override
	@Transactional
	public BlacklistCompany addBlacklistCompany(final BlacklistForm blacklistForm, final Long autorId) {
		BlacklistCompany blacklistCompany = blacklistCompanyRepository.findByCompanyIdAndUserId(blacklistForm.getCompanyId(), blacklistForm.getUserId());
		if(blacklistCompany == null) {
			blacklistCompany = new BlacklistCompany(blacklistForm.getCompanyId(), blacklistForm.getUserId());
		}
		blacklistCompany.setReason(blacklistForm.getReason());
		blacklistCompany.setLockedDate(new DateTime(Date.from(Instant.now())));
		blacklistCompany.setLockedBy(autorId);
		return blacklistCompanyRepository.save(blacklistCompany);
	}
	
}
