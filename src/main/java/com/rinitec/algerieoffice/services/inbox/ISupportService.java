package com.rinitec.algerieoffice.services.inbox;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.inbox.SupportInfo;
import com.rinitec.algerieoffice.web.modal.inbox.SupportNotification;
import com.rinitec.algerieoffice.web.modal.inbox.SupportPush;
import com.rinitec.algerieoffice.web.modal.inbox.SupportSheet;

public interface ISupportService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	void deleteAllExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Integer countSupportOnline();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	Integer countSupportUser(Long userId);
	
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
	 * @param supportId
	 */
	void updateConsulted(Long userId, String supportId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void updateAllConsulted(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<String> findAllEmailsAdmin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param supportPush
	 * @return
	 */
	Support addSupport(SupportPush supportPush);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	boolean hasAllConsulted();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Integer countSupportAdmin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param supportId
	 */
	void updateConsultedAdmin(String supportId);
	
	/**
	 * VERSION BEGIN 03/2021
	 */
	void updateAllConsultedAdmin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void updateAllConsultedAdmin(Long userId);
	
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
	 * @return
	 */
	SupportInfo readSupportInfo(Long userId);
	
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
	 * @param userId
	 * @return
	 */
	String findUserEmail(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param adminId
	 * @return
	 */
	List<String> findAllEmailsAdminOne(Long adminId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param file
	 * @return
	 */
	SupportPush addSupportUserFile(User user, MultipartFile file);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param admin
	 * @param file
	 * @return
	 */
	SupportPush addSupportAdminFile(Long userId, User admin, MultipartFile file);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param message
	 * @return
	 */
	SupportPush addSupportHelp(Long userId, String message);
	
}
