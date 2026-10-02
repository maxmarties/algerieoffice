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
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmAssistLine;

@Repository
public class AssistRepositoryImpl implements AssistRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllAssistCriteria(final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Assist> rootAssist = criteriaQuery.from(Assist.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAssist.get("userId"), rootUser.get("id")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootAssist)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmAssistLine> findAllAssistCriteria(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmAssistLine> criteriaQuery = builder.createQuery(AdmAssistLine.class);
		final Root<Assist> rootAssist = criteriaQuery.from(Assist.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAssist.get("userId"), rootUser.get("id")));
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAssist.get("postedDate")) : builder.asc(rootAssist.get("postedDate")); break;
		default: order = hasDesc ? builder.desc(exp) : builder.asc(exp);
		}
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryCompany.getSelection();
		criteriaQuery.select(builder.construct(AdmAssistLine.class, rootAssist, rootUser, tradename));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmAssistLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
