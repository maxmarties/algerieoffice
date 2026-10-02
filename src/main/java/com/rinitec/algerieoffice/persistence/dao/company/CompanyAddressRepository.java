package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;

public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from CompanyAddress c where c.id in :lines")
	void deleteLinesCompanyAddress(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c from CompanyAddress c where c.company.id = ?1")
	List<CompanyAddress> findAllByCompanyId(Long companyId);
	
}
