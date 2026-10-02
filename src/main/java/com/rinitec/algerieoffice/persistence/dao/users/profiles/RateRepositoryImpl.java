package com.rinitec.algerieoffice.persistence.dao.users.profiles;

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

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmBlockLine;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmRateLine;

@Repository
public class RateRepositoryImpl implements RateRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllRateAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Rate> root = criteriaQuery.from(Rate.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(root.get("memberId"), rootUser.get("id")));
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
	public List<AdmRateLine> findAllRateAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmRateLine> criteriaQuery = builder.createQuery(AdmRateLine.class);
		final Root<Rate> rootRate = criteriaQuery.from(Rate.class);
		final Root<User> rootMember = criteriaQuery.from(User.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> expMember = builder.concat(rootMember.get("firstName"), builder.concat(" ", rootMember.get("lastName")));
		final Expression<String> expUser = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootRate.get("memberId"), rootMember.get("id")));
		predicates.add(builder.equal(rootRate.get("userId"), rootUser.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootRate.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(expMember), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootRate.get("postedDate")) : builder.asc(rootRate.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(expMember) : builder.asc(expMember); break;
		case 3: order = hasDesc ? builder.desc(expUser) : builder.asc(expUser); break;
		default: order = hasDesc ? builder.desc(rootRate.get("approuved")) : builder.asc(rootRate.get("approuved"));
		}
		criteriaQuery.select(builder.construct(AdmRateLine.class, rootRate, rootMember, expUser));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmRateLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllBlockAdmin(final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("locked")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmBlockLine> findAllBlockAdmin(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmBlockLine> criteriaQuery = builder.createQuery(AdmBlockLine.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Profile> subqueryProfile = criteriaQuery.subquery(Profile.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		predicates.add(builder.isTrue(rootUser.get("locked")));
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		subqueryProfile.select(subrootProfile).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		final Expression<Profile> profile = subqueryProfile.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAccount.get("createDate")) : builder.asc(rootAccount.get("createDate"));
		}
		criteriaQuery.select(builder.construct(AdmBlockLine.class, rootUser, profile, rootAccount.get("createDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmBlockLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
