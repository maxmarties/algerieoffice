package com.rinitec.algerieoffice.persistence.dao.company.profile;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.data.IdentityLine;

public interface CompanyIdentityRepositoryCustom {

	Long countAllIdentityCriteria(String search);
	
	List<IdentityLine> findAllIdentityCriteria(String search, int sort, int rows, int page, boolean hasDesc);
	
	Object[] readIdentityDetail(Long companyId);
	
}
