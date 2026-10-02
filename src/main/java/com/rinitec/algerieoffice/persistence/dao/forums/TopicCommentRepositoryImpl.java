package com.rinitec.algerieoffice.persistence.dao.forums;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicLikeComment;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.forums.TopicReply;

@Repository
public class TopicCommentRepositoryImpl implements TopicCommentRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllTopicCommentCriteria(final UUID topicId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("topicId"), topicId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNull(rootComment.get("parentUUID")));
		criteriaQuery.select(builder.count(rootComment)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<TopicReply> findAllTopicCommentCriteria(final Long userId, final UUID topicId, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicReply> criteriaQuery = builder.createQuery(TopicReply.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<TopicLikeComment> subrootLike = subqueryLike.from(TopicLikeComment.class);
		final Subquery<Long> subqueryReply = criteriaQuery.subquery(Long.class);
		final Root<TopicComment> subrootReply = subqueryReply.from(TopicComment.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<TopicLikeComment> subrootLiked = subqueryLiked.from(TopicLikeComment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("topicId"), topicId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNull(rootComment.get("parentUUID")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("commentId"), rootComment.get("id")));
		subqueryReply.select(builder.count(subrootReply)).where(builder.equal(subrootReply.get("parentUUID"), rootComment.get("id")));
		subqueryLiked.select(subrootLiked.get("userId")).where(builder.and(builder.equal(subrootLiked.get("commentId"), rootComment.get("id")), 
				builder.equal(subrootLiked.get("userId"), userId)));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> countReply = subqueryReply.getSelection();
		final Expression<Long> liked = subqueryLiked.getSelection();
		criteriaQuery.select(builder.construct(TopicReply.class, rootComment, rootUser, countLike, countReply, liked, rootComment.get("postedDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootComment.get("postedDate")));
		final TypedQuery<TopicReply> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<TopicReply> findAllTopicReplyCommentCriteria(final Long userId, final UUID commentId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicReply> criteriaQuery = builder.createQuery(TopicReply.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<TopicLikeComment> subrootLike = subqueryLike.from(TopicLikeComment.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<TopicLikeComment> subrootLiked = subqueryLiked.from(TopicLikeComment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("parentUUID"), commentId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("commentId"), rootComment.get("id")));
		subqueryLiked.select(subrootLiked.get("userId")).where(builder.and(builder.equal(subrootLiked.get("commentId"), rootComment.get("id")), 
				builder.equal(subrootLiked.get("userId"), userId)));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> liked = subqueryLiked.getSelection();
		criteriaQuery.select(builder.construct(TopicReply.class, rootComment, rootUser, countLike, liked, rootComment.get("postedDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootComment.get("postedDate")));
		final TypedQuery<TopicReply> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllUsersCommentTopic(final Long userId, final UUID topicId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootComment.get("userId"), userId));
		predicates.add(builder.equal(rootComment.get("topicId"), topicId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), rootUser.get("email"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllUsersCommentReply(final Long userId, final UUID parentId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootComment.get("userId"), userId));
		predicates.add(builder.equal(rootComment.get("parentUUID"), parentId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), rootUser.get("email"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

}
