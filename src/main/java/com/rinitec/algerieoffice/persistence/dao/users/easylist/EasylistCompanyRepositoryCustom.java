package com.rinitec.algerieoffice.persistence.dao.users.easylist;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.easylist.AdmEasylistCompany;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistCompanyLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistLine;

public interface EasylistCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistCompaniesCriteria(Long userId, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistLine> findAllEasylistCompaniesCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companies
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEasylistCompanyCriteria(List<Long> companies, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companies
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<EasylistCompanyLine> findAllEasylistCompanyCriteria(List<Long> companies, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmEasylistCompanyCriteria(Integer filter, String search);
	
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
	List<AdmEasylistCompany> findAllAdmEasylistCompanyCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	List<UUIDMini> findAllEasylistCompany(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companies
	 * @return
	 */
	List<UserMini> findAllNewsletterEasylistCompany(List<Long> companies);
	
}
