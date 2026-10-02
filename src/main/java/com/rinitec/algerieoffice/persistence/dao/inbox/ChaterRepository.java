package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;

public interface ChaterRepository extends JpaRepository<Chater, UUID>, ChaterRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	@Modifying
    @Query("delete from Chater c where c.chaterDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Chater c where c.id in :lines")
	long countChaters(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Chater c where c.id in :lines")
	void deleteChaters(@Param("lines") List<UUID> lines);
	
}
