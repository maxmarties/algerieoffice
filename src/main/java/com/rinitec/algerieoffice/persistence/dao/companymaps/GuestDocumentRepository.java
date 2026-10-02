package com.rinitec.algerieoffice.persistence.dao.companymaps;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;

public interface GuestDocumentRepository extends JpaRepository<GuestDocument, UUID>, GuestDocumentRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 */
	void deleteByDocumentIdAndType(UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param lines
	 * @return
	 */
	@Query("select count(g.id) from GuestDocument g where g.companyId = :companyId and g.type = :type and g.id in :lines")
	long countGuestDocuments(@Param("companyId") Long companyId, @Param("type") DocumentType type, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(g.id) from GuestDocument g where g.id in :lines")
	long countGuests(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 * @return
	 */
	@Query("select g.fileUUID from GuestDocument g where g.documentId = ?1 and g.type = ?2")
	List<UUID> findAllFileUUIDByDocumentId(UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 * @return
	 */
	@Query("select g.fileUUID from GuestDocument g where g.documentId in :lines and g.type = :type")
	List<UUID> findAllFileUUIDByDocumentIds(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param lines
	 * @return
	 */
	@Query("select g.fileUUID from GuestDocument g where g.companyId = :companyId and g.type = :type and g.id in :lines")
	List<UUID> findAllFileUUIDById(@Param("companyId") Long companyId, @Param("type") DocumentType type, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @return
	 */
	@Query("select g.fileUUID from GuestDocument g where g.companyId = ?1 and g.type = ?2")
	List<UUID> findAllFileUUIDByCompanyId(Long companyId, DocumentType type);

	/**
	 * VERSION BEGIN 03/2021
	 * @param type
	 * @param lines
	 */
	@Modifying
	@Query("delete from GuestDocument g where g.type = :type and g.id in :lines")
	void deleteGuestDocuments(@Param("type") DocumentType type, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from GuestDocument g where g.id in :lines")
	void deleteGuests(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 */
	@Modifying
	@Query("delete from GuestDocument g where g.companyId = ?1 and g.type = ?2")
	void deleteAllGuestDocuments(Long companyId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from GuestDocument g where g.documentId in :lines and g.type = :type")
	void deleteGuestDocumentByDocumentsIds(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
		
}
