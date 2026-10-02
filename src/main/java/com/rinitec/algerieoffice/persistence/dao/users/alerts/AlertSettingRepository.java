package com.rinitec.algerieoffice.persistence.dao.users.alerts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertSetting;

public interface AlertSettingRepository extends JpaRepository<AlertSetting, Long> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select a.communications from AlertSetting a where a.userId = ?1")
	Optional<String> findCommunicationsById(Long userId);
	
}
