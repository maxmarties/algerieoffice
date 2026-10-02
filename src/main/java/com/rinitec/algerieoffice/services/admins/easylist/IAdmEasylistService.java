package com.rinitec.algerieoffice.services.admins.easylist;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAdmEasylistService {

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
	ElementsList findAdmEasylistCompanyList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	EasylistCompany deleteAdmEasylistCompany(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAdmEasylistCompanies(List<String> lines) throws NotFoundException, InvalidResourceException;
	
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
	ElementsList findAdmEasylistDocumentList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	EasylistDocument deleteAdmEasylistDocument(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAdmEasylistDocuments(List<String> lines) throws NotFoundException, InvalidResourceException;
	
}
