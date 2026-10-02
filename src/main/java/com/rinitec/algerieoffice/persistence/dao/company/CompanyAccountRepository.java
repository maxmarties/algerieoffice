package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;

public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, Long> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.createdDate from CompanyAccount c where c.companyId = ?1")
	Optional<DateTime> findCreatedDateById(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.modifiedDate from CompanyAccount c where c.companyId = ?1")
	Optional<DateTime> findModifiedDateById(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.createdById from CompanyAccount c where c.companyId = ?1")
	Optional<Long> findCreatedById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.numberOfVisits from CompanyAccount c where c.companyId = ?1")
	Optional<Long> findNumberOfVisitsById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param modifiedDate
	 * @param companyId
	 */
	@Modifying
	@Query("update CompanyAccount c set c.modifiedDate = ?1 where c.companyId = ?2")
	void updateModifiedDate(DateTime modifiedDate, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update CompanyAccount c set c.numberOfVisits = (c.numberOfVisits + 1) where c.companyId = ?1")
	void incrementVisits(Long companyId);
	
}
