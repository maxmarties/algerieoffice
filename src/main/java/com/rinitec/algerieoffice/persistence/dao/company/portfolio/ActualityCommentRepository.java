package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;

public interface ActualityCommentRepository extends JpaRepository<ActualityComment, UUID>, ActualityCommentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actualityId
	 */
	void deleteByActualityId(UUID actualityId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from ActualityComment a where a.actualityId in :lines")
	void deleteActualitiesComment(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select a.id from ActualityComment a where a.userId = ?1")
	List<UUID> findAllCommentUUIDByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actualityId
	 * @return
	 */
	@Query("select a.id from ActualityComment a where a.actualityId = ?1")
	List<UUID> findAllCommentUUIDByActualityId(UUID actualityId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select a.id from ActualityComment a where a.actualityId in :lines")
	List<UUID> findAllCommentUUIDByActualitiesId(@Param("lines") List<UUID> lines);
	
}
