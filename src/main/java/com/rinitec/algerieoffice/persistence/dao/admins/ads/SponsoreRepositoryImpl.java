package com.rinitec.algerieoffice.persistence.dao.admins.ads;

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

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.SponsoreLine;

@Repository
public class SponsoreRepositoryImpl implements SponsoreRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllSponsoreCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Sponsore> root = criteriaQuery.from(Sponsore.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> url = builder.lower(root.get("url"));
			predicates.add(builder.like(url, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root));
		if(!predicates.isEmpty()) {
			criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<SponsoreLine> findAllSponsoreCriteria(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<SponsoreLine> criteriaQuery = builder.createQuery(SponsoreLine.class);
		final Root<Sponsore> root = criteriaQuery.from(Sponsore.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> url = builder.lower(root.get("url"));
			predicates.add(builder.like(url, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("url")) : builder.asc(root.get("url")); break;
		default: order = hasDesc ? builder.desc(root.get("type")) : builder.asc(root.get("type"));
		}
		criteriaQuery.select(builder.construct(SponsoreLine.class, root));
		if(!predicates.isEmpty()) {
			criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}
		criteriaQuery.orderBy(order);
		final TypedQuery<SponsoreLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Sponsore findSponsoreExplorer(final Integer type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Sponsore> criteriaQuery = builder.createQuery(Sponsore.class);
		final Root<Sponsore> root = criteriaQuery.from(Sponsore.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("type"), type));
		predicates.add(builder.lessThan(root.get("viewCount"), root.get("creditCount")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(root).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("viewCount")));
		final TypedQuery<Sponsore> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(1);
		return query.getSingleResult();
	}
	
}
