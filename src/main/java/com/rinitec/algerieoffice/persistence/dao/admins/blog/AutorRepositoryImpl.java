package com.rinitec.algerieoffice.persistence.dao.admins.blog;

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

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.blog.AutorLine;

@Repository
public class AutorRepositoryImpl implements AutorRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllAutorCriteria(final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Autor> root = criteriaQuery.from(Autor.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> autorname = builder.lower(root.get("autorname"));
			predicates.add(builder.like(autorname, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AutorLine> findAllAutorCriteria(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AutorLine> criteriaQuery = builder.createQuery(AutorLine.class);
		final Root<Autor> root = criteriaQuery.from(Autor.class);
		final Subquery<Long> subqueryBlog = criteriaQuery.subquery(Long.class);
		final Root<Blog> subrootBlog = subqueryBlog.from(Blog.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> autorname = builder.lower(root.get("autorname"));
			predicates.add(builder.like(autorname, "%" + search.toLowerCase() + "%"));
		}
		subqueryBlog.select(builder.count(subrootBlog)).where(builder.equal(subrootBlog.get("autorId"), root.get("id")));
		final Expression<Long> countBlog = subqueryBlog.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("autorname")) : builder.asc(root.get("autorname")); break;
		default: order = hasDesc ? builder.desc(root.get("function")) : builder.asc(root.get("function"));
		}
		criteriaQuery.select(builder.construct(AutorLine.class, root, countBlog));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AutorLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllAutors() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<Autor> root = criteriaQuery.from(Autor.class);
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), root.get("autorname"))).orderBy(builder.asc(root.get("autorname")));;
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
