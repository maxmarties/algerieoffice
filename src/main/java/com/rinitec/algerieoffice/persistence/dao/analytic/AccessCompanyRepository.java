package com.rinitec.algerieoffice.persistence.dao.analytic;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.analytic.AccessCompany;

public interface AccessCompanyRepository extends JpaRepository<AccessCompany, UUID>, AccessCompanyRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(a) from AccessCompany a where a.companyId = :companyId and a.id in :lines")
	long countAccessCompany(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param date
	 */
	@Modifying
    @Query("delete from AccessCompany a where a.accessDate <= ?1")
    void deleteAllExpiredSince(DateTime date);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from AccessCompany a where a.id in :lines")
	void deleteAccessCompanies(@Param("lines") List<UUID> lines);
	
}
