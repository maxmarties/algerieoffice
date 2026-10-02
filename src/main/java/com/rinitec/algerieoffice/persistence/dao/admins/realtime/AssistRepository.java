package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;

public interface AssistRepository extends JpaRepository<Assist, UUID>, AssistRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(a.id) from Assist a where a.id in :lines")
	long countAssists(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Assist a where a.id in :lines")
	void deleteAssists(@Param("lines") List<UUID> lines);
	
}
