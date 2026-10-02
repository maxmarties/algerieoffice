package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.communication.CollaboratorLine;

public interface CollaboratorRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllCollaboratorCompanyCriteria(Long companyId, Integer filter, String search);
	
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
	List<CollaboratorLine> findAllCollaboratorCompanyCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
