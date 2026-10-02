package com.rinitec.algerieoffice.persistence.dao.journal;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalAdmin;

public interface JournalAdminRepository extends JpaRepository<JournalAdmin, UUID>, JournalAdminRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param date
	 */
	@Modifying
    @Query("delete from JournalAdmin j where j.postedDate <= ?1")
    void deleteAllExpiredSince(DateTime date);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(j.id) from JournalAdmin j where j.id in :lines")
	long countJournalAdmin(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from JournalAdmin j where j.id in :lines")
	void deleteJournalAdmins(@Param("lines") List<UUID> lines);
	
}
