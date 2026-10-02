package com.rinitec.algerieoffice.services.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.portfolio.EventForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IEventService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countEvent(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	EventForm readEventForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 * @throws MaxKeyswordException
	 * @throws EmptyElementException
	 * @throws InvalidImageException
	 */
	Event addEvent(EventForm eventForm, int maxKeysword, Long userId) throws UrlUnavailableException, AlreadyExistException, 
		MaxKeyswordException, EmptyElementException, InvalidImageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param eventForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws UrlUnavailableException
	 * @throws AlreadyExistException
	 * @throws MaxKeyswordException
	 * @throws EmptyElementException
	 * @throws InvalidImageException
	 */
	Event updateEvent(EventForm eventForm, int maxKeysword, Long userId) throws UrlUnavailableException, AlreadyExistException, 
		MaxKeyswordException, EmptyElementException, InvalidImageException;
	
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
	ElementsList findEventsList(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorEvent(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 * @return
	 */
	Event deleteEvent(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteEvents(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllEvents(Long companyId);
	
}
