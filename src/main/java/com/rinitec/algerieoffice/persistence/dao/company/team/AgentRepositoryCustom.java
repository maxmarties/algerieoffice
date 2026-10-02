package com.rinitec.algerieoffice.persistence.dao.company.team;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.team.AgentLine;

public interface AgentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAgentCriteria(Long companyId, String filter, String search);
	
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
	List<AgentLine> findAllAgentCriteria(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @param sexe
	 * @return
	 */
	Long countAgentsForSector(Integer sector, Integer wilaya, Boolean sexe);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param sexe
	 * @return
	 */
	Long countAgentsForActivity(String code, Integer wilaya, Boolean sexe);
	
}
