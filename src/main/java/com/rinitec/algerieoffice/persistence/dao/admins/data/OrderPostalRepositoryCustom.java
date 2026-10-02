package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.data.PostalLine;

public interface OrderPostalRepositoryCustom {

	Long countAllPostal(String search);
	
	List<PostalLine> findAllPostal(String search, int sort, int rows, int page, boolean hasDesc);
	
}
