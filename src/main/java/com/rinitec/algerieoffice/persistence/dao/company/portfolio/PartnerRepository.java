package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;

public interface PartnerRepository extends JpaRepository<Partner, UUID>, PartnerRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
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
	@Query("select count(p.id) from Partner p where p.companyId = :companyId and p.id in :lines")
	long countPartners(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select p.photoUUID from Partner p where p.id in :lines and p.photoUUID != null")
	List<UUID> findAllPhotoUUIDById(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select p.photoUUID from Partner p where p.companyId = ?1 and p.photoUUID != null")
	List<UUID> findAllPhotoUUIDByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Partner p where p.id in :lines")
	void deleteParteners(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	@Query("select p.name from Partner p where p.id = ?1 and p.companyId = ?2")
	Optional<String> findNameByPartnerIdAndCompanyId(UUID id, Long companyId);
	
}
