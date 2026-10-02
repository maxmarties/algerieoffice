package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;

public interface CompanySearchRepository extends JpaRepository<CompanySearch, Long>, CompanySearchRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update CompanySearch c set c.simultude = (c.simultude + 1) where c.companyId in :lines")
	void incrementSimultudes(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update CompanySearch c set c.token = (c.token + 1) where c.companyId in :lines")
	void incrementTokens(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update CompanySearch c set c.filter = (c.filter + 1) where c.companyId in :lines")
	void incrementFilters(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update CompanySearch c set c.tag = (c.tag + 1) where c.companyId in :lines")
	void incrementTags(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update CompanySearch c set c.view = (c.view + 1) where c.companyId in :lines")
	void incrementViews(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param completed
	 * @param companyId
	 */
	@Modifying
	@Query("update CompanySearch c set c.completed = ?1 where c.companyId = ?2")
	void updateCompletedById(int completed, Long companyId);
	
}
