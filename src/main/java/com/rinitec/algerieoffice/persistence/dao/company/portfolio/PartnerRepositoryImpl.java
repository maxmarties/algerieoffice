package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

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

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.company.portfolio.PartnerLine;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetPartner;

@Repository
public class PartnerRepositoryImpl implements PartnerRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllPartnerCriteria(final Long companyId, final Long filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Partner> root = criteriaQuery.from(Partner.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.equal(root.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(root.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PartnerLine> findAllPartnerCriteria(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PartnerLine> criteriaQuery = builder.createQuery(PartnerLine.class);
		final Root<Partner> rootPartner = criteriaQuery.from(Partner.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPartner.get("companyId"), companyId));
		predicates.add(builder.equal(rootPartner.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootPartner.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(rootPartner.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPartner.get("name")) : builder.asc(rootPartner.get("name")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootPartner.get("modifiedDate")) : builder.asc(rootPartner.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(PartnerLine.class, rootPartner, exp)).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PartnerLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllAutorCriteria(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Partner> subroot = subquery.from(Partner.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.equal(subroot.get("companyId"), companyId));
		subquery.select(subroot.get("autorId")).distinct(true).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(builder.in(root.get("id")).value(subquery)).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllChosePartnerMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Partner> root = criteriaQuery.from(Partner.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("name")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("name")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllFilterPartnerMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Partner> root = criteriaQuery.from(Partner.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Work> subroot = subquery.from(Work.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(subroot.get("companyId"), companyId));
		subquery.select(subroot.get("partnerUUID")).distinct(true).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("name")));
		criteriaQuery.where(builder.in(root.get("id")).value(subquery)).orderBy(builder.asc(root.get("name")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<ExplorerWidgetPartner> findAllExplorerPartnerCriteria(final Long companyId, final boolean hasPingled, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ExplorerWidgetPartner> criteriaQuery = builder.createQuery(ExplorerWidgetPartner.class);
		final Root<Partner> root = criteriaQuery.from(Partner.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(root.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		if(hasPingled) {
			predicates.add(builder.isTrue(root.get("hasPingled")));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("name")) : builder.asc(root.get("name")); break;
		default: order = hasDesc ? builder.desc(root.get("modifiedDate")) : builder.asc(root.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(ExplorerWidgetPartner.class, root)).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ExplorerWidgetPartner> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
