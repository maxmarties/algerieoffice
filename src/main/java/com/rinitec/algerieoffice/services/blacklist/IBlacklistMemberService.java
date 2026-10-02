package com.rinitec.algerieoffice.services.blacklist;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistMember;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.feedback.LockForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IBlacklistMemberService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param memberId
	 * @return
	 */
	boolean HasMemberBlocked(Long userId, Long memberId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lockForm
	 * @return
	 */
	BlacklistMember postOrUpdateBlacklistMember(Long userId, LockForm lockForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findBlackList(Long userId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteBlacklist(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteBlacklists(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllBlacklists(Long userId);
	
}
