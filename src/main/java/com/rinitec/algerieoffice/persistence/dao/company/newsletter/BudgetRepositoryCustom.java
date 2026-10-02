package com.rinitec.algerieoffice.persistence.dao.company.newsletter;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.premium.AdmBudgetLine;

public interface BudgetRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @return
	 */
	Long countAllBudgetAdmin(String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmBudgetLine> findAllBudgetAdmin(String search, int sort, int rows, int page, boolean hasDesc);
	
}
