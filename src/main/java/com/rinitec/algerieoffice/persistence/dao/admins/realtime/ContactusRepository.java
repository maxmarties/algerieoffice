package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;

public interface ContactusRepository extends JpaRepository<Contactus, UUID>, ContactusRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Contactus c where c.id in :lines")
	long countContactus(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Contactus c where c.id in :lines")
	void deleteContactus(@Param("lines") List<UUID> lines);
	
}
