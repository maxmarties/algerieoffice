package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param commentId
	 */
	void deleteByCommentId(UUID commentId);
	
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
	@Query("delete from CommentLike c where c.commentId in :lines")
	void deleteCommentsLike(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @return
	 */
	CommentLike findByUserIdAndCommentId(Long userId, UUID commentId);
	
}
