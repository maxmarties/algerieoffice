package com.rinitec.algerieoffice.persistence.dao.journal;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalUser;

public interface JournalUserRepository extends JpaRepository<JournalUser, UUID>, JournalUserRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(j.id) from JournalUser j where j.userId = :userId and j.id in :lines")
	long countJournalUsers(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param date
	 */
	@Modifying
    @Query("delete from JournalUser j where j.postedDate <= ?1")
    void deleteAllExpiredSince(DateTime date);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from JournalUser j where j.id in :lines")
	void deleteJournalUsers(@Param("lines") List<UUID> lines);
	
}
