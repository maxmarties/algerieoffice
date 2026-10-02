package com.rinitec.algerieoffice.persistence.dao.company.newsletter;

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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmBudgetLine;

@Repository
public class BudgetRepositoryImpl implements BudgetRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllBudgetAdmin(final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Budget> root = criteriaQuery.from(Budget.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(root.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmBudgetLine> findAllBudgetAdmin(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmBudgetLine> criteriaQuery = builder.createQuery(AdmBudgetLine.class);
		final Root<Budget> rootBudget = criteriaQuery.from(Budget.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subqueryUrl = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootUrl = subqueryUrl.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBudget.get("companyId"), rootCompany.get("id")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootBudget.get("emails")) : builder.asc(rootBudget.get("emails")); break;
		case 2: order = hasDesc ? builder.desc(rootBudget.get("sendings")) : builder.asc(rootBudget.get("sendings")); break;
		default: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename"));
		}
		subqueryUrl.select(subrootUrl.get("url")).where(builder.equal(subrootUrl.get("companyId"), rootCompany.get("id")));
		final Expression<String> url = subqueryUrl.getSelection();
		criteriaQuery.select(builder.construct(AdmBudgetLine.class, rootBudget, rootCompany, url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmBudgetLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}

}
