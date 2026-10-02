package com.rinitec.algerieoffice.persistence.dao.companymaps;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;

public interface DocumentOrderRepository extends JpaRepository<DocumentOrder, UUID>, DocumentOrderRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean existsByUserIdAndType(Long userId, OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @param consulted
	 * @return
	 */
	boolean existsByUserIdAndTypeAndConsulted(Long userId, OrderType type, Boolean consulted);

	/**
	 * VERSION BEGIN 03/2021
	 * @param documentUUID
	 * @param type
	 */
	void deleteByDocumentUUIDAndType(UUID documentUUID, OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 * @return
	 */
	@Query("select count(d.id) from DocumentOrder d where d.type = :type and d.id in :lines")
	long countDocumentsOrder(@Param("lines") List<UUID> lines, @Param("type") OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from DocumentOrder d where d.type = :type and d.documentUUID in :lines")
	void deleteOrderByDocuments(@Param("lines") List<UUID> lines, @Param("type") OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 * @return
	 */
	@Query("select d.fileUUID from DocumentOrder d where d.type = :type and d.id in :lines")
	List<UUID> findAllFileUUIDById(@Param("lines") List<UUID> lines, @Param("type") OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param type
	 */
	@Modifying
	@Query("delete from DocumentOrder d where d.type = :type and d.id in :lines")
	void deleteDocumentsOrder(@Param("lines") List<UUID> lines, @Param("type") OrderType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentId
	 */
	@Modifying
	@Query("update DocumentOrder d set d.consulted = true where d.id = ?1")
	void updateConsultedById(UUID documentId);
	
}
