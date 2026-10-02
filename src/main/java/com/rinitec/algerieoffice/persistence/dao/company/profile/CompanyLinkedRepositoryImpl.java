package com.rinitec.algerieoffice.persistence.dao.company.profile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.LinkedWebsite;

@Repository
public class CompanyLinkedRepositoryImpl implements CompanyLinkedRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countSocial(final String provider) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyLinked> rootLinked = criteriaQuery.from(CompanyLinked.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootLinked.get("companyId")));
		if(!StringUtils.isEmpty(provider)) {
			switch(provider) {
			case "facebook": predicates.add(builder.isNotNull(rootLinked.get("facebook"))); break;
			case "twitter": predicates.add(builder.isNotNull(rootLinked.get("twitter"))); break;
			case "linkedin": predicates.add(builder.isNotNull(rootLinked.get("linkedin"))); break;
			case "youtube": predicates.add(builder.isNotNull(rootLinked.get("youtube"))); break;
			case "google": predicates.add(builder.isNotNull(rootLinked.get("google"))); break;
			case "instagram": predicates.add(builder.isNotNull(rootLinked.get("instagram")));
			}
		}
		criteriaQuery.select(builder.countDistinct(rootLinked)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countWebsite() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyLinked> rootLinked = criteriaQuery.from(CompanyLinked.class);
		final Join<CompanyLinked, LinkedWebsite> joinWebsite = rootLinked.join("websites");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootLinked.get("companyId")));
		predicates.add(builder.isNotNull(joinWebsite.get("url")));
		criteriaQuery.select(builder.countDistinct(rootLinked)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
