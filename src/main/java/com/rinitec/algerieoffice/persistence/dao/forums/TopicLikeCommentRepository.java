package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicLikeComment;

public interface TopicLikeCommentRepository extends JpaRepository<TopicLikeComment, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from TopicLikeComment t where t.commentId in :lines")
	void deleteAllTopicLikeCommentByCommentIds(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @return
	 */
	TopicLikeComment findByUserIdAndCommentId(Long userId, UUID commentId);
	
}
