package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.inbox.Support;

public interface SupportRepository extends JpaRepository<Support, UUID>, SupportRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	@Modifying
    @Query("delete from Support s where s.postedDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(s.id) from Support s where s.id in :lines")
	long countSupport(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 * @return
	 */
	@Query("select s.screenUUID from Support s where s.postedDate <= ?1 and s.screenUUID != null")
	List<UUID> findAllScreenUUIDExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param supportId
	 * @param userId
	 */
	@Modifying
	@Query("update Support s set s.consulted = true where s.id = ?1 and s.userId = ?2")
	void updateConsultedByUserId(UUID supportId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	@Modifying
	@Query("update Support s set s.consulted = true where s.userId = ?1 and s.adminId != null")
	void updateAllConsultedByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param supportId
	 */
	@Modifying
	@Query("update Support s set s.consulted = true where s.id = ?1")
	void updateConsultedByAdminId(UUID supportId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	@Modifying
	@Query("update Support s set s.consulted = true where s.userId = ?1 and s.adminId = null")
	void updateAllConsultedByAdminId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 */
	@Modifying
	@Query("update Support s set s.consulted = true where s.adminId = null")
	void updateAllConsulted();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select s.screenUUID from Support s where s.id in :lines")
	List<UUID> findAllScreensUUID(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Support s where s.id in :lines")
	void deleteSupports(@Param("lines") List<UUID> lines);
	
}
