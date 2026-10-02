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
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.dao.forums.TopicCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicMarkRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicSignalRepository;
import com.rinitec.algerieoffice.persistence.dao.forums.TopicVoteRepository;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicLike;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicLikeComment;
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
import com.rinitec.algerieoffice.web.modal.forums.TopicLine;
import com.rinitec.algerieoffice.web.modal.forums.TopicMini;
import com.rinitec.algerieoffice.web.modal.forums.TopicReply;

@Service
public class TopicExplorerService implements ITopicExplorerService {

	private TopicRepository topicRepository;
	private TopicLikeRepository topicLikeRepository; 
	private TopicCommentRepository topicCommentRepository;
	private TopicLikeCommentRepository topicLikeCommentRepository;
	private TopicMarkRepository topicMarkRepository;
	private TopicSignalRepository topicSignalRepository;
	private TopicVoteRepository topicVoteRepository;
	
	@Autowired
	public TopicExplorerService(TopicRepository topicRepository, TopicLikeRepository topicLikeRepository, TopicCommentRepository topicCommentRepository, 
			TopicLikeCommentRepository topicLikeCommentRepository, TopicMarkRepository topicMarkRepository, TopicSignalRepository topicSignalRepository, 
			TopicVoteRepository topicVoteRepository) {
		this.topicRepository = topicRepository;
		this.topicLikeRepository = topicLikeRepository;
		this.topicCommentRepository = topicCommentRepository;
		this.topicLikeCommentRepository = topicLikeCommentRepository;
		this.topicMarkRepository = topicMarkRepository;
		this.topicSignalRepository = topicSignalRepository;
		this.topicVoteRepository = topicVoteRepository;
	}
	
	@Override
	@Transactional
	public TopicVote addTopicVote(final TopicQuizResponse topicQuizResponse) {
		try {
			final UUID topicId = UUID.fromString(topicQuizResponse.getTopicId());
			if(!topicRepository.existsById(topicId)) {
				throw new NotFoundException("message.error.notfound");
			}
			final TopicVote topicVote = new TopicVote(topicQuizResponse.getUserId(), topicId);
			topicVote.setQuiz(topicQuizResponse.getQuiz());
			topicVote.setPostedDate(new DateTime(Date.from(Instant.now())));
			return topicVoteRepository.save(topicVote);
		} catch (IllegalArgumentException e) {
			throw new AccessAuthorityException();
		}
	}
	
	@Transactional
	private final TopicComment postTopicComment(final UUID topicId, final TopicReplyResponse topicReplyResponse) {
		final TopicComment topicComment = new TopicComment();
		topicComment.setUserId(topicReplyResponse.getUserId());
		topicComment.setTopicId(topicId);
		topicComment.setMessage(topicReplyResponse.getMessage().getBytes());
		topicComment.setParentUUID(!StringUtils.isEmpty(topicReplyResponse.getParentId()) ? UUID.fromString(topicReplyResponse.getParentId()) : null);
		topicComment.setPostedDate(new DateTime(Date.from(Instant.now())));
		return topicCommentRepository.save(topicComment);
	}
	
	@Override
	@Transactional
	public Object[] addTopicComment(final TopicReplyResponse topicReplyResponse) {
		try {
			final UUID topicId = UUID.fromString(topicReplyResponse.getTopicId());
			final Optional<Topic> uOptional = topicRepository.findById(topicId);
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final TopicComment topicComment = postTopicComment(topicId, topicReplyResponse);
			return new Object[] {uOptional.get(), topicComment};
		} catch (IllegalArgumentException e) {
			throw new AccessAuthorityException();
		}
	}
	
