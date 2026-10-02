package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.forums.TopicReply;

public interface TopicCommentRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @return
	 */
	Long countAllTopicCommentCriteria(UUID topicId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<TopicReply> findAllTopicCommentCriteria(Long userId, UUID topicId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @param limit
	 * @return
	 */
	List<TopicReply> findAllTopicReplyCommentCriteria(Long userId, UUID commentId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @return
	 */
	List<UserMini> findAllUsersCommentTopic(Long userId, UUID topicId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param parentId
	 * @return
	 */
	List<UserMini> findAllUsersCommentReply(Long userId, UUID parentId);
	
}
