package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;

public interface PromoteRepository extends JpaRepository<Promote, UUID>, PromoteRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasTrashed
	 * @return
	 */
	long countByHasTrashed(Boolean hasTrashed);
	
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
	 * @param enabled
	 * @return
	 */
	long countByCompanyIdAndHasTrashedAndEnabled(Long companyId, Boolean hasTrashed, boolean enabled);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(p.id) from Promote p where p.id in :lines")
	long countPromotes(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(p.id) from Promote p where p.companyId = :companyId and p.id in :lines")
	long countPromotes(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update Promote p set p.hasTrashed = true where p.id in :lines")
	void trashPromotes(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Promote p set p.hasTrashed = true where p.companyId = ?1")
	void trashAllPromotes(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Promote p where p.id in :lines")
	void deletePromotes(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select p.photoUUID from Promote p where p.id in :lines")
	List<UUID> findAllPhotoUUIDById(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select p.id from Promote p where p.companyId = ?1 and p.hasTrashed = true")
	List<UUID> findAllTrashedIds(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select p.id from Promote p where p.companyId = ?1")
	List<UUID> findAllPromoteIdsByCompanyId(Long companyId);
	
}
