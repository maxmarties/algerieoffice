package com.rinitec.algerieoffice.services.forums;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.forums.TopicForm;
import com.rinitec.algerieoffice.web.modal.forums.TopicInbox;
import com.rinitec.algerieoffice.web.modal.forums.TopicLine;
import com.rinitec.algerieoffice.web.modal.forums.TopicMember;
import com.rinitec.algerieoffice.web.modal.forums.TopicQuizVote;

public interface ITopicService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean hasMaxTopic(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @return
	 */
	TopicForm readTopicForm(String id, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicForm
	 * @return
	 */
	Topic addTopic(TopicForm topicForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicForm
	 * @return
	 */
	Topic updateTopic(TopicForm topicForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Topic deleteTopic(String topicId, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param userId
	 * @return
	 */
	TopicInbox findTopicInbox(String topicId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param userId
	 * @return
	 */
	TopicQuizVote readTopicQuizVote(String topicId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param commentId
	 * @return
	 */
	TopicComment findTopicComment(String commentId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	TopicMember findTopicMember(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param autorId
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<TopicLine> findTopicUserList(Long autorId, Long userId, int limit);
	
}
