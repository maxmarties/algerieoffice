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
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.PromoteActivity;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.PromoteWilaya;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmPromoteLine;
import com.rinitec.algerieoffice.web.modal.company.marketplace.PromoteLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecyclePromoteLine;

@Repository
public class PromoteRepositoryImpl implements PromoteRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllPromoteCriteria(final Long companyId, final Long filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Promote> root = criteriaQuery.from(Promote.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PromoteLine> findAllPromoteCriteria(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PromoteLine> criteriaQuery = builder.createQuery(PromoteLine.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<DocumentOrder> subroot = subquery.from(DocumentOrder.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> subpredicates = new ArrayList<>();
		predicates.add(builder.equal(rootPromote.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootPromote.get("hasTrashed")));
		predicates.add(builder.equal(rootPromote.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootPromote.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPromote.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subpredicates.add(builder.equal(subroot.get("type"), OrderType.promote));
		subpredicates.add(builder.equal(subroot.get("documentUUID"), rootPromote.get("id")));
		subpredicates.add(builder.isFalse(subroot.get("consulted")));
		subquery.select(builder.count(subroot)).where(subpredicates.toArray(new Predicate[0]));
		final Expression<Long> orderCount = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPromote.get("title")) : builder.asc(rootPromote.get("title")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		case 3: order = hasDesc ? builder.desc(rootPromote.get("creditCount")) : builder.asc(rootPromote.get("creditCount")); break;
		default: order = hasDesc ? builder.desc(rootPromote.get("viewCount")) : builder.asc(rootPromote.get("viewCount"));
		}
		criteriaQuery.select(builder.construct(PromoteLine.class, rootPromote, exp, orderCount));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PromoteLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllAutorCriteria(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Promote> subroot = subquery.from(Promote.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.equal(subroot.get("companyId"), companyId));
		predicates.add(builder.isFalse(subroot.get("hasTrashed")));
		subquery.select(subroot.get("autorId")).distinct(true).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(builder.in(root.get("id")).value(subquery)).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllPromoteAdmin(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Promote> root = criteriaQuery.from(Promote.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(root.get("enabled")) : builder.isFalse(root.get("enabled")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmPromoteLine> findAllPromoteAdmin(final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmPromoteLine> criteriaQuery = builder.createQuery(AdmPromoteLine.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPromote.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(rootPromote.get("enabled")) : builder.isFalse(rootPromote.get("enabled")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPromote.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(rootPromote.get("title")) : builder.asc(rootPromote.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootPromote.get("viewCount")) : builder.asc(rootPromote.get("viewCount")); break;
		default: order = hasDesc ? builder.desc(rootPromote.get("creditCount")) : builder.asc(rootPromote.get("creditCount"));
		}
		criteriaQuery.select(builder.construct(AdmPromoteLine.class, rootPromote, rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmPromoteLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Promote findPromoteExplorer(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Promote> criteriaQuery = builder.createQuery(Promote.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final Subquery<Integer> subqueryActivity = criteriaQuery.subquery(Integer.class);
		final Root<PromoteActivity> subrootActivity = subqueryActivity.from(PromoteActivity.class);
		final Subquery<Integer> subqueryWilaya = criteriaQuery.subquery(Integer.class);
		final Root<PromoteWilaya> subrootWilaya = subqueryWilaya.from(PromoteWilaya.class);
		final List<Predicate> predicates = new ArrayList<>();
		subqueryActivity.select(subrootActivity.get("sector")).where(builder.equal(subrootActivity.get("promoteUUID"), rootPromote.get("id")));
		subqueryWilaya.select(subrootWilaya.get("wilaya")).where(builder.equal(subrootWilaya.get("promoteUUID"), rootPromote.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), companyId));
		predicates.add(builder.notEqual(rootPromote.get("companyId"), companyId));
		predicates.add(builder.lessThan(rootPromote.get("viewCount"), rootPromote.get("creditCount")));
		predicates.add(builder.isTrue(rootPromote.get("enabled")));
		predicates.add(builder.isFalse(rootPromote.get("hasTrashed")));
		predicates.add(builder.in(joinActivity.get("sector")).value(subqueryActivity));
		predicates.add(builder.or(builder.in(joinAddress.get("wilaya")).value(subqueryWilaya), builder.isNull(subqueryWilaya)));
		criteriaQuery.select(rootPromote).distinct(true).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootPromote.get("viewCount")));
		final TypedQuery<Promote> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(1);
		return query.getSingleResult();
	}
	
	@Override
	public Promote findPromoteHome() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Promote> criteriaQuery = builder.createQuery(Promote.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.lessThan(rootPromote.get("viewCount"), rootPromote.get("creditCount")));
		predicates.add(builder.isTrue(rootPromote.get("enabled")));
		predicates.add(builder.isFalse(rootPromote.get("hasTrashed")));
		predicates.add(builder.isFalse(rootPromote.get("hasPageonly")));
		criteriaQuery.select(rootPromote).distinct(true).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootPromote.get("viewCount")));
		final TypedQuery<Promote> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(1);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllRecyclePromoteCriteria(final Long companyId, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Promote> root = criteriaQuery.from(Promote.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasTrashed")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<RecyclePromoteLine> findAllRecyclePromoteCriteria(final Long companyId, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<RecyclePromoteLine> criteriaQuery = builder.createQuery(RecyclePromoteLine.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPromote.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootPromote.get("hasTrashed")));
		predicates.add(builder.equal(rootPromote.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPromote.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPromote.get("title")) : builder.asc(rootPromote.get("title")); break;
		default: order = hasDesc ? builder.desc(rootPromote.get("creditCount")) : builder.asc(rootPromote.get("creditCount"));
		}
		criteriaQuery.select(builder.construct(RecyclePromoteLine.class, rootPromote, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<RecyclePromoteLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActivePromote() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Promote> rootPromote = criteriaQuery.from(Promote.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPromote.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.lessThan(rootPromote.get("viewCount"), rootPromote.get("creditCount")));
		predicates.add(builder.isTrue(rootPromote.get("enabled")));
		predicates.add(builder.isFalse(rootPromote.get("hasTrashed")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootPromote)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
