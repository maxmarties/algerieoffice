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

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.admins.realtime.DeactivateCompanyLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.DeactivateUserLine;

@Repository
public class DeactivateRepositoryImpl implements DeactivateRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllDeactivateCompany(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Deactivate> root = criteriaQuery.from(Deactivate.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("hasCompany")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("reason"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(root.get("accountId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<DeactivateCompanyLine> findAllDeactivateCompany(final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DeactivateCompanyLine> criteriaQuery = builder.createQuery(DeactivateCompanyLine.class);
		final Root<Deactivate> rootDeactivate = criteriaQuery.from(Deactivate.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootDeactivate.get("hasCompany")));
		predicates.add(builder.equal(rootDeactivate.get("accountId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootDeactivate.get("reason"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootDeactivate.get("deactivateDate")) : builder.asc(rootDeactivate.get("deactivateDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootDeactivate.get("consulted")) : builder.asc(rootDeactivate.get("consulted"));
		}
		criteriaQuery.select(builder.construct(DeactivateCompanyLine.class, rootDeactivate, rootCompany));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<DeactivateCompanyLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllDeactivateUser(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Deactivate> root = criteriaQuery.from(Deactivate.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isFalse(root.get("hasCompany")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("reason"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(root.get("accountId"), rootUser.get("id")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<DeactivateUserLine> findAllDeactivateUser(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DeactivateUserLine> criteriaQuery = builder.createQuery(DeactivateUserLine.class);
		final Root<Deactivate> rootDeactivate = criteriaQuery.from(Deactivate.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.isFalse(rootDeactivate.get("hasCompany")));
		predicates.add(builder.equal(rootDeactivate.get("accountId"), rootUser.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootDeactivate.get("reason"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootDeactivate.get("deactivateDate")) : builder.asc(rootDeactivate.get("deactivateDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootDeactivate.get("consulted")) : builder.asc(rootDeactivate.get("consulted"));
		}
		criteriaQuery.select(builder.construct(DeactivateUserLine.class, rootDeactivate, rootUser));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<DeactivateUserLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
