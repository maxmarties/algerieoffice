package com.rinitec.algerieoffice.persistence.dao.inbox;

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

import com.rinitec.algerieoffice.persistence.modal.inbox.Talk;
import com.rinitec.algerieoffice.web.modal.user.feedback.TalkLine;

@Repository
public class TalkRepositoryImpl implements TalkRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllTalkCriteria(final Long companyId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Talk> root = criteriaQuery.from(Talk.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			switch(filter) {
			case 1: predicates.add(builder.or(builder.between(root.get("type"), 1, 6), builder.equal(root.get("type"), 9))); break;
			case 2: predicates.add(builder.between(root.get("type"), 7, 8)); break;
			case 3: predicates.add(builder.between(root.get("type"), 10, 13));
			}
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<TalkLine> findAllTalkCriteria(final Long companyId, final Integer filter, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TalkLine> criteriaQuery = builder.createQuery(TalkLine.class);
		final Root<Talk> root = criteriaQuery.from(Talk.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			switch(filter) {
			case 1: predicates.add(builder.or(builder.between(root.get("type"), 1, 6), builder.equal(root.get("type"), 9))); break;
			case 2: predicates.add(builder.between(root.get("type"), 7, 8)); break;
			case 3: predicates.add(builder.between(root.get("type"), 10, 13));
			}
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("talkedDate")) : builder.asc(root.get("talkedDate")); break;
		default: order = hasDesc ? builder.desc(root.get("consulted")) : builder.asc(root.get("consulted"));
		}
		criteriaQuery.select(builder.construct(TalkLine.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<TalkLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
