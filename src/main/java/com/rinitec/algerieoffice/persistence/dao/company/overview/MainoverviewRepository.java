package com.rinitec.algerieoffice.persistence.dao.company.overview;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainoverview;

public interface MainoverviewRepository extends JpaRepository<Mainoverview, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select m.history from Mainoverview m where m.companyId = ?1")
	Optional<String> findHistoryByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select m.hasOverview from Mainoverview m where m.companyId = ?1")
	Optional<Boolean> findHasOverviewByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select m.presentation from Mainoverview m where m.companyId = ?1")
	byte[] findPresentationByCompanyId(Long companyId);
	
}
