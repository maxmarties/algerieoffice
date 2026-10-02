package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.CampaignTarget;

public interface CampaignTargetRepository extends JpaRepository<CampaignTarget, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from CampaignTarget c where c.id in :lines")
	void deleteCampaignsTarget(@Param("lines") List<UUID> lines);
	
}
