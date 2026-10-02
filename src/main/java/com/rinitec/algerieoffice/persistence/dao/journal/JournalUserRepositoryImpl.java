package com.rinitec.algerieoffice.persistence.dao.journal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalUser;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardHistory;
import com.rinitec.algerieoffice.web.modal.user.dashboard.HistoryLine;

@Repository
public class JournalUserRepositoryImpl implements JournalUserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllJournalUserCriteria(final Long userId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<JournalUser> root = criteriaQuery.from(JournalUser.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<HistoryLine> findAllJouranlUserCriteria(final Long userId, final Integer filter, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<HistoryLine> criteriaQuery = builder.createQuery(HistoryLine.class);
		final Root<JournalUser> root = criteriaQuery.from(JournalUser.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("postedDate")) : builder.asc(root.get("postedDate")); break;
		default: order = hasDesc ? builder.desc(root.get("action")) : builder.asc(root.get("action"));
		}
		criteriaQuery.select(builder.construct(HistoryLine.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<HistoryLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<DashboardHistory> findLastDashboardHistory(final Long userId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DashboardHistory> criteriaQuery = builder.createQuery(DashboardHistory.class);
		final Root<JournalUser> root = criteriaQuery.from(JournalUser.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		criteriaQuery.select(builder.construct(DashboardHistory.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("postedDate")));
		final TypedQuery<DashboardHistory> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countJournalUser(final Long userId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<JournalUser> root = criteriaQuery.from(JournalUser.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(userId != null) {
			predicates.add(builder.equal(root.get("userId"), userId));
		}
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
