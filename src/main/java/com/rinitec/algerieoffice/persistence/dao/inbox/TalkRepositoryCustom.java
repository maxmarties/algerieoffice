package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.user.feedback.TalkLine;

public interface TalkRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	Long countAllTalkCriteria(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<TalkLine> findAllTalkCriteria(Long companyId, Integer filter, int sort, int rows, int page, boolean hasDesc);
}
