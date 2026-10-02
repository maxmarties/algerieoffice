package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicVote;

public interface TopicVoteRepository extends JpaRepository<TopicVote, UUID> {

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
	 * @param topicId
	 * @param quiz
	 * @return
	 */
	long countByTopicIdAndQuiz(UUID topicId, Integer quiz);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from TopicVote t where t.topicId in :lines")
	void deleteAllTopicVoteByTopicIds(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @return
	 */
	TopicVote findByUserIdAndTopicId(Long userId, UUID topicId);
	
}
