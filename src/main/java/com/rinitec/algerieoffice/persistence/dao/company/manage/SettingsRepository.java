package com.rinitec.algerieoffice.persistence.dao.company.manage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select s.service from Settings s where s.companyId = ?1")
	Optional<Boolean> findServiceById(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select s.banner from Settings s where s.companyId = ?1")
	Optional<String> findBannerById(Long companyId);
	
}
