package com.rinitec.algerieoffice.persistence.dao.journal;

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

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardJournal;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.JournalLine;

@Repository
public class JournalCompanyRepositoryImpl implements JournalCompanyRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllJournalCompanyCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<JournalCompany> rootJournal = criteriaQuery.from(JournalCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootJournal.get("companyId"), companyId));
		predicates.add(builder.equal(rootUser.get("companyId"), companyId));
		predicates.add(builder.equal(rootJournal.get("userId"), rootUser.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootJournal.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootJournal)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<JournalLine> findAllJouranlCompanyCriteria(final Long companyId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<JournalLine> criteriaQuery = builder.createQuery(JournalLine.class);
		final Root<JournalCompany> rootJournal = criteriaQuery.from(JournalCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootJournal.get("companyId"), companyId));
		predicates.add(builder.equal(rootUser.get("companyId"), companyId));
		predicates.add(builder.equal(rootJournal.get("userId"), rootUser.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootJournal.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootJournal.get("postedDate")) : builder.asc(rootJournal.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootJournal.get("action")) : builder.asc(rootJournal.get("action"));
		}
		criteriaQuery.select(builder.construct(JournalLine.class, rootJournal, rootUser));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<JournalLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<JournalAccess> findJournalAccessListCriteria(final Long companyId, final String action) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<JournalAccess> criteriaQuery = builder.createQuery(JournalAccess.class);
		final Root<JournalCompany> rootJournal = criteriaQuery.from(JournalCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootJournal.get("companyId"), companyId));
		predicates.add(builder.equal(rootUser.get("companyId"), companyId));
		predicates.add(builder.equal(rootJournal.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootJournal.get("action"), action));
		predicates.add(builder.between(rootJournal.get("postedDate"), ParseUtil.getBeginDate(1), ParseUtil.getEndDate(1)));
		criteriaQuery.select(builder.construct(JournalAccess.class, rootUser, rootJournal));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootJournal.get("postedDate")));
		final TypedQuery<JournalAccess> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(100);
		return query.getResultList();
	}
	
	@Override
	public Long countJournalCompany(final Long companyId, final Boolean login, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<JournalCompany> root = criteriaQuery.from(JournalCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(companyId != null) {
			predicates.add(builder.equal(root.get("companyId"), companyId));
		}
		if(login != null) {
			predicates.add(login ? builder.equal(root.get("action"), ConstraintesJournal.COMPANY_LOGIN_USER) 
					: builder.notEqual(root.get("action"), ConstraintesJournal.COMPANY_LOGIN_USER));
		}
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<DashboardJournal> findLastJouranlCompany(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DashboardJournal> criteriaQuery = builder.createQuery(DashboardJournal.class);
		final Root<JournalCompany> rootJournal = criteriaQuery.from(JournalCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootJournal.get("companyId"), companyId));
		predicates.add(builder.equal(rootUser.get("companyId"), companyId));
		predicates.add(builder.equal(rootJournal.get("userId"), rootUser.get("id")));
		criteriaQuery.select(builder.construct(DashboardJournal.class, rootJournal, rootUser.get("id"), exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootJournal.get("postedDate")));
		final TypedQuery<DashboardJournal> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
