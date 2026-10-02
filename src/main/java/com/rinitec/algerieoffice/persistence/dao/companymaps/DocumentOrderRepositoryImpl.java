package com.rinitec.algerieoffice.persistence.dao.companymaps;

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

import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmCampaignOrderLine;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmPromoteOrderLine;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumOrderLine;

@Repository
public class DocumentOrderRepositoryImpl implements DocumentOrderRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllPromoteOrderAdmin(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<DocumentOrder> root = criteriaQuery.from(DocumentOrder.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("type"), OrderType.promote));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(root.get("consulted")) : builder.isFalse(root.get("consulted")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
			final Expression<String> title = builder.lower(rootPromote.get("title"));
			predicates.add(builder.equal(root.get("documentUUID"), rootPromote.get("id")));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmPromoteOrderLine> findAllPromoteOrderAdmin(final Boolean filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmPromoteOrderLine> criteriaQuery = builder.createQuery(AdmPromoteOrderLine.class);
		final Root<DocumentOrder> rootOrder = criteriaQuery.from(DocumentOrder.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<User> subroot = subquery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootOrder.get("type"), OrderType.promote));
		predicates.add(builder.equal(rootOrder.get("documentUUID"), rootPromote.get("id")));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(rootOrder.get("consulted")) : builder.isFalse(rootOrder.get("consulted")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPromote.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> exp = builder.concat(subroot.get("firstName"), builder.concat(" ", subroot.get("lastName")));
		subquery.select(exp).where(builder.equal(subroot.get("id"), rootOrder.get("validateById")));
		final Expression<String> username = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootOrder.get("orderDate")) : builder.asc(rootOrder.get("orderDate")); break;
		case 2: order = hasDesc ? builder.desc(rootPromote.get("title")) : builder.asc(rootPromote.get("title")); break;
		default: order = hasDesc ? builder.desc(rootOrder.get("pack")) : builder.asc(rootOrder.get("pack"));
		}
		criteriaQuery.select(builder.construct(AdmPromoteOrderLine.class, rootOrder, rootPromote.get("title"), username));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmPromoteOrderLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllCampaignOrderAdmin(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<DocumentOrder> root = criteriaQuery.from(DocumentOrder.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("type"), OrderType.campaign));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(root.get("consulted")) : builder.isFalse(root.get("consulted")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Campaign> rootCampaign = criteriaQuery.from(Campaign.class);
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(root.get("documentUUID"), rootCampaign.get("id")));
			predicates.add(builder.equal(rootCampaign.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmCampaignOrderLine> findAllCampaignOrderAdmin(final Boolean filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmCampaignOrderLine> criteriaQuery = builder.createQuery(AdmCampaignOrderLine.class);
		final Root<DocumentOrder> rootOrder = criteriaQuery.from(DocumentOrder.class);
		final Root<Campaign> rootCampaign = criteriaQuery.from(Campaign.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<User> subroot = subquery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootOrder.get("type"), OrderType.campaign));
		predicates.add(builder.equal(rootOrder.get("documentUUID"), rootCampaign.get("id")));
		predicates.add(builder.equal(rootCampaign.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(rootOrder.get("consulted")) : builder.isFalse(rootOrder.get("consulted")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> exp = builder.concat(subroot.get("firstName"), builder.concat(" ", subroot.get("lastName")));
		subquery.select(exp).where(builder.equal(subroot.get("id"), rootOrder.get("validateById")));
		final Expression<String> username = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootOrder.get("orderDate")) : builder.asc(rootOrder.get("orderDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootOrder.get("pack")) : builder.asc(rootOrder.get("pack"));
		}
		criteriaQuery.select(builder.construct(AdmCampaignOrderLine.class, rootOrder, rootCompany.get("tradename"), username));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmCampaignOrderLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllPremiumOrderAdmin(final Integer filter, final String search, final OrderType type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<DocumentOrder> root = criteriaQuery.from(DocumentOrder.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("type"), type));
		if(filter != null) {
			predicates.add(builder.equal(root.get("pack"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(root.get("userId"), rootUser.get("id")));
			predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmPremiumOrderLine> findAllPremiumOrderAdmin(final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc, final OrderType type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmPremiumOrderLine> criteriaQuery = builder.createQuery(AdmPremiumOrderLine.class);
		final Root<DocumentOrder> rootOrder = criteriaQuery.from(DocumentOrder.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootOrder.get("type"), type));
		predicates.add(builder.equal(rootOrder.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(builder.equal(rootOrder.get("pack"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootOrder.get("orderDate")) : builder.asc(rootOrder.get("orderDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 3: order = hasDesc ? builder.desc(rootOrder.get("pack")) : builder.asc(rootOrder.get("pack")); break;
		default: order = hasDesc ? builder.desc(rootOrder.get("consulted")) : builder.asc(rootOrder.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmPremiumOrderLine.class, rootOrder, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmPremiumOrderLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public AdmPremiumOrderLine findOneAdmPremiumOrderLine(final UUID orderId, final OrderType type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmPremiumOrderLine> criteriaQuery = builder.createQuery(AdmPremiumOrderLine.class);
		final Root<DocumentOrder> rootOrder = criteriaQuery.from(DocumentOrder.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootOrder.get("id"), orderId));
		predicates.add(builder.equal(rootOrder.get("type"), type));
		predicates.add(builder.equal(rootOrder.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		criteriaQuery.select(builder.construct(AdmPremiumOrderLine.class, rootOrder, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<AdmPremiumOrderLine> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
}
