package com.rinitec.algerieoffice.services.company.team;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.team.AgentForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAgentService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countAgent(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllChoseUser(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	AgentForm readAgentForm(Long id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @return
	 */
	AgentForm readAgentFormCollaborator(Long userId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param agentForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws PhoneExistException
	 * @throws SocialExistException
	 * @throws NotFoundException
	 * @throws UrlUnavailableException
	 */
	Agent addAgent(AgentForm agentForm) throws AlreadyExistException, PhoneExistException, SocialExistException, NotFoundException, UrlUnavailableException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param agentForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws PhoneExistException
	 * @throws SocialExistException
	 * @throws NotFoundException
	 * @throws UrlUnavailableException
	 */
	Agent updateAgent(AgentForm agentForm) throws AlreadyExistException, PhoneExistException, SocialExistException, NotFoundException, UrlUnavailableException;
	
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
	ElementsList findAgentsList(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 */
	Agent deleteAgent(Long id, Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 */
	void deleteAgents(List<Long> lines, Long companyId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllAgents(Long companyId);
	
}
