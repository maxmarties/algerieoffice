package com.rinitec.algerieoffice.services.admins.ads;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.ads.SponsoreForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface ISponsoreService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	SponsoreForm readSponsoreForm(String id);

	/**
	 * VERSION BEGIN 03/2021
	 * @param sponsoreForm
	 * @return
	 */
	Sponsore addSponsore(SponsoreForm sponsoreForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sponsoreForm
	 * @return
	 */
	Sponsore updateSponsore(SponsoreForm sponsoreForm);
	
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
	ElementsList findSponsoresList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Sponsore deleteSponsore(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteSponsores(List<String> lines) throws NotFoundException, InvalidResourceException;
	
}
