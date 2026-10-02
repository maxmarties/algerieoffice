package com.rinitec.algerieoffice.services.user.easylist;

import java.util.List;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IEasylistDetailService {

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
	ElementsList findEasylistCompanies(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	EasylistCompany deleteEasylistCompany(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteEasylistCompanies(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllEasylistCompanies(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @return
	 */
	EasylistCompany readEasylistCompany(String id, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	ElementsList findEasylistCompany(String id, Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findEasylistDocuments(Long userId, DocumentType type, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param type
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	EasylistDocument deleteEasylistDocument(String id, Long userId, DocumentType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @param type
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteEasylistDocuments(List<String> lines, Long userId, DocumentType type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 */
	void deleteAllEasylistDocuments(Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param type
	 * @return
	 */
	EasylistDocument readEasylistDocument(String id, Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	ElementsList findEasylistPost(String id, Long userId, String filter, String search, int sort, int rows, int page, boolean hasDesc) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	ElementsList findEasylistAnnonce(String id, Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	ElementsList findEasylistEvent(String id, Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	ElementsList findEasylistEmploye(String id, Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc) throws NotFoundException, InvalidResourceException;
	
}
