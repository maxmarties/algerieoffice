package com.rinitec.algerieoffice.persistence.dao.company.posts;

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

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.result.CategoryMini;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.modal.company.posts.CategoryLine;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public List<UUIDMini> findAllChoseCategoryMini(final Long companyId, final UUID categoryId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isNull(root.get("parentUUID")));
		if(categoryId != null) {
			predicates.add(builder.notEqual(root.get("id"), categoryId));
		}
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("name")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("name")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllFilterCategoryMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
		final Root<Category> subroot = subquery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(subroot.get("companyId"), companyId));
		predicates.add(builder.isNotNull(subroot.get("parentUUID")));
		subquery.select(subroot.get("parentUUID")).distinct(true).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("name")));
		criteriaQuery.where(builder.in(root.get("id")).value(subquery)).orderBy(builder.asc(root.get("name")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllCategoryMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("name")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("name")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	private final UUID parseFilterUUID(final String filter) {
		try {
			return UUID.fromString(filter);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	public Long countAllCategoryCriteria(final Long companyId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(root.get("parentUUID")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(root.get("parentUUID"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(root.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<CategoryLine> findAllCategoryCriteria(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CategoryLine> criteriaQuery = builder.createQuery(CategoryLine.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final Subquery<String> subqueryCategory = criteriaQuery.subquery(String.class);
		final Root<Category> subrootCategory = subqueryCategory.from(Category.class);
		final Subquery<Long> subqueryPost = criteriaQuery.subquery(Long.class);
		final Root<Post> subrootPost = subqueryPost.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(root.get("parentUUID")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(root.get("parentUUID"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(root.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		subqueryCategory.select(subrootCategory.get("name")).where(builder.equal(subrootCategory.get("id"), root.get("parentUUID")));
		final Expression<String> parentname = subqueryCategory.getSelection();
		subqueryPost.select(builder.count(subrootPost)).where(builder.equal(subrootPost.get("categoryId"), root.get("id")));
		final Expression<Long> countPost = subqueryPost.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("name")) : builder.asc(root.get("name")); break;
		default: order = hasDesc ? builder.desc(root.get("parentUUID")) : builder.asc(root.get("parentUUID"));
		}
		criteriaQuery.select(builder.construct(CategoryLine.class, root, parentname, countPost));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<CategoryLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<CategoryMini> findAllExplorerCategory(final Long companyId, final UUID parentUUID) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CategoryMini> criteriaQuery = builder.createQuery(CategoryMini.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Post> subroot = subquery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> subpredicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(parentUUID == null) {
			predicates.add(builder.isNull(root.get("parentUUID")));
		} else {
			predicates.add(builder.equal(root.get("parentUUID"), parentUUID));
		}
		subpredicates.add(builder.isFalse(subroot.get("hasTrashed")));
		subpredicates.add(builder.isTrue(subroot.get("hasPublished")));
		subpredicates.add(builder.equal(subroot.get("categoryId"), root.get("id")));
		subquery.select(builder.count(subroot)).where(subpredicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(CategoryMini.class, root.get("id"), root.get("name"), subquery.getSelection()));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("name")));
		final TypedQuery<CategoryMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public UUIDMini findExplorerCategory(final Long companyId, final String identify) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.equal(root.get("identify"), identify));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("name")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public List<PostMini> findAllPingledCategoryMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostMini> criteriaQuery = builder.createQuery(PostMini.class);
		final Root<Category> root = criteriaQuery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPingled")));
		criteriaQuery.select(builder.construct(PostMini.class, root.get("name"), root.get("identify")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("name")));
		final TypedQuery<PostMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(10);
		return query.getResultList();
	}
	
}
