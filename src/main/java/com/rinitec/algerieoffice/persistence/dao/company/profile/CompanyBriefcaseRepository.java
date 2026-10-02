package com.rinitec.algerieoffice.persistence.dao.company.profile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;

public interface CompanyBriefcaseRepository extends JpaRepository<CompanyBriefcase, Long>, CompanyBriefcaseRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.briefcase from CompanyBriefcase c where c.companyId = ?1")
	Optional<Integer> findBriefcaseByCompanyId(Long companyId);
	
}
