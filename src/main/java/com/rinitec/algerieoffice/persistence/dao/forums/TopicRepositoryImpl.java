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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicLike;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicMark;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.forums.SearchForumForm;
import com.rinitec.algerieoffice.web.modal.forums.TopicInbox;
import com.rinitec.algerieoffice.web.modal.forums.TopicLine;
import com.rinitec.algerieoffice.web.modal.forums.TopicMember;
import com.rinitec.algerieoffice.web.modal.forums.TopicMini;

@Repository
public class TopicRepositoryImpl implements TopicRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public TopicInbox findOneTopicInbox(final UUID topicId, final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicInbox> criteriaQuery = builder.createQuery(TopicInbox.class);
		final Root<Topic> rootTopic = criteriaQuery.from(Topic.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<TopicLike> subrootLike = subqueryLike.from(TopicLike.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<TopicComment> subrootComment = subqueryComment.from(TopicComment.class);
		final Subquery<Long> subqueryUsers = criteriaQuery.subquery(Long.class);
		final Root<TopicComment> subrootUsers = subqueryUsers.from(TopicComment.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<TopicLike> subrootLiked = subqueryLiked.from(TopicLike.class);
		final Subquery<Long> subqueryMarked = criteriaQuery.subquery(Long.class);
		final Root<TopicMark> subrootMarked = subqueryMarked.from(TopicMark.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootTopic.get("id"), topicId));
		predicates.add(builder.equal(rootTopic.get("userId"), rootUser.get("id")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("topicId"), rootTopic.get("id")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("topicId"), rootTopic.get("id")));
		subqueryUsers.select(builder.countDistinct(subrootUsers.get("userId"))).where(builder.equal(subrootUsers.get("topicId"), rootTopic.get("id")));
		subqueryLiked.select(subrootLiked.get("userId")).where(builder.and(builder.equal(subrootLiked.get("topicId"), rootTopic.get("id")), builder.equal(subrootLiked.get("userId"), userId)));
		subqueryMarked.select(subrootMarked.get("userId")).where(builder.and(builder.equal(subrootMarked.get("topicId"), rootTopic.get("id")), builder.equal(subrootMarked.get("userId"), userId)));
		final Expression<Long> likeCount = subqueryLike.getSelection();
		final Expression<Long> commentCount = subqueryComment.getSelection();
		final Expression<Long> userCount = subqueryUsers.getSelection();
		final Expression<Long> likedId = subqueryLiked.getSelection();
		final Expression<Long> markedId = subqueryMarked.getSelection();
		criteriaQuery.select(builder.construct(TopicInbox.class, rootTopic, rootUser, likeCount, commentCount, userCount, likedId, markedId));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<TopicInbox> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public TopicMember findTopicMember(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicMember> criteriaQuery = builder.createQuery(TopicMember.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootUser.get("id"), userId));
		predicates.add(builder.isNotNull(rootUser.get("companyId")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		criteriaQuery.select(builder.construct(TopicMember.class, rootUser, rootAccount, rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<TopicMember> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public List<Long> findAvatarsTopicLine(final UUID topicId, final Long autorId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("topicId"), topicId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		predicates.add(builder.notEqual(rootUser.get("id"), autorId));
		predicates.add(builder.isTrue(rootUser.get("hasAvatar")));
		criteriaQuery.select(rootUser.get("id")).where(predicates.toArray(new Predicate[0])).distinct(true);
		final TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllTopicLine(final SearchForumForm searchForumForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Topic> rootTopic = criteriaQuery.from(Topic.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootTopic.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		if(!StringUtils.isEmpty(searchForumForm.getToken())) {
			final Expression<String> title = builder.lower(rootTopic.get("title"));
			predicates.add(builder.like(title, "%" + searchForumForm.getToken().toLowerCase() + "%"));
		}
		if(searchForumForm.hasPresentFilter()) {
			predicates.add(builder.between(rootTopic.get("createdDate"), ParseUtil.getBeginDate(searchForumForm.getFilter()), ParseUtil.getEndDate(searchForumForm.getFilter())));
		}
		if(searchForumForm.hasPresentCategory()) {
			predicates.add(builder.equal(rootTopic.get("category"), searchForumForm.getCategory()));
		}
		if(searchForumForm.hasPresentTabulation()) {
			switch(searchForumForm.getTabulation()) {
			case 1: 
				predicates.add(builder.equal(rootTopic.get("userId"), searchForumForm.getUserId())); break;
			case 2: 
				final Root<TopicMark> rootMark = criteriaQuery.from(TopicMark.class);
				predicates.add(builder.equal(rootMark.get("topicId"), rootTopic.get("id")));
				predicates.add(builder.equal(rootMark.get("userId"), searchForumForm.getUserId()));
				break;
			case 3:
				predicates.add(builder.isTrue(rootTopic.get("hasQuiz")));
			}
		}
		criteriaQuery.select(builder.countDistinct(rootTopic)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<TopicLine> findTopicLineList(final SearchForumForm searchForumForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicLine> criteriaQuery = builder.createQuery(TopicLine.class);
		final Root<Topic> rootTopic = criteriaQuery.from(Topic.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<TopicComment> subrootComment = subqueryComment.from(TopicComment.class);
		final Subquery<Long> subqueryMark = criteriaQuery.subquery(Long.class);
		final Root<TopicMark> subrootMark = subqueryMark.from(TopicMark.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootTopic.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		if(!StringUtils.isEmpty(searchForumForm.getToken())) {
			final Expression<String> title = builder.lower(rootTopic.get("title"));
			predicates.add(builder.like(title, "%" + searchForumForm.getToken().toLowerCase() + "%"));
		}
		if(searchForumForm.hasPresentFilter()) {
			predicates.add(builder.between(rootTopic.get("createdDate"), ParseUtil.getBeginDate(searchForumForm.getFilter()), ParseUtil.getEndDate(searchForumForm.getFilter())));
		}
		if(searchForumForm.hasPresentCategory()) {
			predicates.add(builder.equal(rootTopic.get("category"), searchForumForm.getCategory()));
		}
		if(searchForumForm.hasPresentTabulation()) {
			switch(searchForumForm.getTabulation()) {
			case 1: 
				predicates.add(builder.equal(rootTopic.get("userId"), searchForumForm.getUserId())); break;
			case 2: 
				final Root<TopicMark> rootMark = criteriaQuery.from(TopicMark.class);
				predicates.add(builder.equal(rootMark.get("topicId"), rootTopic.get("id")));
				predicates.add(builder.equal(rootMark.get("userId"), searchForumForm.getUserId()));
				break;
			case 3:
				predicates.add(builder.isTrue(rootTopic.get("hasQuiz")));
			}
		}
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("topicId"), rootTopic.get("id")));
		subqueryMark.select(subrootMark.get("userId")).where(builder.and(builder.equal(subrootMark.get("topicId"), rootTopic.get("id")), 
				builder.equal(subrootMark.get("userId"), searchForumForm.getUserId())));
		final Expression<Long> commentCount = subqueryComment.getSelection();
		final Expression<Long> markedId = subqueryMark.getSelection();
		final Order order;
		switch (searchForumForm.getSort()) {
		case 1: order = searchForumForm.isDesc() ? builder.desc(rootTopic.get("createdDate")) : builder.asc(rootTopic.get("createdDate")); break;
		case 2: order = searchForumForm.isDesc() ? builder.desc(rootTopic.get("viewCount")) : builder.asc(rootTopic.get("viewCount")); break;
		case 3: order = searchForumForm.isDesc() ? builder.desc(rootTopic.get("modifiedDate")) : builder.asc(rootTopic.get("modifiedDate")); break;
		default: order = searchForumForm.isDesc() ? builder.desc(rootTopic.get("title")) : builder.asc(rootTopic.get("title"));
		}
		criteriaQuery.select(builder.construct(TopicLine.class, rootUser, rootTopic, commentCount, markedId, rootTopic.get("createdDate"), 
				rootTopic.get("viewCount"), rootTopic.get("modifiedDate"), rootTopic.get("title"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<TopicLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchForumForm.getPage() - 1) * searchForumForm.getRow()).setMaxResults(searchForumForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<TopicLine> findUserTopicLineList(final Long autorId, final Long userId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicLine> criteriaQuery = builder.createQuery(TopicLine.class);
		final Root<Topic> rootTopic = criteriaQuery.from(Topic.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<TopicComment> subrootComment = subqueryComment.from(TopicComment.class);
		final Subquery<Long> subqueryMark = criteriaQuery.subquery(Long.class);
		final Root<TopicMark> subrootMark = subqueryMark.from(TopicMark.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootTopic.get("userId"), autorId));
		predicates.add(builder.equal(rootUser.get("id"), autorId));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("topicId"), rootTopic.get("id")));
		subqueryMark.select(subrootMark.get("userId")).where(builder.and(builder.equal(subrootMark.get("topicId"), rootTopic.get("id")), 
				builder.equal(subrootMark.get("userId"), userId)));
		final Expression<Long> commentCount = subqueryComment.getSelection();
		final Expression<Long> markedId = subqueryMark.getSelection();
		criteriaQuery.select(builder.construct(TopicLine.class, rootUser, rootTopic, commentCount, markedId));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootTopic.get("createdDate")));
		final TypedQuery<TopicLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<TopicMini> findTopicMiniList(final UUID topicId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicMini> criteriaQuery = builder.createQuery(TopicMini.class);
		final Root<Topic> rootTopic = criteriaQuery.from(Topic.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<TopicComment> subrootComment = subqueryComment.from(TopicComment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootTopic.get("id"), topicId));
		predicates.add(builder.equal(rootTopic.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("topicId"), rootTopic.get("id")));
		final Expression<Long> commentCount = subqueryComment.getSelection();
		criteriaQuery.select(builder.construct(TopicMini.class, rootTopic, commentCount));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootTopic.get("createdDate")));
		final TypedQuery<TopicMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<TopicMini> findLastTopicMiniList(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TopicMini> criteriaQuery = builder.createQuery(TopicMini.class);
		final Root<Topic> rootTopic = criteriaQuery.from(Topic.class);
		final Root<TopicComment> rootComment = criteriaQuery.from(TopicComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootTopic.get("id"), rootComment.get("topicId")));
		predicates.add(builder.equal(rootTopic.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		criteriaQuery.select(builder.construct(TopicMini.class, rootTopic, builder.count(rootComment.get("id")))).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.groupBy(rootTopic).orderBy(builder.desc(builder.count(rootComment.get("id"))), builder.desc(rootTopic.get("createdDate")));
		final TypedQuery<TopicMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
