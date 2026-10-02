package com.rinitec.algerieoffice.persistence.dao.company.profile;

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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyIdentity;
import com.rinitec.algerieoffice.web.modal.admins.data.IdentityLine;

@Repository
public class CompanyIdentityRepositoryImpl implements CompanyIdentityRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllIdentityCriteria(final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<CompanyIdentity> rootIdentity = criteriaQuery.from(CompanyIdentity.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(rootIdentity.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
			criteriaQuery.select(builder.count(rootIdentity)).where(predicates.toArray(new Predicate[0]));
		} else {
			criteriaQuery.select(builder.count(rootIdentity));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<IdentityLine> findAllIdentityCriteria(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<IdentityLine> criteriaQuery = builder.createQuery(IdentityLine.class);
		final Root<CompanyIdentity> rootIdentity = criteriaQuery.from(CompanyIdentity.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootIdentity.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootIdentity.get("requestedDate")) : builder.asc(rootIdentity.get("requestedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootAccount.get("createdDate")) : builder.asc(rootAccount.get("createdDate")); break;
		default: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename"));
		}
		criteriaQuery.select(builder.construct(IdentityLine.class, rootIdentity.get("companyId"), rootCompany.get("hasAvatar"), 
				rootCompany.get("tradename"), rootIdentity.get("requestedDate"), rootAccount.get("createdDate"), rootIdentity.get("consulted")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<IdentityLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Object[] readIdentityDetail(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyIdentity> rootIdentity = criteriaQuery.from(CompanyIdentity.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootIdentity.get("companyId"), companyId));
		predicates.add(builder.equal(rootIdentity.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		criteriaQuery.multiselect(rootCompany, rootIdentity.get("requestedDate"), rootAccount.get("createdDate"), 
				rootSeo.get("url")).where(predicates.toArray(new Predicate[0]));
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
}

