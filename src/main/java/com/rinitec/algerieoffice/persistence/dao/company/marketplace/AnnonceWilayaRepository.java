package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceWilaya;

public interface AnnonceWilayaRepository extends JpaRepository<AnnonceWilaya, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceUUID
	 * @return
	 */
	@Query("select a.wilaya from AnnonceWilaya a where a.annonceUUID = ?1 and a.wilaya != null")
	List<Integer> findWilayasByAnnonceId(UUID annonceUUID);
	
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
	@Query("delete from AnnonceWilaya a where a.annonceUUID in :lines")
	void deleteAnnonceWilayaByAnnonceIds(@Param("lines") List<UUID> lines);
	
}
