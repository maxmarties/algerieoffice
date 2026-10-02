package com.rinitec.algerieoffice.persistence.dao.users.favorite;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;

public interface FavoriteDocumentRepository extends JpaRepository<FavoriteDocument, UUID>, FavoriteDocumentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	long countByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param documentId
	 * @param type
	 * @return
	 */
	boolean existsByUserIdAndDocumentIdAndType(Long userId, UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param documentId
	 * @param type
	 * @return
	 */
	FavoriteDocument findByUserIdAndDocumentIdAndType(Long userId, UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 * @param type
	 */
	void deleteByDocumentIdAndType(UUID documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @param type
	 * @return
	 */
	@Query("select count(f.id) from FavoriteDocument f where f.userId = :userId and f.id in :lines and f.type = :type")
	long countFavoriteDocuments(@Param("userId") Long userId, @Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from FavoriteDocument f where f.id in :lines and f.type = :type")
	void deleteFavoriteDocuments(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 */
	@Modifying
	@Query("delete from FavoriteDocument f where f.userId = ?1 and f.type = ?2")
	void deleteAllFavoriteDocuments(Long userId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from FavoriteDocument f where f.documentId in :lines and f.type = :type")
	void deleteFavoriteDocumentByDocumentsIds(@Param("lines") List<UUID> lines, @Param("type") DocumentType type);
	
}
