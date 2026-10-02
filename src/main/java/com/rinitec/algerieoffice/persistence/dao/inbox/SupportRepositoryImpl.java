package com.rinitec.algerieoffice.persistence.dao.inbox;

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

import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmSupportLine;
import com.rinitec.algerieoffice.web.modal.inbox.SupportNotification;
import com.rinitec.algerieoffice.web.modal.inbox.SupportSheet;

@Repository
public class SupportRepositoryImpl implements SupportRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public long countNewSupportUser(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Support> root = criteriaQuery.from(Support.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.isNotNull(root.get("adminId")));
		predicates.add(builder.isFalse(root.get("consulted")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public long countNewSupportAdmin() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Support> root = criteriaQuery.from(Support.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isNull(root.get("adminId")));
		predicates.add(builder.isFalse(root.get("consulted")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<SupportNotification> findAllSupportNotification(final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<SupportNotification> criteriaQuery = builder.createQuery(SupportNotification.class);
		final Root<Support> rootSupport = criteriaQuery.from(Support.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<Support> subrootSupport = subquery.from(Support.class);
		final List<Predicate> predicates = new ArrayList<>();
		subquery.select(builder.greatest(subrootSupport.<DateTime>get("postedDate"))).where(builder.equal(subrootSupport.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootSupport.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootSupport.get("postedDate"), subquery.getSelection()));
		criteriaQuery.select(builder.construct(SupportNotification.class, rootUser, rootSupport));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootSupport.get("postedDate")));
		final TypedQuery<SupportNotification> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<SupportSheet> findAllSupportSheetUser(final Long userId, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<SupportSheet> criteriaQuery = builder.createQuery(SupportSheet.class);
		final Root<Support> root = criteriaQuery.from(Support.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		criteriaQuery.select(builder.construct(SupportSheet.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("postedDate")));
		final TypedQuery<SupportSheet> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<SupportSheet> findAllSupportSheetAdmin(final Long userId, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<SupportSheet> criteriaQuery = builder.createQuery(SupportSheet.class);
		final Root<Support> root = criteriaQuery.from(Support.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<User> subroot = subquery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(subroot.get("firstName"), builder.concat(" ", subroot.get("lastName")));
		predicates.add(builder.equal(root.get("userId"), userId));
		subquery.select(exp).where(builder.equal(subroot.get("id"), root.get("adminId")));
		final Expression<String> username = subquery.getSelection();
		criteriaQuery.select(builder.construct(SupportSheet.class, root, username));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("postedDate")));
		final TypedQuery<SupportSheet> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllUserSupportCriteria(final boolean recevied) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<Support> rootSupport = criteriaQuery.from(Support.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<Support> subrootSupport = subquery.from(Support.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		subquery.select(builder.greatest(subrootSupport.<DateTime>get("postedDate"))).where(builder.equal(subrootSupport.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootSupport.get("userId"), rootUser.get("id")));
		predicates.add(recevied ? builder.isNull(rootSupport.get("adminId")) : builder.isNotNull(rootSupport.get("adminId")));
		predicates.add(builder.equal(rootSupport.get("postedDate"), subquery.getSelection()));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), exp, rootSupport.get("postedDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootSupport.get("postedDate")));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllSupportCriteria(final boolean recevied, final Long filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Support> rootSupport = criteriaQuery.from(Support.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(recevied ? builder.isNull(rootSupport.get("adminId")) : builder.isNotNull(rootSupport.get("adminId")));
		if(filter != null) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			predicates.add(builder.equal(rootSupport.get("userId"), rootUser.get("id")));
			predicates.add(builder.equal(rootUser.get("id"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> message = builder.lower(rootSupport.get("message"));
			predicates.add(builder.like(message, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootSupport)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmSupportLine> findAllSupportReceviedCriteria(final Long filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmSupportLine> criteriaQuery = builder.createQuery(AdmSupportLine.class);
		final Root<Support> rootSupport = criteriaQuery.from(Support.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootSupport.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNull(rootSupport.get("adminId")));
		if(filter != null) {
			predicates.add(builder.equal(rootUser.get("id"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> message = builder.lower(rootSupport.get("message"));
			predicates.add(builder.like(message, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootSupport.get("postedDate")) : builder.asc(rootSupport.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootSupport.get("consulted")) : builder.asc(rootSupport.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmSupportLine.class, rootUser, rootSupport));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmSupportLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<AdmSupportLine> findAllSupportSenderCriteria(final Long filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmSupportLine> criteriaQuery = builder.createQuery(AdmSupportLine.class);
		final Root<Support> rootSupport = criteriaQuery.from(Support.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subquery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		final Expression<String> expAutor = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		predicates.add(builder.equal(rootSupport.get("userId"), rootUser.get("id")));
		predicates.add(builder.isNotNull(rootSupport.get("adminId")));
		if(filter != null) {
			predicates.add(builder.equal(rootUser.get("id"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> message = builder.lower(rootSupport.get("message"));
			predicates.add(builder.like(message, "%" + search.toLowerCase() + "%"));
		}
		subquery.select(expAutor).where(builder.equal(subrootAutor.get("id"), rootSupport.get("adminId")));
		final Expression<String> autor = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootSupport.get("postedDate")) : builder.asc(rootSupport.get("postedDate")); break;
		default: order = hasDesc ? builder.desc(exp) : builder.asc(exp);
		}
		criteriaQuery.select(builder.construct(AdmSupportLine.class, rootUser, rootSupport, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmSupportLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
