package com.rinitec.algerieoffice.services.user.dashboard;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardAnalytic;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardComment;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardMessage;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardUser;

public interface IDashboardUserService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	DashboardUser readDashboardUser(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	int countCompletedProfile(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	int countPerformProfile(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	DashboardAnalytic readDashboardAnalytic(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<DashboardMessage> findLastDashboardMessage(Long userId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<DashboardComment> findLastDashboardComment(Long userId, int limit);
	
}
