package com.rinitec.algerieoffice.services.user.feedback;

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

import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.CommentLikeRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogLike;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityLike;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.CommentLike;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.user.repport.CommentForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsCommentLine;

@Service
public class LikeService implements ILikeService {

	private BlogRepository blogRepository;
	private BlogLikeRepository blogLikeRepository;
	private ActualityRepository actualityRepository;
	private ActualityLikeRepository actualityLikeRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private CommentLikeRepository commentLikeRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public LikeService(BlogRepository blogRepository, BlogLikeRepository blogLikeRepository, ActualityRepository actualityRepository, 
			ActualityLikeRepository actualityLikeRepository, ActualityCommentRepository actualityCommentRepository, 
			CommentLikeRepository commentLikeRepository, ActiveUserStore activeUserStore) {
		this.blogRepository = blogRepository;
		this.blogLikeRepository = blogLikeRepository;
		this.actualityRepository = actualityRepository;
		this.actualityLikeRepository = actualityLikeRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.commentLikeRepository = commentLikeRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasLikeBlog(final Long userId, final String blogId) {
		try {
			return blogLikeRepository.existsByUserIdAndBlogId(userId, UUID.fromString(blogId));
		} catch (IllegalArgumentException e) {}
		return false;
	}
	
	@Transactional
	private final BlogLike addOrDeleteBlogLike(final Long userId, final UUID blogId) {
		BlogLike blogLike = blogLikeRepository.findByUserIdAndBlogId(userId, blogId);
		if(blogLike == null) {
			blogLike = new BlogLike(userId, blogId);
			return blogLikeRepository.save(blogLike);
		} else {
			blogLikeRepository.delete(blogLike);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Blog postOrRemoveBlogLike(final Long userId, final String blogId) {
		try {
			final Optional<Blog> uOptional = blogRepository.findById(UUID.fromString(blogId));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Blog blog = uOptional.get();
			final BlogLike blogLike = addOrDeleteBlogLike(userId, blog.getId());
			if(blogLike != null) {
				return blog;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasLikeActu(final Long userId, final String actualityId) {
		try {
			return actualityLikeRepository.existsByUserIdAndActualityId(userId, UUID.fromString(actualityId));
		} catch (IllegalArgumentException e) {}
		return false;
	}
	
	@Transactional
	private final Actuality updateSharedActuality(final Actuality actuality) {
		actuality.setSharedDate(new DateTime(Date.from(Instant.now())));
		return actualityRepository.save(actuality);
	}
	
	private final ActualityLike addOrDeleteActualityLike(final Long userId, final UUID actuId) {
		ActualityLike actualityLike = actualityLikeRepository.findByUserIdAndActualityId(userId, actuId);
		if(actualityLike == null) {
			actualityLike = new ActualityLike(userId, actuId);
			return actualityLikeRepository.save(actualityLike);
		} else {
			actualityLikeRepository.delete(actualityLike);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Actuality postOrRemoveActualityLike(final Long userId, final String actualityId) {
		try {
			final Optional<Actuality> uOptional = actualityRepository.findById(UUID.fromString(actualityId));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Actuality actuality = uOptional.get();
			final ActualityLike actualityLike = addOrDeleteActualityLike(userId, actuality.getId());
			if(actualityLike != null) {
				return updateSharedActuality(actuality);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final CommentLike addOrDeleteCommentLike(final Long userId, final UUID commentId) {
		CommentLike commentLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId);
		if(commentLike == null) {
			commentLike = new CommentLike(userId, commentId);
			return commentLikeRepository.save(commentLike);
		} else {
			commentLikeRepository.delete(commentLike);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Object[] postOrRemoveCommentLike(final Long userId, final String commentId) {
		try {
			final Optional<ActualityComment> uOptional = actualityCommentRepository.findById(UUID.fromString(commentId));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final ActualityComment actualityComment = uOptional.get();
			final CommentLike commentLike = addOrDeleteCommentLike(userId, actualityComment.getId());
			if(commentLike != null) {
				final Optional<Long> autorOptional = actualityRepository.findAutorIdByActualityId(actualityComment.getActualityId());
				return new Object[] {actualityComment, autorOptional.isPresent() ? autorOptional.get() : null};
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final ActualityComment postActualityComment(final UUID actualityId, final CommentForm commentForm) {
		final ActualityComment actualityComment = new ActualityComment();
		actualityComment.setUserId(commentForm.getUserId());
		actualityComment.setActualityId(actualityId);
		actualityComment.setMessage(commentForm.getMessage());
		actualityComment.setPostedDate(new DateTime(Date.from(Instant.now())));
		return actualityCommentRepository.save(actualityComment);
	}
	
	@Override
	@Transactional
	public Object[] addActualityComment(final CommentForm commentForm) {
		try {
			final UUID actuId = UUID.fromString(commentForm.getActualityId());
			final Optional<Actuality> uOptional = actualityRepository.findById(actuId);
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final ActualityComment actualityComment = postActualityComment(actuId, commentForm);
			final Actuality actuality = updateSharedActuality(uOptional.get());
			return new Object[] {actuality, actualityComment.getId().toString()};
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findActualityCommentList(final Long userId, final String actuId, final int page, final int rows) {
		try {
			final UUID actuUUID = UUID.fromString(actuId);
			final Long countResult = page == 1 ? actualityCommentRepository.countAllActualityComments(actuUUID) : 0L;
			final List<NewsCommentLine> lines = (page == 1 && countResult == 0L) ? new ArrayList<NewsCommentLine>() 
				: actualityCommentRepository.findAllActualityComments(userId, actuUUID, page, rows);
			if(!lines.isEmpty()) {
				for (final NewsCommentLine line : lines) {
					final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
					line.updateOnline(hasOnline);
				}
			}
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {}
		return new ElementsList(0L, new ArrayList<NewsCommentLine>());
	}
	
}
