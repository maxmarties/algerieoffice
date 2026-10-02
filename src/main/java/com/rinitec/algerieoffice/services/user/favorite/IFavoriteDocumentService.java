package com.rinitec.algerieoffice.services.user.favorite;

import java.util.List;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IFavoriteDocumentService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param documentId
	 * @param type
	 * @return
	 */
	boolean hasFavoriteDocument(Long userId, String documentId, DocumentType type);
	
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
	ElementsList findFavoritePostList(Long userId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
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
	ElementsList findFavoriteAnnonceList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
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
	ElementsList findFavoriteEventList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
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
	ElementsList findFavoriteEmployetList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFavoriteDocument(String id, Long userId, DocumentType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteFavoriteDocuments(List<String> lines, Long userId, DocumentType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 */
	void deleteAllFavoriteDocuments(Long userId, DocumentType type);
	
}
