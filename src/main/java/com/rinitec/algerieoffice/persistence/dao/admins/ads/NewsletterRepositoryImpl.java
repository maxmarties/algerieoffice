package com.rinitec.algerieoffice.persistence.dao.admins.ads;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmNewsletterLine;

@Repository
public class NewsletterRepositoryImpl implements NewsletterRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllNewsletterAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Newsletter> root = criteriaQuery.from(Newsletter.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(root.get("suscribeDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(root.get("email")), "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmNewsletterLine> findAllNewsletterAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmNewsletterLine> criteriaQuery = builder.createQuery(AdmNewsletterLine.class);
		final Root<Newsletter> root = criteriaQuery.from(Newsletter.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(root.get("suscribeDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(root.get("email")), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("suscribeDate")) : builder.asc(root.get("suscribeDate")); break;
		case 2: order = hasDesc ? builder.desc(root.get("email")) : builder.asc(root.get("email")); break;
		default: order = hasDesc ? builder.desc(root.get("enabled")) : builder.asc(root.get("enabled"));
		}
		criteriaQuery.select(builder.construct(AdmNewsletterLine.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmNewsletterLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<String> findAllNewsletter(final List<String> users, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		final Root<Newsletter> root = criteriaQuery.from(Newsletter.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		if(!users.isEmpty()) {
			predicates.add(builder.not(root.get("email").in(users)));
		}
		criteriaQuery.select(root.get("email")).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("suscribeDate")));
		final TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
