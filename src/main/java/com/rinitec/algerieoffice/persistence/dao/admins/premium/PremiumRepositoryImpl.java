package com.rinitec.algerieoffice.persistence.dao.admins.premium;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumLine;
import com.rinitec.algerieoffice.web.modal.company.tools.PremiumLine;

@Repository
public class PremiumRepositoryImpl implements PremiumRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllPremiumAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Premium> root = criteriaQuery.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("pass"), filter));
		}
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
	public List<AdmPremiumLine> findAllPremiumAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmPremiumLine> criteriaQuery = builder.createQuery(AdmPremiumLine.class);
		final Root<Premium> rootPremium = criteriaQuery.from(Premium.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPremium.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(builder.equal(rootPremium.get("pass"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPremium.get("expiryDate")) : builder.asc(rootPremium.get("expiryDate")); break;
		case 2: order = hasDesc ? builder.desc(rootPremium.get("createDate")) : builder.asc(rootPremium.get("createDate")); break;
		case 3: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootPremium.get("enabled")) : builder.asc(rootPremium.get("enabled"));
		}
		criteriaQuery.select(builder.construct(AdmPremiumLine.class, rootPremium, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmPremiumLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<AdmPremiumLine> findPremiumAdminCompany(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmPremiumLine> criteriaQuery = builder.createQuery(AdmPremiumLine.class);
		final Root<Premium> rootPremium = criteriaQuery.from(Premium.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPremium.get("companyId"), companyId));
		predicates.add(builder.equal(rootPremium.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		criteriaQuery.select(builder.construct(AdmPremiumLine.class, rootPremium, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootPremium.get("expiryDate")));
		final TypedQuery<AdmPremiumLine> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllPremiumCriteria(final Long companyId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Premium> root = criteriaQuery.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.equal(root.get("pass"), filter));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PremiumLine> findAllPremiumCriteria(final Long companyId, final Integer filter, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PremiumLine> criteriaQuery = builder.createQuery(PremiumLine.class);
		final Root<Premium> root = criteriaQuery.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.equal(root.get("pass"), filter));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("expiryDate")) : builder.asc(root.get("expiryDate")); break;
		case 2: order = hasDesc ? builder.desc(root.get("createDate")) : builder.asc(root.get("createDate")); break;
		default: order = hasDesc ? builder.desc(root.get("enabled")) : builder.asc(root.get("enabled"));
		}
		criteriaQuery.select(builder.construct(PremiumLine.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PremiumLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActivePremium(final Integer pass) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Premium> root = criteriaQuery.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.greaterThanOrEqualTo(root.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicates.add(builder.isTrue(root.get("enabled")));
		if(pass != null && pass != 0) {
			predicates.add(builder.equal(root.get("pass"), pass));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllPremium(final Integer pass, final DateTime begin, final DateTime end, final boolean create) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Premium> root = criteriaQuery.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.between(create ? root.get("createDate") : root.get("expiryDate"), begin, end));
		if(pass != null && pass != 0) {
			predicates.add(builder.equal(root.get("pass"), pass));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PremiumLine> findAllPremium(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PremiumLine> criteriaQuery = builder.createQuery(PremiumLine.class);
		final Root<Premium> root = criteriaQuery.from(Premium.class);
		criteriaQuery.select(builder.construct(PremiumLine.class, root)).where(builder.equal(root.get("companyId"), companyId));
		criteriaQuery.orderBy(builder.desc(root.get("expiryDate")));
		final TypedQuery<PremiumLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(20);
		return query.getResultList();
	}
	
}
