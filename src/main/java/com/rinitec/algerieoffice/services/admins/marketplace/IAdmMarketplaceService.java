package com.rinitec.algerieoffice.services.admins.marketplace;

import com.rinitec.algerieoffice.web.modal.admins.ElementsList;

public interface IAdmMarketplaceService {

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
	ElementsList findAdmAnnoncesList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
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
	ElementsList findAdmEmployesList(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
