package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmSupportLine;
import com.rinitec.algerieoffice.web.modal.inbox.SupportNotification;
import com.rinitec.algerieoffice.web.modal.inbox.SupportSheet;

public interface SupportRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	long countNewSupportUser(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	long countNewSupportAdmin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param rows
	 * @return
	 */
	List<SupportNotification> findAllSupportNotification(int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<SupportSheet> findAllSupportSheetUser(Long userId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<SupportSheet> findAllSupportSheetAdmin(Long userId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param recevied
	 * @return
	 */
	List<UserMini> findAllUserSupportCriteria(boolean recevied);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param recevied
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllSupportCriteria(boolean recevied, Long filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmSupportLine> findAllSupportReceviedCriteria(Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmSupportLine> findAllSupportSenderCriteria(Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
