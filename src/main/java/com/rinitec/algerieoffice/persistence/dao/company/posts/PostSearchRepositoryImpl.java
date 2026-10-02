package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.ArrayList;
import java.util.List;

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

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostSearch;
import com.rinitec.algerieoffice.web.modal.company.posts.PostAccess;
import com.rinitec.algerieoffice.web.modal.company.posts.PostStatLine;

@Repository
public class PostSearchRepositoryImpl implements PostSearchRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllPostStatCriteria(final Long companyId, final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostSearch> rootSearch = criteriaQuery.from(PostSearch.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPost.get("id"), rootSearch.get("id")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(rootPost.get("service"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootPost)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PostStatLine> findAllPostStatCriteria(final Long companyId, final Boolean filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostStatLine> criteriaQuery = builder.createQuery(PostStatLine.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostSearch> rootSearch = criteriaQuery.from(PostSearch.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPost.get("id"), rootSearch.get("id")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(rootPost.get("service"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootSearch.get("clickCount")) : builder.asc(rootSearch.get("clickCount")); break;
		case 2: order = hasDesc ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootPost.get("title")) : builder.asc(rootPost.get("title"));
		}
		criteriaQuery.select(builder.construct(PostStatLine.class, rootPost, rootSearch));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PostStatLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<PostAccess> findPostAccessList(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostAccess> criteriaQuery = builder.createQuery(PostAccess.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostPhoto> rootPhoto = criteriaQuery.from(PostPhoto.class);
		final Root<PostSearch> rootSearch = criteriaQuery.from(PostSearch.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<Category> subroot = subquery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPhoto.get("postUUID"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("id"), rootSearch.get("id")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPhoto.get("hasPrincipal")));
		predicates.add(builder.greaterThan(rootSearch.get("view"), 0L));
		subquery.select(subroot.get("name")).where(builder.equal(subroot.get("id"), rootPost.get("categoryId")));
		final Expression<String> category = subquery.getSelection();
		criteriaQuery.select(builder.construct(PostAccess.class, rootSearch, rootPost, rootPhoto.get("photoUUID"), category));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootSearch.get("view")));
		final TypedQuery<PostAccess> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countPostStatistic(final Long companyId, final String attribut, final boolean service) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostSearch> rootSearch = criteriaQuery.from(PostSearch.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPost.get("id"), rootSearch.get("id")));
		predicates.add(builder.equal(rootPost.get("service"), service));
		criteriaQuery.select(builder.sumAsLong(rootSearch.get(attribut))).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
