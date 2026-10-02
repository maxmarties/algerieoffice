package com.rinitec.algerieoffice.persistence.dao.blacklist;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;

public interface BlacklistCompanyRepository extends JpaRepository<BlacklistCompany, UUID>, BlacklistCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param userId
	 * @return
	 */
	boolean existsByCompanyIdAndUserId(Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param userId
	 * @return
	 */
	BlacklistCompany findByCompanyIdAndUserId(Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(b.id) from BlacklistCompany b where b.companyId = :companyId and b.id in :lines")
	long countBlacklistCompanies(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from BlacklistCompany b where b.id in :lines")
	void deleteBlacklists(@Param("lines") List<UUID> lines);
	
}
