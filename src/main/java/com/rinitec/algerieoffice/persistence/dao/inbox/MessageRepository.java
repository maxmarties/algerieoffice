package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.inbox.Message;

public interface MessageRepository extends JpaRepository<Message, UUID>, MessageRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param recepientId
	 * @param consulted
	 * @return
	 */
	long countByRecepientIdAndConsulted(Long recepientId, Boolean consulted);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param senderId
	 */
	void deleteBySenderId(Long senderId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param recepientId
	 */
	void deleteByRecepientId(Long recepientId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param senderId
	 * @return
	 */
	boolean existsBySenderId(Long senderId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param senderId
	 * @return
	 */
	boolean existsByRecepientId(Long senderId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(m.id) from Message m where m.recepientId = :userId and m.id in :lines")
	long countMessagesRecepient(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(m.id) from Message m where m.senderId = :userId and m.id in :lines")
	long countMessagesSender(@Param("userId") Long userId, @Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	@Modifying
    @Query("delete from Message m where m.postedDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param messageId
	 * @param recepientId
	 */
	@Modifying
	@Query("update Message m set m.consulted = true where m.id = ?1 and m.recepientId = ?2")
	void updateConsultedMessage(UUID messageId, Long recepientId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	@Modifying
	@Query("update Message m set m.consulted = true where m.recepientId = ?1")
	void updateAllConsultedByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param senderId
	 */
	@Modifying
	@Query("update Message m set m.consulted = true where m.recepientId = ?1 and m.senderId = ?2")
	void updateAllConsultedByUserIdAndSenderId(Long userId, Long senderId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(m.id) from Message m where m.id in :lines")
	long countMessages(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Message m where m.id in :lines")
	void deleteMessages(@Param("lines") List<UUID> lines);
	
}
