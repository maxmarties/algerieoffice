package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;

public interface AnnonceDetailRepository extends JpaRepository<AnnonceDetail, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from AnnonceDetail a where a.id in :lines")
	void deleteAnnonceDetails(@Param("lines") List<UUID> lines);
	
}
