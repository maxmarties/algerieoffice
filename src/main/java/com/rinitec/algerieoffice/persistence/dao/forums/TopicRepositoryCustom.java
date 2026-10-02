package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.web.form.forums.SearchForumForm;
import com.rinitec.algerieoffice.web.modal.forums.TopicInbox;
import com.rinitec.algerieoffice.web.modal.forums.TopicLine;
import com.rinitec.algerieoffice.web.modal.forums.TopicMember;
import com.rinitec.algerieoffice.web.modal.forums.TopicMini;

public interface TopicRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	TopicInbox findOneTopicInbox(UUID topicId, Long userId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	TopicMember findTopicMember(Long userId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param autorId
	 * @param limit
	 * @return
	 */
	List<Long> findAvatarsTopicLine(UUID topicId, Long autorId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchForumForm
	 * @return
	 */
	Long countAllTopicLine(SearchForumForm searchForumForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchForumForm
	 * @return
	 */
	List<TopicLine> findTopicLineList(SearchForumForm searchForumForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param autorId
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<TopicLine> findUserTopicLineList(Long autorId, Long userId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param topicId
	 * @param limit
	 * @return
	 */
	List<TopicMini> findTopicMiniList(UUID topicId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<TopicMini> findLastTopicMiniList(int limit);
	
}
