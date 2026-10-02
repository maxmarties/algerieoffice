package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmChatboterLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmChatbotLine;
import com.rinitec.algerieoffice.web.modal.company.communication.ChatbotLine;

public interface ChatbotRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllChatbotCriteria(Long companyId, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<ChatbotLine> findAllChatbotCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmChatbotCriteria(Boolean filter, String search);
	
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
	List<AdmChatbotLine> findAllAdmChatbotCriteria(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmChatboterCriteria(Integer filter, String search);
	
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
	List<AdmChatboterLine> findAllAdmChatboterCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
