package com.rinitec.algerieoffice.services.admins;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;

public interface IDeleteCompanyService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 */
	Company deleteCompany(Long companyId) throws NotFoundException;
	
}
