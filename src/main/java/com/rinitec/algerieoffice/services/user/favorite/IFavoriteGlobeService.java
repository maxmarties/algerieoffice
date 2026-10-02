package com.rinitec.algerieoffice.services.user.favorite;

import java.util.List;

import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IFavoriteGlobeService {

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
	ElementsList findFavoriteCompanyList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void alertFavoriteCompany(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFavoriteCompany(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFavoriteCompanies(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllFavoriteCompanies(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param accountId
	 * @return
	 */
	boolean hasFavoriteAccount(Long userId, Long accountId);

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
	ElementsList findFavoriteAccountList(Long userId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void alertFavoriteAccount(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFavoriteAccount(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFavoriteAccounts(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllFavoriteAccounts(Long userId);
	
}
