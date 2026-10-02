package com.rinitec.algerieoffice.persistence.dao.inbox;

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

import org.springframework.stereotype.Repository;

import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;
import com.rinitec.algerieoffice.web.modal.user.feedback.NotificationLine;

@Repository
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllNotificationCriteria(final Long userId, final Boolean filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Notification> root = criteriaQuery.from(Notification.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(root.get("hasIcon")) : builder.isFalse(root.get("hasIcon")));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<NotificationLine> findAllNotificationCriteria(final Long userId, final Boolean filter, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NotificationLine> criteriaQuery = builder.createQuery(NotificationLine.class);
		final Root<Notification> root = criteriaQuery.from(Notification.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(filter ? builder.isTrue(root.get("hasIcon")) : builder.isFalse(root.get("hasIcon")));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("notifiedDate")) : builder.asc(root.get("notifiedDate")); break;
		default: order = hasDesc ? builder.desc(root.get("consulted")) : builder.asc(root.get("consulted"));
		}
		criteriaQuery.select(builder.construct(NotificationLine.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<NotificationLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
