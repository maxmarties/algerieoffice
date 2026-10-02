package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.List;

public interface IdentityHistoryRepositoryCustom {

	List<Object[]> findAllIdentityHistory(Long companyId);
	
}
