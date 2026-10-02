package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, UUID>, CampaignRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param published
	 * @return
	 */
	long countByPublished(boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 */
	void deleteByDocumentIdAndType(UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param enabled
	 * @param published
	 * @return
	 */
	long countByCompanyIdAndEnabledAndPublished(Long companyId, boolean enabled, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Campaign c where c.id in :lines")
	long countCampaigns(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Campaign c where c.companyId = :companyId and c.id in :lines")
	long countCampaigns(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Campaign c where c.id in :lines")
	void deleteCampaigns(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from Campaign c where c.documentId in :lines and c.type = :type")
	void deleteCampaignsByDocumentsIds(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select c.id from Campaign c where c.companyId = ?1")
	List<UUID> findAllIdByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 * @return
	 */
	@Query("select c.id from Campaign c where c.documentId = ?1 and c.type = ?2")
	List<UUID> findAllIdByDocumentId(UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 * @return
	 */
	@Query("select c.id from Campaign c where c.documentId in :lines and c.type = :type")
	List<UUID> findAllIdByDocumentIds(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
}