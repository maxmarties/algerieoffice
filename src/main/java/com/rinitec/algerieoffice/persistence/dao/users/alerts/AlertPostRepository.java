package com.rinitec.algerieoffice.persistence.dao.users.alerts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;

public interface AlertPostRepository extends JpaRepository<AlertPost, UUID>, AlertPostRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param enabled
	 * @return
	 */
	long countByEnabled(boolean enabled);
	
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
	 * @param userId
	 * @param lines
	 * @param type
	 * @return
	 */
	@Query("select count(a.id) from AlertPost a where a.userId = :userId and a.id in :lines and a.type = :type")
	long countAlertPosts(@Param("userId") Long userId, @Param("lines") List<UUID> lines, @Param("type") DocumentType type);

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from AlertPost a where a.id in :lines and a.type = :type")
	void deleteAlertPosts(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 */
	@Modifying
	@Query("delete from AlertPost a where a.userId = ?1 and a.type = ?2")
	void deleteAllAlertPosts(Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update AlertPost a set a.potential = (a.potential + 1) where a.id in :lines")
	void incrementAlertPotentiel(@Param("lines") List<UUID> lines);
	
}
