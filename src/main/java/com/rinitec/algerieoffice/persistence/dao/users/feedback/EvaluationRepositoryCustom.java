package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.communication.EvaluationLine;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticEvaluation;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardEvaluation;
import com.rinitec.algerieoffice.web.modal.user.communication.UserEvaluationLine;

public interface EvaluationRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEvaluationCompanyCriteria(Long companyId, Integer filter, String search);
	
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
	List<EvaluationLine> findAllEvaluationCompanyCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEvaluationUserCriteria(Long userId, Integer filter, String search);
	
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
	List<UserEvaluationLine> findAllEvaluationUserCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	AnalyticEvaluation findAnalyticEvaluation(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DashboardEvaluation> findLastDashboardEvaluation(Long companyId, int limit);
	
}
