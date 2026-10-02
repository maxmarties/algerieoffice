package com.rinitec.algerieoffice.persistence.dao.users.easylist;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;

public interface EasylistDocumentRepository extends JpaRepository<EasylistDocument, UUID>, EasylistDocumentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	long countByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 */
	void deleteByUserIdAndType(Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @param lines
	 * @return
	 */
	@Query("select count(e.id) from EasylistDocument e where e.userId = :userId and e.type = :type and e.id in :lines")
	long countEasylistDocuments(@Param("userId") Long userId, @Param("type") DocumentType type, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(e.id) from EasylistDocument e where e.id in :lines")
	long countAllEasylistDocuments(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from EasylistDocument e where e.type = :type and e.id in :lines")
	void deleteEasylistDocuments(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EasylistDocument e where e.id in :lines")
	void deleteAllEasylistDocuments(@Param("lines") List<UUID> lines);
	
}
