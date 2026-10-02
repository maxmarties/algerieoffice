package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.WorkDetail;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.company.portfolio.WorkLine;

@Repository
public class WorkRepositoryImpl implements WorkRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final UUID parseFilterUUID(final String filter) {
		try {
			return UUID.fromString(filter);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	public Long countAllWorkCriteria(final Long companyId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Work> root = criteriaQuery.from(Work.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(root.get("partnerUUID")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(root.get("partnerUUID"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<WorkLine> findAllWorkCriteria(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<WorkLine> criteriaQuery = builder.createQuery(WorkLine.class);
		final Root<Work> rootWork = criteriaQuery.from(Work.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<Partner> subroot = subquery.from(Partner.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootWork.get("companyId"), companyId));
		predicates.add(builder.equal(rootWork.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(rootWork.get("partnerUUID")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(rootWork.get("partnerUUID"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootWork.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquery.select(subroot.get("name")).where(builder.equal(subroot.get("id"), rootWork.get("partnerUUID")));
		final Expression<String> partnername = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootWork.get("workDate")) : builder.asc(rootWork.get("workDate")); break;
		case 2: order = hasDesc ? builder.desc(rootWork.get("title")) : builder.asc(rootWork.get("title")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootWork.get("modifiedDate")) : builder.asc(rootWork.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(WorkLine.class, rootWork, exp, partnername)).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<WorkLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllExplorerWorkCriteria(final Long companyId, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Work> root = criteriaQuery.from(Work.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<Object[]> findAllExplorerWorkCriteria(final Long companyId, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Work> rootWork = criteriaQuery.from(Work.class);
		final Root<WorkDetail> rootDetail = criteriaQuery.from(WorkDetail.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootWork.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootWork.get("hasPublished")));
		predicates.add(builder.equal(rootDetail.get("id"), rootWork.get("id")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootWork.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootWork.get("workDate")) : builder.asc(rootWork.get("workDate")); break;
		default: order = hasDesc ? builder.desc(rootWork.get("modifiedDate")) : builder.asc(rootWork.get("modifiedDate"));
		}
		criteriaQuery.multiselect(rootWork, rootDetail.get("photoUUID")).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActiveWork() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Work> rootWork = criteriaQuery.from(Work.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootWork.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootWork.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootWork)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
