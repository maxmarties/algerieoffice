package com.rinitec.algerieoffice.services.forums;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicMark;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicSignal;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicVote;
import com.rinitec.algerieoffice.ujson.TopicQuizResponse;
import com.rinitec.algerieoffice.ujson.TopicReplyResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.forums.SearchForumForm;
import com.rinitec.algerieoffice.web.form.forums.SignalForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.forums.TopicMini;
import com.rinitec.algerieoffice.web.modal.forums.TopicReply;

public interface ITopicExplorerService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicQuizResponse
	 * @return
	 * @throws NotFoundException
	 * @throws AccessAuthorityException
	 */
	TopicVote addTopicVote(TopicQuizResponse topicQuizResponse) throws NotFoundException, AccessAuthorityException;

	/**
	 * VERSION BEGIN 03/2021
	 * @param topicReplyResponse
	 * @return
	 * @throws NotFoundException
	 * @throws AccessAuthorityException
	 */
	Object[] addTopicComment(TopicReplyResponse topicReplyResponse) throws NotFoundException, AccessAuthorityException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @return
	 * @throws NotFoundException
	 */
	Topic postOrRemoveTopicLike(Long userId, String topicId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @return
	 * @throws NotFoundException
	 */
	TopicMark postOrRemoveTopicMark(Long userId, String topicId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @return
	 * @throws NotFoundException
	 */
	Object[] postOrRemoveCommentLike(Long userId, String commentId) throws NotFoundException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param signalForm
	 * @return
	 */
	TopicSignal addTopicSignal(Long userId, SignalForm signalForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @return
	 * @throws NotFoundException
	 * @throws AccessAuthorityException
	 */
	TopicComment deleteTopicComment(Long userId, String commentId) throws NotFoundException, AccessAuthorityException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param topicId
	 * @param page
	 * @param rows
	 * @return
	 */
	ElementsList findTopicCommentList(Long userId, String topicId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param commentId
	 * @param limit
	 * @return
	 */
	List<TopicReply> findTopicRelpyCommentList(Long userId, String commentId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchForumForm
	 * @return
	 */
	ElementsList findTopicsList(SearchForumForm searchForumForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param limit
	 * @return
	 */
	List<TopicMini> findTopicMiniList(String topicId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<TopicMini> findLastTopicMiniList(int limit);
	
}
