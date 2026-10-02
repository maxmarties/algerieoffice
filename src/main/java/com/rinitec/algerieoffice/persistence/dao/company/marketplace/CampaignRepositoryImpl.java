package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.CampaignTarget;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmCampaignLine;

@Repository
public class CampaignRepositoryImpl implements CampaignRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllCampaignCriteria(final Long companyId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Campaign> root = criteriaQuery.from(Campaign.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), ParseUtil.parseDocumentType(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<Object[]> findAllCampaignCriteria(final Long companyId, final Integer filter, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Campaign> rootCampaign = criteriaQuery.from(Campaign.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<DocumentOrder> subroot = subquery.from(DocumentOrder.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> subpredicates = new ArrayList<>();
		predicates.add(builder.equal(rootCampaign.get("companyId"), companyId));
		predicates.add(builder.equal(rootCampaign.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootCampaign.get("type"), ParseUtil.parseDocumentType(filter)));
		}
		subpredicates.add(builder.equal(subroot.get("type"), OrderType.campaign));
		subpredicates.add(builder.equal(subroot.get("documentUUID"), rootCampaign.get("id")));
		subpredicates.add(builder.isFalse(subroot.get("consulted")));
		subquery.select(builder.count(subroot)).where(subpredicates.toArray(new Predicate[0]));
		final Expression<Long> orderCount = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCampaign.get("clickCount")) : builder.asc(rootCampaign.get("clickCount")); break;
		case 2: order = hasDesc ? builder.desc(rootCampaign.get("viewCount")) : builder.asc(rootCampaign.get("viewCount")); break;
		case 3: order = hasDesc ? builder.desc(rootCampaign.get("creditCount")) : builder.asc(rootCampaign.get("creditCount")); break;
		default: order = hasDesc ? builder.desc(exp) : builder.asc(exp);
		}
		criteriaQuery.multiselect(rootCampaign, exp, orderCount).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllCampaignAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Campaign> root = criteriaQuery.from(Campaign.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), ParseUtil.parseDocumentType(filter)));
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
	public List<AdmCampaignLine> findAllCampaignAdmin(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmCampaignLine> criteriaQuery = builder.createQuery(AdmCampaignLine.class);
		final Root<Campaign> rootCampaign = criteriaQuery.from(Campaign.class);
		final Root<CampaignTarget> rootTarget = criteriaQuery.from(CampaignTarget.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCampaign.get("id"), rootTarget.get("id")));
		predicates.add(builder.equal(rootCampaign.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(builder.equal(rootCampaign.get("type"), ParseUtil.parseDocumentType(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(rootCampaign.get("clickCount")) : builder.asc(rootCampaign.get("clickCount")); break;
		case 3: order = hasDesc ? builder.desc(rootCampaign.get("viewCount")) : builder.asc(rootCampaign.get("viewCount")); break;
		default: order = hasDesc ? builder.desc(rootCampaign.get("creditCount")) : builder.asc(rootCampaign.get("creditCount"));
		}
		criteriaQuery.select(builder.construct(AdmCampaignLine.class, rootCampaign, rootTarget, rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmCampaignLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Campaign findCampaignDashboard(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Campaign> criteriaQuery = builder.createQuery(Campaign.class);
		final Root<Campaign> rootCampaign = criteriaQuery.from(Campaign.class);
		final Root<CampaignTarget> rootTarget = criteriaQuery.from(CampaignTarget.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCampaign.get("id"), rootTarget.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicates.add(builder.notEqual(rootCampaign.get("companyId"), companyId));
		predicates.add(builder.lessThan(rootCampaign.get("clickCount"), rootCampaign.get("creditCount")));
		predicates.add(builder.isTrue(rootCampaign.get("enabled")));
		predicates.add(builder.isTrue(rootCampaign.get("published")));
		predicates.add(builder.or(builder.isNull(rootTarget.get("sector")), builder.in(joinActivity.get("sector")).value(rootTarget.get("sector"))));
		predicates.add(builder.or(builder.isNull(rootTarget.get("wilaya")), builder.in(joinAddress.get("wilaya")).value(rootTarget.get("wilaya"))));
		criteriaQuery.select(rootCampaign).distinct(true).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootCampaign.get("viewCount")));
		final TypedQuery<Campaign> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(1);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllActiveCampaign() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Campaign> rootCampaign = criteriaQuery.from(Campaign.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCampaign.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.lessThan(rootCampaign.get("clickCount"), rootCampaign.get("creditCount")));
		predicates.add(builder.isTrue(rootCampaign.get("enabled")));
		predicates.add(builder.isTrue(rootCampaign.get("published")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootCampaign)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
