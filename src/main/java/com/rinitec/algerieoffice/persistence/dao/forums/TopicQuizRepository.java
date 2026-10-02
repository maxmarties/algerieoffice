package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicQuiz;

public interface TopicQuizRepository extends JpaRepository<TopicQuiz, UUID> {

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
	@Query("delete from TopicQuiz t where t.topicId in :lines")
	void deleteAllTopicQuizByTopicIds(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @return
	 */
	@Query("select t.proposal from TopicQuiz t where t.topicId = ?1 order by t.quiz ASC")
	List<String> findAllProposalByTopicId(UUID topicId);
	
}
