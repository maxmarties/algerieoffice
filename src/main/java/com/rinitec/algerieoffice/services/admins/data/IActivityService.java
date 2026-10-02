package com.rinitec.algerieoffice.services.admins.data;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.admins.datas.ActivityForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IActivityService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	boolean existsById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	boolean existsByURL(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	ActivityForm readActivity(Long id);

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<List<String>> findAllChoseActivity();
	
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
	ElementsList findActivityList(String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param activityForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 */
	Activity addActivity(ActivityForm activityForm) throws AlreadyExistException, UrlUnavailableException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param activityForm
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 */
	Activity updateActivity(ActivityForm activityForm) throws AlreadyExistException, UrlUnavailableException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	Activity deleteActivity(Long id) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 */
	void deleteActivities(List<Long> lines) throws NotFoundException;
	
}
