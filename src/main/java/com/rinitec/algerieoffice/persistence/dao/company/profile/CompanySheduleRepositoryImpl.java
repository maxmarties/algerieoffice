package com.rinitec.algerieoffice.persistence.dao.company.profile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;

@Repository
public class CompanySheduleRepositoryImpl implements CompanySheduleRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countFax() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyShedule> rootShedule = criteriaQuery.from(CompanyShedule.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootShedule.get("companyId")));
		predicates.add(builder.isNotNull(rootShedule.get("fax")));
		criteriaQuery.select(builder.countDistinct(rootShedule)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countMobile() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyShedule> rootShedule = criteriaQuery.from(CompanyShedule.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootShedule.get("companyId")));
		predicates.add(builder.isNotNull(rootShedule.get("mobile")));
		criteriaQuery.select(builder.countDistinct(rootShedule)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
}
