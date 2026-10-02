package com.rinitec.algerieoffice.persistence.dao.admins.premium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;

public interface PremiumRepository extends JpaRepository<Premium, UUID>, PremiumRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(p.id) from Premium p where p.id in :lines")
	long countPremiums(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(p.id) from Premium p where p.companyId = :companyId and p.id in :lines")
	long countPremiums(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Premium p set p.enabled = false where p.companyId = ?1")
	void disabledAllByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	@Modifying
	@Query("update Premium p set p.enabled = false where p.expiryDate <= ?1")
	void disabledAllExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param now
	 * @return
	 */
	@Query("select p.pass from Premium p where p.companyId = ?1 and p.expiryDate > ?2 and p.enabled = true")
	Optional<Integer> findPremiumPassByCompanyId(Long companyId, DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Premium p where p.id in :lines")
	void deleteAdminPremiums(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Premium p where p.id in :lines and p.enabled = false")
	void deletePremiums(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("delete from Premium p where p.companyId = ?1 and p.enabled = false")
	void deleteAllPremiums(Long companyId);
	
}
