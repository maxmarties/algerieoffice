package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicSignal;

public interface TopicSignalRepository extends JpaRepository<TopicSignal, UUID> {

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
	@Query("delete from TopicSignal t where t.topicId in :lines")
	void deleteAllTopicSignalByTopicIds(@Param("lines") List<UUID> lines);
	
}
