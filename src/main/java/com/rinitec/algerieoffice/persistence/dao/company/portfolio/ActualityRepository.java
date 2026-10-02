package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;

public interface ActualityRepository extends JpaRepository<Actuality, UUID>, ActualityRepositoryCustom {
	
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
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
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
	@Query("select count(a.id) from Actuality a where a.companyId = :companyId and a.id in :lines")
	long countActualities(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select a.autorId from Actuality a where a.id = ?1")
	Optional<Long> findAutorIdByActualityId(UUID id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select a.id from Actuality a where a.companyId = ?1")
	List<UUID> findAllIDByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select a.photoUUID from Actuality a where a.id in :lines")
	List<UUID> findAllPhotoUUIDById(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select a.photoUUID from Actuality a where a.companyId = ?1")
	List<UUID> findAllPhotoUUIDByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Actuality a where a.id in :lines")
	void deleteActualities(@Param("lines") List<UUID> lines);
	
}
