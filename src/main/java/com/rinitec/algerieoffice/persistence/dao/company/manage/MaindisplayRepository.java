package com.rinitec.algerieoffice.persistence.dao.company.manage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Maindisplay;

public interface MaindisplayRepository extends JpaRepository<Maindisplay, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select m.display from Maindisplay m where m.companyId = ?1")
	Optional<String> findDisplayByCompanyId(Long companyId);
	
}
