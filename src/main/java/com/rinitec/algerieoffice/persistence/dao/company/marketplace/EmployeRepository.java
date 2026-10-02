package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;

public interface EmployeRepository extends JpaRepository<Employe, UUID>, EmployeRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasTrashed
	 * @return
	 */
	long countByHasTrashed(Boolean hasTrashed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasPublished
	 * @return
	 */
	long countByHasPublished(Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasTrashed
	 * @return
	 */
	long countByCompanyIdAndHasTrashed(Long companyId, Boolean hasTrashed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasTrashed
	 * @param hasPublished
	 * @return
	 */
	long countByCompanyIdAndHasTrashedAndHasPublished(Long companyId, Boolean hasTrashed, Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(e.id) from Employe e where e.companyId = :companyId and e.id in :lines")
	long countEmployes(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select e.id from Employe e where e.companyId = ?1 and e.identify = ?2")
	Optional<UUID> findIdByIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeId
	 * @return
	 */
	@Query("select e.title from Employe e where e.id = ?1")
	Optional<String> findTitleByEmployeId(UUID employeId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param linesUUID
	 */
	@Modifying
	@Query("update Employe e set e.hasTrashed = true where e.id in :linesUUID")
	void trashEmployes(@Param("linesUUID") List<UUID> linesUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Employe e set e.hasTrashed = true where e.companyId = ?1")
	void trashAllEmployes(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select count(e) from Employe e where e.companyId = ?1 and e.hasTrashed = false and e.hasPublished = true")
	long countExplorerEmployes(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select e from Employe e where e.companyId = ?1 and e.identify = ?2 and e.hasTrashed = false and e.hasPublished = true")
	Optional<Employe> findEmployeByCompanyIdAndIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeId
	 */
	@Modifying
	@Query("update Employe e set e.clickCount = (e.clickCount + 1) where e.id = ?1")
	void incrementClicks(UUID employeId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeId
	 */
	@Modifying
	@Query("update Employe e set e.workCount = (e.workCount + 1) where e.id = ?1")
	void incrementWorks(UUID employeId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Employe e where e.id in :lines")
	void deleteEmployes(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select e.id from Employe e where e.companyId = ?1 and e.hasTrashed = true")
	List<UUID> findAllTrashedIds(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select e.id from Employe e where e.companyId = ?1")
	List<UUID> findAllEmployeIdsByCompanyId(Long companyId);
	
}
