package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.Topic;

public interface TopicRepository extends JpaRepository<Topic, UUID>, TopicRepositoryCustom {

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
	 * @return
	 */
	@Query("select t.id from Topic t where t.userId = ?1")
	List<UUID> findAllTopicByUser(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param date
	 * @return
	 */
	@Query("select t.id from Topic t where t.createdDate <= ?1")
	List<UUID> findAllExpiredTopicId(DateTime date);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Topic t where t.id in :lines")
	void deleteTopics(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 */
	@Modifying
	@Query("update Topic t set t.viewCount = (t.viewCount + 1) where t.id = ?1")
	void incrementViews(UUID topicId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select t.userId from Topic t where t.id = ?1")
	Optional<Long> findAutorIdByTopicId(UUID id);
	
}
