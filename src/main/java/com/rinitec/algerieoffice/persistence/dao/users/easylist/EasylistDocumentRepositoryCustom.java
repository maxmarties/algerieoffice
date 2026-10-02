package com.rinitec.algerieoffice.persistence.dao.users.easylist;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.web.modal.admins.easylist.AdmEasylistDocument;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistAnnonceLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistEmployeLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistEventLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistPostLine;

public interface EasylistDocumentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistDocumentsCriteria(Long userId, DocumentType type, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistLine> findAllEasylistDocumentsCriteria(Long userId, DocumentType type, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistPostCriteria(List<UUID> documents, String filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistPostLine> findAllEasylistPostCriteria(List<UUID> documents, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistAnnonceCriteria(List<UUID> documents, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistAnnonceLine> findAllEasylistAnnonceCriteria(List<UUID> documents, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistEventCriteria(List<UUID> documents, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistEventLine> findAllEasylistEventCriteria(List<UUID> documents, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistEmployeCriteria(List<UUID> documents, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documents
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistEmployeLine> findAllEasylistEmployeCriteria(List<UUID> documents, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmEasylistDocumentCriteria(Integer filter, String search);
	
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
	List<AdmEasylistDocument> findAllAdmEasylistDocumentCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
