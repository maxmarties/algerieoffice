package com.rinitec.algerieoffice.persistence.dao.company.overview;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainheader;

public interface MainheaderRepository extends JpaRepository<Mainheader, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select m.hasCover from Mainheader m where m.companyId = ?1")
	Optional<Boolean> findHasCoverById(Long companyId);
	
}
