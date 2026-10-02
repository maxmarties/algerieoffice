package com.rinitec.algerieoffice.persistence.dao.inbox;

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
import javax.persistence.criteria.Subquery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmChaterLine;
import com.rinitec.algerieoffice.web.modal.inbox.ChaterPush;

@Repository
public class ChaterRepositoryImpl implements ChaterRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ChaterPush> findAllChatterCriteria(final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ChaterPush> criteriaQuery = builder.createQuery(ChaterPush.class);
		final Root<Chater> rootChater = criteriaQuery.from(Chater.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootChater.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.between(rootChater.get("chaterDate"), ParseUtil.getBeginDate(ParseUtil.TODAY), ParseUtil.getEndDate(ParseUtil.TODAY)));
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.select(builder.construct(ChaterPush.class, rootChater, exp, rootUser.get("hasAvatar"), rootCompany.get("tradename"), 
				rootSeo.get("url"), premium));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootChater.get("chaterDate")));
		final TypedQuery<ChaterPush> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllChaterAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Chater> root = criteriaQuery.from(Chater.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(root.get("chaterDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(root.get("userId"), rootUser.get("id")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmChaterLine> findAllChaterAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmChaterLine> criteriaQuery = builder.createQuery(AdmChaterLine.class);
		final Root<Chater> rootChater = criteriaQuery.from(Chater.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootChater.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootChater.get("chaterDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootChater.get("chaterDate")) : builder.asc(rootChater.get("chaterDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootChater.get("type")) : builder.asc(rootChater.get("type"));
		}
		criteriaQuery.select(builder.construct(AdmChaterLine.class, rootChater, rootUser, rootCompany.get("tradename")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmChaterLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