	@Transactional
	private final TopicLike addOrDeleteTopicLike(final Long userId, final UUID topicId) {
		TopicLike topicLike = topicLikeRepository.findByUserIdAndTopicId(userId, topicId);
		if(topicLike == null) {
			topicLike = new TopicLike(userId, topicId);
			return topicLikeRepository.save(topicLike);
		} else {
			topicLikeRepository.delete(topicLike);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Topic postOrRemoveTopicLike(final Long userId, final String topicId) {
		try {
			final Optional<Topic> uOptional = topicRepository.findById(UUID.fromString(topicId));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Topic topic = uOptional.get();
			final TopicLike topicLike = addOrDeleteTopicLike(userId, topic.getId());
			return topicLike != null ? topic : null;
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final TopicMark addOrDeleteTopicMark(final Long userId, final UUID topicId) {
		TopicMark topicMark = topicMarkRepository.findByUserIdAndTopicId(userId, topicId);
		if(topicMark == null) {
			topicMark = new TopicMark(userId, topicId);
			return topicMarkRepository.save(topicMark);
		} else {
			topicMarkRepository.delete(topicMark);
		}
		return null;
	}
	
	@Override
	@Transactional
	public TopicMark postOrRemoveTopicMark(final Long userId, final String topicId) {
		try {
			final UUID topicUUID = UUID.fromString(topicId);
			if(!topicRepository.existsById(topicUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			return addOrDeleteTopicMark(userId, topicUUID);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final TopicLikeComment addOrDeleteTopicLikeComment(final Long userId, final UUID commentId) {
		TopicLikeComment topicLikeComment = topicLikeCommentRepository.findByUserIdAndCommentId(userId, commentId);
		if(topicLikeComment == null) {
			topicLikeComment = new TopicLikeComment(userId, commentId);
			return topicLikeCommentRepository.save(topicLikeComment);
		} else {
			topicLikeCommentRepository.delete(topicLikeComment);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Object[] postOrRemoveCommentLike(final Long userId, final String commentId) {
		try {
			final Optional<TopicComment> uOptional = topicCommentRepository.findById(UUID.fromString(commentId));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final TopicComment topicComment = uOptional.get();
			final TopicLikeComment topicLikeComment = addOrDeleteTopicLikeComment(userId, topicComment.getId());
			if(topicLikeComment != null) {
				final Optional<Long> autorOptional = topicRepository.findAutorIdByTopicId(topicComment.getTopicId());
				return new Object[] {topicComment, autorOptional.isPresent() ? autorOptional.get() : null};
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public TopicSignal addTopicSignal(final Long userId, final SignalForm signalForm) {
		try {
			final TopicSignal topicSignal = new TopicSignal();
			topicSignal.setUserId(userId);
			topicSignal.setType(signalForm.getTypeSignal());
			topicSignal.setReason(!StringUtils.isEmpty(signalForm.getReasonSignal()) ? signalForm.getReasonSignal() : null);
			topicSignal.setTopicId(UUID.fromString(signalForm.getTopicSignal()));
			topicSignal.setCommentId(!StringUtils.isEmpty(signalForm.getCommentSignal()) ? UUID.fromString(signalForm.getCommentSignal()) : null);
			topicSignal.setPostedDate(new DateTime(Date.from(Instant.now())));
			return topicSignalRepository.save(topicSignal);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public TopicComment deleteTopicComment(final Long userId, final String commentId) {
		try {
			final Optional<TopicComment> uOptional = topicCommentRepository.findById(UUID.fromString(commentId));
			if(!uOptional.isPresent() || !uOptional.get().getUserId().equals(userId)) {
				throw new NotFoundException("message.error.notfound");
			}
			final TopicComment topicComment = uOptional.get();
			topicCommentRepository.deleteByParentUUID(topicComment.getId());
			topicCommentRepository.delete(topicComment);
			return topicComment;
		} catch (IllegalArgumentException e) {
			throw new AccessAuthorityException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findTopicCommentList(final Long userId, final String topicId, final int page, final int rows) {
		try {
			final UUID topicUUID = UUID.fromString(topicId);
			final Long countResult = page == 1 ? topicCommentRepository.countAllTopicCommentCriteria(topicUUID) : 0L;
			final List<TopicReply> lines = (page == 1 && countResult == 0L) ? new ArrayList<TopicReply>() 
				: topicCommentRepository.findAllTopicCommentCriteria(userId, topicUUID, page, rows);
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {}
		return new ElementsList(0L, new ArrayList<TopicReply>());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TopicReply> findTopicRelpyCommentList(final Long userId, final String commentId, final int limit) {
		try {
			return topicCommentRepository.findAllTopicReplyCommentCriteria(userId, UUID.fromString(commentId), limit);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findTopicsList(final SearchForumForm searchForumForm) {
		final Long countResult = topicRepository.countAllTopicLine(searchForumForm);
		final List<TopicLine> lines = countResult == 0L ? new ArrayList<TopicLine>() : topicRepository.findTopicLineList(searchForumForm);
		if(!lines.isEmpty()) {
			for (final TopicLine topicLine : lines) {
				topicLine.initUsersAvatars(topicRepository.findAvatarsTopicLine(UUID.fromString(topicLine.getId()), topicLine.getUserId(), 5));
			}
		}
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TopicMini> findTopicMiniList(final String topicId, final int limit) {
		try {
			final List<TopicMini> lines = topicRepository.findTopicMiniList(UUID.fromString(topicId), limit);
			if(!lines.isEmpty()) {
				for (final TopicMini topicMini : lines) {
					topicMini.initUsersAvatars(topicRepository.findAvatarsTopicLine(UUID.fromString(topicMini.getId()), topicMini.getUserId(), 3));
				}
			}
			return lines;
		} catch (IllegalArgumentException e) {}
		return new ArrayList<TopicMini>();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TopicMini> findLastTopicMiniList(final int limit) {
		final List<TopicMini> lines = topicRepository.findLastTopicMiniList(limit);
		if(!lines.isEmpty()) {
			for (final TopicMini topicMini : lines) {
				topicMini.initUsersAvatars(topicRepository.findAvatarsTopicLine(UUID.fromString(topicMini.getId()), topicMini.getUserId(), 3));
			}
		}
		return lines;
	}
	
}
