package com.rinitec.algerieoffice.persistence.dao.analytic;

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

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.analytic.AccessCompany;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticAutentified;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardDetect;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectLine;

@Repository
public class AccessCompanyRepositoryImpl implements AccessCompanyRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllDetectCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> rootAccess = criteriaQuery.from(AccessCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAccess.get("companyId"), companyId));
		predicates.add(builder.equal(rootAccess.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNotNull(rootAccess.get("userId")));
		predicates.add(builder.notEqual(rootUser.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		if(filter != null) {
			predicates.add(builder.between(rootAccess.get("accessDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootAccess)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<DetectLine> findAllDetectCriteria(final Long companyId, final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DetectLine> criteriaQuery = builder.createQuery(DetectLine.class);
		final Root<AccessCompany> rootAccess = criteriaQuery.from(AccessCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootAccess.get("companyId"), companyId));
		predicates.add(builder.equal(rootAccess.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNotNull(rootAccess.get("userId")));
		predicates.add(builder.notEqual(rootUser.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		if(filter != null) {
			predicates.add(builder.between(rootAccess.get("accessDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryCompany.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAccess.get("accessDate")) : builder.asc(rootAccess.get("accessDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAccess.get("accessType")) : builder.asc(rootAccess.get("accessType"));
		}
		criteriaQuery.select(builder.construct(DetectLine.class, rootAccess, rootUser, tradename));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<DetectLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<DetectAccess> findDetectAccessListCriteria(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DetectAccess> criteriaQuery = builder.createQuery(DetectAccess.class);
		final Root<AccessCompany> rootAccess = criteriaQuery.from(AccessCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryCount = criteriaQuery.subquery(Long.class);
		final Root<AccessCompany> subrootAccess = subqueryCount.from(AccessCompany.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<DateTime> subqueryDate = criteriaQuery.subquery(DateTime.class);
		final Root<AccessCompany> subrootDate = subqueryDate.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAccess.get("companyId"), companyId));
		predicates.add(builder.equal(rootAccess.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNotNull(rootAccess.get("userId")));
		predicates.add(builder.notEqual(rootUser.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		subqueryCount.select(builder.count(subrootAccess)).where(builder.equal(subrootAccess.get("userId"), rootUser.get("id")));
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootUser.get("companyId")));
		subqueryDate.select(builder.greatest(subrootDate.<DateTime>get("accessDate"))).where(builder.equal(subrootDate.get("userId"), rootUser.get("id")));
		final Expression<Long> count = subqueryCount.getSelection();
		final Expression<String> tradename = subqueryCompany.getSelection();
		final Expression<String> url = subquerySeo.getSelection();
		final Expression<DateTime> date = subqueryDate.getSelection();
		criteriaQuery.select(builder.construct(DetectAccess.class, rootUser, tradename, url, date, count)).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<DetectAccess> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAccessCompany(final Long companyId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> root = criteriaQuery.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.between(root.get("accessDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAccessCompanyByType(final Long companyId, final Integer type, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> root = criteriaQuery.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.equal(root.get("accessType"), ParseUtil.parseAccessType(type)));
		if(filter != null) {
			predicates.add(builder.between(root.get("accessDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAccessCompanyByWeb(final Long companyId, final boolean web, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> root = criteriaQuery.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(web ? builder.isTrue(root.get("fromWeb")) : builder.isFalse(root.get("fromWeb")));
		if(companyId != null) {
			predicates.add(builder.equal(root.get("companyId"), companyId));
		}
		if(filter != null) {
			predicates.add(builder.between(root.get("accessDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAccessCompanyByAuthentified(final Long companyId, final boolean authentified, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> root = criteriaQuery.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(authentified ? builder.isNotNull(root.get("userId")) : builder.isNull(root.get("userId")));
		if(filter != null) {
			predicates.add(builder.between(root.get("accessDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AnalyticAutentified> findAllAnalyticAutentified(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnalyticAutentified> criteriaQuery = builder.createQuery(AnalyticAutentified.class);
		final Root<AccessCompany> rootAccess = criteriaQuery.from(AccessCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<DateTime> subqueryDate = criteriaQuery.subquery(DateTime.class);
		final Root<AccessCompany> subrootDate = subqueryDate.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAccess.get("companyId"), companyId));
		predicates.add(builder.isNotNull(rootAccess.get("userId")));
		predicates.add(builder.equal(rootAccess.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNotNull(rootUser.get("companyId")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		subqueryDate.select(builder.greatest(subrootDate.<DateTime>get("accessDate"))).where(builder.equal(subrootDate.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootAccess.get("accessDate"), subqueryDate.getSelection()));
		criteriaQuery.select(builder.construct(AnalyticAutentified.class, rootCompany, rootSeo.get("url"), rootAccess.get("accessDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAccess.get("accessDate")));
		final TypedQuery<AnalyticAutentified> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<DashboardDetect> findLastDashboardDetect(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DashboardDetect> criteriaQuery = builder.createQuery(DashboardDetect.class);
		final Root<AccessCompany> rootAccess = criteriaQuery.from(AccessCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final Subquery<DateTime> subqueryDate = criteriaQuery.subquery(DateTime.class);
		final Root<AccessCompany> subrootDate = subqueryDate.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAccess.get("companyId"), companyId));
		predicates.add(builder.equal(rootAccess.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNotNull(rootAccess.get("userId")));
		predicates.add(builder.notEqual(rootUser.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		subqueryDate.select(builder.greatest(subrootDate.<DateTime>get("accessDate"))).where(builder.equal(subrootDate.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootAccess.get("accessDate"), subqueryDate.getSelection()));
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryCompany.getSelection();
		criteriaQuery.select(builder.construct(DashboardDetect.class, rootUser, tradename, rootAccess.get("accessDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAccess.get("accessDate")));
		final TypedQuery<DashboardDetect> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAccessUser(final Long userId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> root = criteriaQuery.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.between(root.get("accessDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllAccess(final DateTime begin, final DateTime end, final boolean authentified) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AccessCompany> root = criteriaQuery.from(AccessCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.between(root.get("accessDate"), begin, end));
		predicates.add(authentified ? builder.isNotNull(root.get("userId")) : builder.isNull(root.get("userId")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
