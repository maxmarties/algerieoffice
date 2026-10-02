package com.rinitec.algerieoffice.persistence.dao.companymaps;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;

public interface GuestPartnerRepository extends JpaRepository<GuestPartner, UUID>, GuestPartnerRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param partnerId
	 * @return
	 */
	boolean existsByCompanyIdAndPartnerId(Long companyId, Long partnerId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param partnerId
	 */
	void deleteByPartnerId(Long partnerId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param partnerId
	 * @param lines
	 * @return
	 */
	@Query("select count(g.id) from GuestPartner g where g.partnerId = :partnerId and g.id in :lines")
	long countGuestPartners(@Param("partnerId") Long partnerId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param partnerId
	 * @param lines
	 * @return
	 */
	@Query("select g.guestBy from GuestPartner g where g.partnerId = :partnerId and g.approuved = false and g.id in :lines")
	List<Long> findAllGuestedByCompanyId(@Param("partnerId") Long partnerId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param partnerId
	 * @return
	 */
	@Query("select g.guestBy from GuestPartner g where g.partnerId = ?1 and g.approuved = false")
	List<Long> findAllGuestedsByCompanyId(Long partnerId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from GuestPartner g where g.id in :lines")
	void deleteGuestPartners(@Param("lines") List<UUID> lines);
	
}
