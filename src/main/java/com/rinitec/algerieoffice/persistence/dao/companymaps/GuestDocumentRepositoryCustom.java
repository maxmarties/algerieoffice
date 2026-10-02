package com.rinitec.algerieoffice.persistence.dao.companymaps;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmGuestLine;
import com.rinitec.algerieoffice.web.modal.company.prospect.ProspectLine;

public interface GuestDocumentRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllQuoteDocumentCriteria(Long companyId, Integer filter, String search);

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
	List<ProspectLine> findAllQuoteDocumentCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdsDocumentCriteria(Long companyId, Integer filter, String search);

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
	List<ProspectLine> findAllAdsDocumentCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllInfoDocumentCriteria(Long companyId, Integer filter, String search);

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
	List<ProspectLine> findAllInfoDocumentCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllJobDocumentCriteria(Long companyId, Integer filter, String search);

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
	List<ProspectLine> findAllJobDocumentCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	Long countGuestDocumentCompany(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countGuestCompany(Long companyId, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param object
	 * @param begin
	 * @return
	 */
	Long countGuestCompanyByObject(Long companyId, Integer object, DateTime begin);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countGuestPostCompany(Long companyId, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param hasPost
	 * @return
	 */
	Long countGuestAll(Long companyId, DateTime begin, boolean hasPost);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllGuestAdmin(Integer filter, String search);
	
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
	List<AdmGuestLine> findAllGuestAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param begin
	 * @param end
	 * @param type
	 * @return
	 */
	Long countAllGuest(DateTime begin, DateTime end, int type);
	
}
