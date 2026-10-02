package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.PromoteActivity;

public interface PromoteActivityRepository extends JpaRepository<PromoteActivity, UUID> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param promoteUUID
	 */
	void deleteByPromoteUUID(UUID promoteUUID);

	/**
	 * VERSION BEGIN 03/2021
	 * @param promoteUUID
	 * @return
	 */
	@Query("select p.sector from PromoteActivity p where p.promoteUUID = ?1")
	List<Integer> findSectorsByPromoteId(UUID promoteUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from PromoteActivity p where p.promoteUUID in :lines")
	void deletePromoteActivityByPromoteIds(@Param("lines") List<UUID> lines);
	
}
