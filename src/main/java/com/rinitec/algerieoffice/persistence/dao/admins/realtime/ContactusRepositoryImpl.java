package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmContactLine;

@Repository
public class ContactusRepositoryImpl implements ContactusRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllContactusCriteria(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Contactus> root = criteriaQuery.from(Contactus.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(filter ? builder.isNull(root.get("userId")) : builder.isNotNull(root.get("userId")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		if(!predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		} else {
			criteriaQuery.select(builder.count(root));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmContactLine> findAllContactusCriteria(final Boolean filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmContactLine> criteriaQuery = builder.createQuery(AdmContactLine.class);
		final Root<Contactus> root = criteriaQuery.from(Contactus.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		if(filter != null) {
			predicates.add(filter ? builder.isNull(root.get("userId")) : builder.isNotNull(root.get("userId")));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("postedDate")) : builder.asc(root.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		case 3: order = hasDesc ? builder.desc(root.get("object")) : builder.asc(root.get("object")); break;
		default: order = hasDesc ? builder.desc(root.get("consulted")) : builder.asc(root.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmContactLine.class, root));
		if(!predicates.isEmpty()) {
			criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}
		criteriaQuery.orderBy(order);
		final TypedQuery<AdmContactLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
