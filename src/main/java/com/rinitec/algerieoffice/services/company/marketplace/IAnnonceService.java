package com.rinitec.algerieoffice.services.company.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.marketplace.AnnonceForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAnnonceService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasMaxAnnonce(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countTrashedAnnonce(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	AnnonceForm readAnnonceForm(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws MaxKeyswordException
	 */
	Annonce addAnnonce(AnnonceForm annonceForm, int maxKeysword, Long userId) throws AlreadyExistException, UrlUnavailableException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceForm
	 * @param maxKeysword
	 * @param userId
	 * @return
	 * @throws AlreadyExistException
	 * @throws UrlUnavailableException
	 * @throws MaxKeyswordException
	 */
	Annonce updateAnnonce(AnnonceForm annonceForm, int maxKeysword, Long userId) throws AlreadyExistException, UrlUnavailableException, MaxKeyswordException;
	
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
	ElementsList findAnnoncesList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Annonce publishAnnonce(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Annonce trashAnnonce(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void trashAnnonces(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void trashAllAnnonces(Long companyId);
	
}
