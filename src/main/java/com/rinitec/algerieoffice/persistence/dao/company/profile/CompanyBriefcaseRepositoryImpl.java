package com.rinitec.algerieoffice.persistence.dao.company.profile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesCapital;

@Repository
public class CompanyBriefcaseRepositoryImpl implements CompanyBriefcaseRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countByTypeForSector(final Integer sector, final Integer wilaya, final int type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(builder.equal(rootBriefcase.get("type"), type));
		criteriaQuery.select(builder.countDistinct(rootBriefcase)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countByBriefcaseForSector(final Integer sector, final Integer wilaya, final int briefcase) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(builder.equal(rootBriefcase.get("briefcase"), briefcase));
		criteriaQuery.select(builder.countDistinct(rootBriefcase)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countByWarehouseForSector(final Integer sector, final Integer wilaya, final boolean warehouse) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(warehouse ? builder.isTrue(rootBriefcase.get("warehouse")) : builder.isFalse(rootBriefcase.get("warehouse")));
		criteriaQuery.select(builder.countDistinct(rootBriefcase)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public ChartCompaniesCapital readChartCompaniesCapital(final Integer sector, final Integer wilaya) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ChartCompaniesCapital> criteriaQuery = builder.createQuery(ChartCompaniesCapital.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		criteriaQuery.select(builder.construct(ChartCompaniesCapital.class, builder.sum(rootBriefcase.get("capital")), 
				builder.avg(rootBriefcase.get("capital"))));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<ChartCompaniesCapital> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countByTypeForActivity(final String code, final Integer wilaya, final int type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(builder.equal(rootBriefcase.get("type"), type));
		criteriaQuery.select(builder.countDistinct(rootBriefcase)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public ChartCompaniesCapital readChartCompaniesCapital(final String code, final Integer wilaya) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ChartCompaniesCapital> criteriaQuery = builder.createQuery(ChartCompaniesCapital.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		criteriaQuery.select(builder.construct(ChartCompaniesCapital.class, builder.sum(rootBriefcase.get("capital")), 
				builder.avg(rootBriefcase.get("capital"))));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<ChartCompaniesCapital> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countByBriefcaseForActivity(final String code, final Integer wilaya, final int briefcase) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(builder.equal(rootBriefcase.get("briefcase"), briefcase));
		criteriaQuery.select(builder.countDistinct(rootBriefcase)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countByWarehouseForActivity(final String code, final Integer wilaya, final boolean warehouse) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootBriefcase.get("companyId")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicates.add(warehouse ? builder.isTrue(rootBriefcase.get("warehouse")) : builder.isFalse(rootBriefcase.get("warehouse")));
		criteriaQuery.select(builder.countDistinct(rootBriefcase)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
