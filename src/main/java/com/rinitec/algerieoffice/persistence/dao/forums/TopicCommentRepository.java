package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;

public interface TopicCommentRepository extends JpaRepository<TopicComment, UUID>, TopicCommentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 */
	void deleteByTopicId(UUID topicId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param parentUUID
	 */
	void deleteByParentUUID(UUID parentUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @return
	 */
	@Query("select t.id from TopicComment t where t.topicId = ?1")
	List<UUID> findAllTopicCommentByTopicId(UUID topicId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select t.id from TopicComment t where t.topicId in :lines")
	List<UUID> findAllTopicCommentByTopicIds(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from TopicComment t where t.topicId in :lines")
	void deleteAllTopicCommentByTopicIds(@Param("lines") List<UUID> lines);
	
}
