package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.blog.AutorLine;

public interface AutorRepositoryCustom {

	Long countAllAutorCriteria(String search);
	
	List<AutorLine> findAllAutorCriteria(String search, int sort, int rows, int page, boolean hasDesc);
	
	List<UserMini> findAllAutors();
	
}
