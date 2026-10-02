package com.rinitec.algerieoffice.services.forums;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.forums.TopicCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicMarkRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicQuizRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicSignalRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicVoteRepository;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicQuiz;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicVote;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.forums.TopicForm;
import com.rinitec.algerieoffice.web.modal.forums.TopicInbox;
import com.rinitec.algerieoffice.web.modal.forums.TopicLine;
import com.rinitec.algerieoffice.web.modal.forums.TopicMember;
import com.rinitec.algerieoffice.web.modal.forums.TopicQuizVote;

@Service
public class TopicService implements ITopicService {

	private TopicRepository topicRepository;
	private TopicQuizRepository topicQuizRepository;
	private TopicVoteRepository topicVoteRepository;
	private TopicCommentRepository topicCommentRepository;
	private TopicLikeRepository topicLikeRepository;
	private TopicMarkRepository topicMarkRepository;
	private TopicSignalRepository topicSignalRepository;
	private TopicLikeCommentRepository topicLikeCommentRepository;
	
	@Autowired
	public TopicService(TopicRepository topicRepository, TopicQuizRepository topicQuizRepository, TopicVoteRepository topicVoteRepository, 
			TopicCommentRepository topicCommentRepository, TopicLikeRepository topicLikeRepository, TopicMarkRepository topicMarkRepository, 
			TopicSignalRepository topicSignalRepository, TopicLikeCommentRepository topicLikeCommentRepository) {
		this.topicRepository = topicRepository;
		this.topicQuizRepository = topicQuizRepository;
		this.topicVoteRepository = topicVoteRepository;
		this.topicCommentRepository = topicCommentRepository;
		this.topicLikeRepository = topicLikeRepository;
		this.topicMarkRepository = topicMarkRepository;
		this.topicSignalRepository = topicSignalRepository;
		this.topicLikeCommentRepository = topicLikeCommentRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasMaxTopic(final Long userId) {
		final long count = topicRepository.countByUserId(userId);
		return count >= ConstraintesForm.MAX_COUNT_DATA;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TopicForm readTopicForm(final String id, final Long userId) {
		try {
			final Optional<Topic> uOptional = topicRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && uOptional.get().getUserId().equals(userId)) {
				final Topic topic = uOptional.get();
				final TopicForm topicForm = new TopicForm();
				topicForm.setId(id);
				topicForm.setUserId(userId);
				topicForm.setCategory(topic.getCategory());
				topicForm.setTitle(topic.getTitle());
				topicForm.setDetail(new String(topic.getDetail()));
				topicForm.setLanguage(topic.getLanguage());
				topicForm.setHasQuiz(topic.getHasQuiz());
				if(topic.getHasQuiz()) {
					topicForm.setProposals(topicQuizRepository.findAllProposalByTopicId(topic.getId()));
				}
				return topicForm;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final void createNewTopicsQuiz(final UUID topicId, final List<String> proposals) {
		final List<TopicQuiz> topicsQuiz = new ArrayList<TopicQuiz>();
		for(int i = 0; i < proposals.size(); i++) {
			final TopicQuiz topicQuiz = new TopicQuiz(topicId);
			topicQuiz.setQuiz(i + 1);
			topicQuiz.setProposal(proposals.get(i));
			topicsQuiz.add(topicQuiz);
		}
		topicQuizRepository.saveAll(topicsQuiz);
	}
	
	@Transactional
	private final Topic postTopic(final TopicForm topicForm) {
		final Topic topic = new Topic();
		topic.setUserId(topicForm.getUserId());
		topic.setCategory(topicForm.getCategory());
		topic.setTitle(topicForm.getTitle());
		topic.setDetail(topicForm.getDetail().getBytes());
		topic.setLanguage(topicForm.getLanguage());
		topic.setCreatedDate(new DateTime(Date.from(Instant.now())));
		topic.setHasQuiz(topicForm.getHasQuiz());
		return topicRepository.save(topic);
	}
	
	@Override
	@Transactional
	public Topic addTopic(final TopicForm topicForm) {
		final Topic topic = postTopic(topicForm);
		if(topic.getHasQuiz()) {
			createNewTopicsQuiz(topic.getId(), topicForm.getProposals());
		}
		return topic;
	}
	
	@Override
	@Transactional
	public Topic updateTopic(final TopicForm topicForm) {
		final Optional<Topic> uOptional = topicRepository.findById(UUID.fromString(topicForm.getId()));
		if(uOptional.isPresent() && uOptional.get().getUserId().equals(topicForm.getUserId())) {
			final Topic topic = uOptional.get();
			topic.setTitle(topicForm.getTitle());
			topic.setDetail(topicForm.getDetail().getBytes());
			topic.setLanguage(topicForm.getLanguage());
			topic.setModifiedDate(new DateTime(Date.from(Instant.now())));
			topic.setHasQuiz(topicForm.getHasQuiz());
			return topicRepository.save(topic);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Topic deleteTopic(final String topicId, final Long userId) {
		try {
			final UUID topicUUID = UUID.fromString(topicId);
			final Optional<Topic> uOptional = topicRepository.findById(topicUUID);
			if(!uOptional.isPresent() || !uOptional.get().getUserId().equals(userId)) {
				throw new NotFoundException("message.error.notfound");
			}
			final Topic topic = uOptional.get();
			final List<UUID> commentsId = topicCommentRepository.findAllTopicCommentByTopicId(topicUUID);
			if(!commentsId.isEmpty()) {
				topicLikeCommentRepository.deleteAllTopicLikeCommentByCommentIds(commentsId);
				topicCommentRepository.deleteByTopicId(topicUUID);
			}
			topicLikeRepository.deleteByTopicId(topicUUID);
			topicMarkRepository.deleteByTopicId(topicUUID);
			topicQuizRepository.deleteByTopicId(topicUUID);
			topicSignalRepository.deleteByTopicId(topicUUID);
			topicVoteRepository.deleteByTopicId(topicUUID);
			topicRepository.delete(topic);
			return topic;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public TopicInbox findTopicInbox(final String topicId, final Long userId) {
		try {
			final UUID topicUUID = UUID.fromString(topicId);
			final TopicInbox inbox = topicRepository.findOneTopicInbox(topicUUID, userId);
			topicRepository.incrementViews(topicUUID);
			return inbox;
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TopicQuizVote readTopicQuizVote(final String topicId, final Long userId) {
		try {
			final UUID topicUUID = UUID.fromString(topicId);
			final List<String> proposals = topicQuizRepository.findAllProposalByTopicId(topicUUID);
			if(!proposals.isEmpty()) {
				final List<Long> counts = new ArrayList<Long>();
				for (int i = 0; i < proposals.size(); i++) {
					counts.add(topicVoteRepository.countByTopicIdAndQuiz(topicUUID, i + 1));
				}
				final TopicVote topicVote = topicVoteRepository.findByUserIdAndTopicId(userId, topicUUID);
				return new TopicQuizVote(topicVote, proposals, counts);
			}
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TopicComment findTopicComment(final String commentId) {
		try {
			final Optional<TopicComment> uOptional = topicCommentRepository.findById(UUID.fromString(commentId));
			if(uOptional.isPresent() && topicRepository.existsById(uOptional.get().getTopicId())) {
				return uOptional.get();
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TopicMember findTopicMember(final Long userId) {
		try {
			return topicRepository.findTopicMember(userId);
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TopicLine> findTopicUserList(final Long autorId, final Long userId, final int limit) {
		final List<TopicLine> lines = topicRepository.findUserTopicLineList(autorId, userId, limit);
		if(!lines.isEmpty()) {
			for (final TopicLine topicLine : lines) {
				topicLine.initUsersAvatars(topicRepository.findAvatarsTopicLine(UUID.fromString(topicLine.getId()), topicLine.getUserId(), 5));
			}
		}
		return topicRepository.findUserTopicLineList(autorId, userId, limit);
	}
	
}
