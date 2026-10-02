package com.rinitec.algerieoffice.persistence.dao.company.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;

public interface CompanySheduleRepository extends JpaRepository<CompanyShedule, Long>, CompanySheduleRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param mobile
	 * @return
	 */
	boolean existsByMobile(String mobile);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fax
	 * @return
	 */
	boolean existsByFax(String fax);
	
}
