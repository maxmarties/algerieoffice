package com.rinitec.algerieoffice.services.inbox;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.inbox.FollowedAccount;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedCompany;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedUser;

public interface IFollowedService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FollowedCompany> findAllFollowedCompany(Long userId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FollowedAccount> findAllFollowedAccount(Long userId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FollowedUser> findAllFollowedUser(Long userId, Long companyId, String search, int page, int rows);
	
}
