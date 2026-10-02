package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.inbox.Inbox;

public interface InboxRepository extends JpaRepository<Inbox, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select i.countNotification from Inbox i where i.userId = ?1")
	Optional<Integer> findCountNotificationById(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param countNotification
	 * @param userId
	 */
	@Modifying
	@Query("update Inbox i set i.countNotification = ?1 where i.userId = ?2")
	void updateCountNotificationById(Integer countNotification, Long userId);
	
}
