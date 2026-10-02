package com.rinitec.algerieoffice.persistence.dao.companymaps.profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.LinkedWebsite;

public interface LinkedWebsiteRepository extends JpaRepository<LinkedWebsite, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	boolean existsByUrl(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from LinkedWebsite l where l.id in :lines")
	void deleteLinesLinkedWebsite(@Param("lines") List<Long> lines);
	
}
