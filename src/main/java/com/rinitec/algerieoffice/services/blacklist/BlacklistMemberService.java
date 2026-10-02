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

import com.rinitec.algerieoffice.persistence.dao.blacklist.BlacklistMemberRepository;
import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistMember;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.feedback.LockForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.tools.BlacklistLine;

@Service
public class BlacklistMemberService implements IBlacklistMemberService {

	private BlacklistMemberRepository blacklistMemberRepository;
	
	@Autowired
	public BlacklistMemberService(BlacklistMemberRepository blacklistMemberRepository) {
		this.blacklistMemberRepository = blacklistMemberRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean HasMemberBlocked(final Long userId, final Long memberId) {
		return blacklistMemberRepository.existsByUserIdAndMemberId(userId, memberId);
	}
	
	@Override
	@Transactional
	public BlacklistMember postOrUpdateBlacklistMember(final Long userId, final LockForm lockForm) {
		BlacklistMember blacklistMember = blacklistMemberRepository.findByUserIdAndMemberId(userId, lockForm.getMemberLock());
		if(blacklistMember == null) {
			blacklistMember = new BlacklistMember(userId, lockForm.getMemberLock());
		}
		blacklistMember.setReason(lockForm.getReasonLock());
		blacklistMember.setLockedDate(new DateTime(Date.from(Instant.now())));
		return blacklistMemberRepository.save(blacklistMember);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findBlackList(final Long userId, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final Long countResult = blacklistMemberRepository.countAllBlacklistCriteria(userId, search);
		final List<BlacklistLine> lines = countResult == 0L ? new ArrayList<BlacklistLine>() 
				: blacklistMemberRepository.findAllBlacklistCriteria(userId, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteBlacklist(final String id, final Long userId) {
		try {
			final Optional<BlacklistMember> uOptional = blacklistMemberRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final BlacklistMember blacklistMember = uOptional.get();
			blacklistMemberRepository.delete(blacklistMember);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteBlacklists(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) blacklistMemberRepository.countBlacklistMembers(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			blacklistMemberRepository.deleteBlacklists(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllBlacklists(final Long userId) {
		blacklistMemberRepository.deleteByUserId(userId);
	}
	
}
