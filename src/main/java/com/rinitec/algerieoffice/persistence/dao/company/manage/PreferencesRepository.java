package com.rinitec.algerieoffice.persistence.dao.company.manage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Preferences;

public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select p.mails from Preferences p where p.companyId = ?1")
	Optional<String> findMailsById(Long companyId);
	
}
