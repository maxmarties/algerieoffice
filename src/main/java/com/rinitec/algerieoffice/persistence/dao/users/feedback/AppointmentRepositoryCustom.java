package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmAppointLine;
import com.rinitec.algerieoffice.web.modal.company.communication.AppointLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserAppointLine;

public interface AppointmentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAppointmentCompanyCriteria(Long companyId, Integer filter, String search);
	
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
	List<AppointLine> findAllAppointmentCompanyCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAppointmentUserCriteria(Long userId, Integer filter, String search);
	
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
	List<UserAppointLine> findAllAppointmentUserCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAppointAdmin(Integer filter, String search);
	
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
	List<AdmAppointLine> findAllAppointAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
