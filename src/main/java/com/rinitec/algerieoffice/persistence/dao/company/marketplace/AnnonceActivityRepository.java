package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceActivity;

public interface AnnonceActivityRepository extends JpaRepository<AnnonceActivity, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceUUID
	 * @return
	 */
	@Query("select a.sector from AnnonceActivity a where a.annonceUUID = ?1")
	List<Integer> findSectorsByAnnonceId(UUID annonceUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceUUID
	 */
	void deleteByAnnonceUUID(UUID annonceUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from AnnonceActivity a where a.annonceUUID in :lines")
	void deleteAnnonceActivityByAnnonceIds(@Param("lines") List<UUID> lines);
	
}
