package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce, UUID>, AnnonceRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasTrashed
	 * @return
	 */
	long countByHasTrashed(Boolean hasTrashed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasPublished
	 * @return
	 */
	long countByHasPublished(Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasTrashed
	 * @return
	 */
	long countByCompanyIdAndHasTrashed(Long companyId, Boolean hasTrashed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasTrashed
	 * @param hasPublished
	 * @return
	 */
	long countByCompanyIdAndHasTrashedAndHasPublished(Long companyId, Boolean hasTrashed, Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select a.id from Annonce a where a.companyId = ?1 and a.identify = ?2")
	Optional<UUID> findIdByIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceId
	 * @return
	 */
	@Query("select a.title from Annonce a where a.id = ?1")
	Optional<String> findTitleByAnnonceId(UUID annonceId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceId
	 * @param companyId
	 * @return
	 */
	@Query("select a.title from Annonce a where a.id = ?1 and a.companyId = ?2")
	Optional<String> findTitleById(UUID annonceId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(a.id) from Annonce a where a.companyId = :companyId and a.id in :lines")
	long countAnnonces(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param linesUUID
	 */
	@Modifying
	@Query("update Annonce a set a.hasTrashed = true where a.id in :linesUUID")
	void trashAnnonces(@Param("linesUUID") List<UUID> linesUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Annonce a set a.hasTrashed = true where a.companyId = ?1")
	void trashAllAnnonces(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @return
	 */
	@Query("select count(a) from Annonce a where a.companyId = ?1 and a.type = ?2 and a.hasTrashed = false and a.hasPublished = true")
	long countExplorerAnnonces(Long companyId, Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select a from Annonce a where a.companyId = ?1 and a.identify = ?2 and a.hasTrashed = false and a.hasPublished = true")
	Optional<Annonce> findAnnonceByCompanyIdAndIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceId
	 */
	@Modifying
	@Query("update Annonce a set a.clickCount = (a.clickCount + 1) where a.id = ?1")
	void incrementClicks(UUID annonceId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param annonceId
	 */
	@Modifying
	@Query("update Annonce a set a.workCount = (a.workCount + 1) where a.id = ?1")
	void incrementWorks(UUID annonceId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Annonce a where a.id in :lines")
	void deleteAnnonces(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select a.id from Annonce a where a.companyId = ?1 and a.hasTrashed = true")
	List<UUID> findAllTrashedIds(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select a.id from Annonce a where a.companyId = ?1")
	List<UUID> findAllAnnoncesIdsByCompanyId(Long companyId);
	
}
