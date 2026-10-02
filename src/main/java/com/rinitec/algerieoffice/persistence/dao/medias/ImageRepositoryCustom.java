package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.medias.Image;

public interface ImageRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Image> findProxyImages(Long companyId, String search, int page, int rows);
	
}
