package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID>, NotificationRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param consulted
	 * @return
	 */
	long countByUserIdAndConsulted(Long userId, Boolean consulted);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(n.id) from Notification n where n.userId = :userId and n.id in :lines")
	long countNotifications(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param date
	 */
	@Modifying
    @Query("delete from Notification n where n.notifiedDate <= ?1")
    void deleteAllExpiredSince(DateTime date);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param pageable
	 * @return
	 */
	@Query("select n from Notification n where n.userId = :userId order by n.notifiedDate DESC")
	List<Notification> findAllNotification(@Param("userId") Long userId, Pageable pageable);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	@Modifying
	@Query("update Notification n set n.consulted = true where n.userId = ?1")
	void updateAllConsultedByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 */
	@Modifying
	@Query("delete from Notification n where n.id in :lines")
	void deleteNotifications(@Param("lines") List<UUID> lines);
	
}
