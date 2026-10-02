package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

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
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.CommentLike;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsCommentLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserCommentLine;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardComment;

@Repository
public class ActualityCommentRepositoryImpl implements ActualityCommentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<UserMini> findAllUsersComment(final Long userId, final UUID actuId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<ActualityComment> rootComment = criteriaQuery.from(ActualityComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootComment.get("userId"), userId));
		predicates.add(builder.equal(rootComment.get("actualityId"), actuId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), rootUser.get("email"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActualityComments(final UUID actuId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<ActualityComment> rootComment = criteriaQuery.from(ActualityComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("actualityId"), actuId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		criteriaQuery.select(builder.count(rootComment)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<NewsCommentLine> findAllActualityComments(final Long userId, final UUID actuId, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NewsCommentLine> criteriaQuery = builder.createQuery(NewsCommentLine.class);
		final Root<ActualityComment> rootComment = criteriaQuery.from(ActualityComment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<CommentLike> subrootLike = subqueryLike.from(CommentLike.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<CommentLike> subrootLiked = subqueryLiked.from(CommentLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("actualityId"), actuId));
		predicates.add(builder.equal(rootComment.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("commentId"), rootComment.get("id")));
		subqueryLiked.select(subrootLiked.get("userId")).where(builder.and(builder.equal(subrootLiked.get("commentId"), rootComment.get("id")), 
				builder.equal(subrootLiked.get("userId"), userId)));
		final Expression<String> tradename = subqueryCompany.getSelection();
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> liked = subqueryLiked.getSelection();
		criteriaQuery.select(builder.construct(NewsCommentLine.class, rootComment, rootUser, tradename, countLike, liked, rootComment.get("postedDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootComment.get("postedDate")));
		final TypedQuery<NewsCommentLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActualityCommentCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<ActualityComment> rootComment = criteriaQuery.from(ActualityComment.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("userId"), userId));
		predicates.add(builder.equal(rootComment.get("actualityId"), rootActuality.get("id")));
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.between(rootComment.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootActuality.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootComment)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UserCommentLine> findAllActualityCommentCriteria(final Long userId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserCommentLine> criteriaQuery = builder.createQuery(UserCommentLine.class);
		final Root<ActualityComment> rootComment = criteriaQuery.from(ActualityComment.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<CommentLike> subrootLike = subqueryLike.from(CommentLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("userId"), userId));
		predicates.add(builder.equal(rootComment.get("actualityId"), rootActuality.get("id")));
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.between(rootComment.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootActuality.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootCompany.get("id")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("commentId"), rootComment.get("id")));
		final Expression<String> url = subquerySeo.getSelection();
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootComment.get("postedDate")) : builder.asc(rootComment.get("postedDate")); break;
		default: order = hasDesc ? builder.desc(rootActuality.get("title")) : builder.asc(rootActuality.get("title"));
		}
		criteriaQuery.select(builder.construct(UserCommentLine.class, rootComment, rootCompany, url, rootActuality.get("title"), countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<UserCommentLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<DashboardComment> findLastDashboardComment(final Long userId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DashboardComment> criteriaQuery = builder.createQuery(DashboardComment.class);
		final Root<ActualityComment> rootComment = criteriaQuery.from(ActualityComment.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootComment.get("userId"), userId));
		predicates.add(builder.equal(rootComment.get("actualityId"), rootActuality.get("id")));
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(DashboardComment.class, rootComment));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootComment.get("postedDate")));
		final TypedQuery<DashboardComment> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
