package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;

public interface WorkRepository extends JpaRepository<Work, UUID>, WorkRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasPublished
	 * @return
	 */
	long countByHasPublished(Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasPublished
	 * @return
	 */
	long countByCompanyIdAndHasPublished(Long companyId, Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(w.id) from Work w where w.companyId = :companyId and w.id in :lines")
	long countWorks(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select w.id from Work w where w.companyId = ?1 and w.identify = ?2")
	Optional<UUID> findIdByIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select w.id from Work w where w.companyId = ?1")
	List<UUID> findAllIdByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Work w where w.id in :lines")
	void deleteWorks(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param parentUUID
	 */
	@Modifying
	@Query("update Work w set w.partnerUUID = null where w.partnerUUID = ?1")
	void trashPartnerUUID(UUID parentUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update Work w set w.partnerUUID = null where w.partnerUUID in :lines")
	void trashPartners(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Work w set w.partnerUUID = null where w.companyId = ?1")
	void trashAllPartners(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select w from Work w where w.companyId = ?1 and w.identify = ?2 and w.hasPublished = true")
	Optional<Work> findWorkByCompanyIdAndIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select w.id from Work w where w.companyId = ?1")
	List<UUID> findAllWorkIdsByCompanyId(Long companyId);
	
}
