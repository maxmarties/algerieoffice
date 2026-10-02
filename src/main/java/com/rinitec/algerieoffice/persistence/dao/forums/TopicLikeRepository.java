package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicLike;

public interface TopicLikeRepository extends JpaRepository<TopicLike, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @return
	 */
	boolean existsByUserIdAndTopicId(Long userId, UUID topicId);
	
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
	 * @param lines
	 */
	@Modifying
	@Query("delete from TopicLike t where t.topicId in :lines")
	void deleteAllTopicLikeByTopicIds(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @return
	 */
	TopicLike findByUserIdAndTopicId(Long userId, UUID topicId);
	
}
