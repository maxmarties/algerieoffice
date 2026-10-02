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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmMessageLine;
import com.rinitec.algerieoffice.web.modal.inbox.MessageNotification;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePopup;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardMessage;
import com.rinitec.algerieoffice.web.modal.user.feedback.MessageLine;

@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<MessagePopup> findAllMessagePopupCriteria(final Long userId, final Long recepientId, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<MessagePopup> criteriaQuery = builder.createQuery(MessagePopup.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.or(builder.and(builder.equal(rootMessage.get("senderId"), userId), builder.equal(rootMessage.get("recepientId"), recepientId)), 
				builder.and(builder.equal(rootMessage.get("senderId"), recepientId), builder.equal(rootMessage.get("recepientId"), userId))));
		predicates.add(builder.equal(rootUser.get("id"), recepientId));
		criteriaQuery.select(builder.construct(MessagePopup.class, rootMessage, rootUser.get("id"), rootUser.get("hasAvatar")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootMessage.get("postedDate")));
		final TypedQuery<MessagePopup> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<MessageNotification> findAllMessageNotification(final Long userId, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<MessageNotification> criteriaQuery = builder.createQuery(MessageNotification.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<Message> subrootMessage = subquery.from(Message.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> subpredicates = new ArrayList<>();
		subpredicates.add(builder.or(builder.and(builder.equal(subrootMessage.get("senderId"), userId), builder.equal(subrootMessage.get("recepientId"), rootUser.get("id"))), 
				builder.and(builder.equal(subrootMessage.get("recepientId"), userId), builder.equal(subrootMessage.get("senderId"), rootUser.get("id")))));
		subquery.select(builder.greatest(subrootMessage.<DateTime>get("postedDate"))).where(subpredicates.toArray(new Predicate[0]));
		predicates.add(builder.or(builder.and(builder.equal(rootMessage.get("senderId"), userId), builder.equal(rootMessage.get("recepientId"), rootUser.get("id"))), 
				builder.and(builder.equal(rootMessage.get("recepientId"), userId), builder.equal(rootMessage.get("senderId"), rootUser.get("id")))));
		predicates.add(builder.equal(rootMessage.get("postedDate"), subquery.getSelection()));
		criteriaQuery.select(builder.construct(MessageNotification.class, rootUser, rootMessage));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootMessage.get("postedDate")));
		final TypedQuery<MessageNotification> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllUserMessageCriteria(final Long userId, boolean recevied) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<Message> subrootMessage = subquery.from(Message.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(recevied) {
			predicates.add(builder.equal(rootMessage.get("recepientId"), userId));
			predicates.add(builder.equal(rootMessage.get("senderId"), rootUser.get("id")));
			subquery.select(builder.greatest(subrootMessage.<DateTime>get("postedDate"))).where(builder.and(builder.equal(subrootMessage.get("recepientId"), userId), 
					builder.equal(subrootMessage.get("senderId"), rootUser.get("id"))));
		} else {
			predicates.add(builder.equal(rootMessage.get("senderId"), userId));
			predicates.add(builder.equal(rootMessage.get("recepientId"), rootUser.get("id")));
			subquery.select(builder.greatest(subrootMessage.<DateTime>get("postedDate"))).where(builder.and(builder.equal(subrootMessage.get("senderId"), userId), 
					builder.equal(subrootMessage.get("recepientId"), rootUser.get("id"))));
		}
		predicates.add(builder.equal(rootMessage.get("postedDate"), subquery.getSelection()));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), exp, rootMessage.get("postedDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootMessage.get("postedDate")));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllMessageCriteria(final Long userId, final boolean recevied, final Long filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(recevied) {
			predicates.add(builder.equal(rootMessage.get("recepientId"), userId));
			predicates.add(builder.equal(rootMessage.get("senderId"), rootUser.get("id")));
		} else {
			predicates.add(builder.equal(rootMessage.get("senderId"), userId));
			predicates.add(builder.equal(rootMessage.get("recepientId"), rootUser.get("id")));
		}
		if(filter != null) {
			predicates.add(builder.equal(rootUser.get("id"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> message = builder.lower(rootMessage.get("message"));
			predicates.add(builder.like(message, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootMessage)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<MessageLine> findAllMessageCriteria(final Long userId, final boolean recevied, final Long filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<MessageLine> criteriaQuery = builder.createQuery(MessageLine.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(recevied) {
			predicates.add(builder.equal(rootMessage.get("recepientId"), userId));
			predicates.add(builder.equal(rootMessage.get("senderId"), rootUser.get("id")));
		} else {
			predicates.add(builder.equal(rootMessage.get("senderId"), userId));
			predicates.add(builder.equal(rootMessage.get("recepientId"), rootUser.get("id")));
		}
		if(filter != null) {
			predicates.add(builder.equal(rootUser.get("id"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> message = builder.lower(rootMessage.get("message"));
			predicates.add(builder.like(message, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootMessage.get("postedDate")) : builder.asc(rootMessage.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootMessage.get("consulted")) : builder.asc(rootMessage.get("consulted"));
		}
		criteriaQuery.select(builder.construct(MessageLine.class, rootUser, rootMessage)).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<MessageLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<DashboardMessage> findLastDashboardMessage(final Long userId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DashboardMessage> criteriaQuery = builder.createQuery(DashboardMessage.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<Message> subrootMessage = subquery.from(Message.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> subpredicates = new ArrayList<>();
		subpredicates.add(builder.or(builder.and(builder.equal(subrootMessage.get("senderId"), userId), builder.equal(subrootMessage.get("recepientId"), rootUser.get("id"))), 
				builder.and(builder.equal(subrootMessage.get("recepientId"), userId), builder.equal(subrootMessage.get("senderId"), rootUser.get("id")))));
		subquery.select(builder.greatest(subrootMessage.<DateTime>get("postedDate"))).where(subpredicates.toArray(new Predicate[0]));
		predicates.add(builder.or(builder.and(builder.equal(rootMessage.get("senderId"), userId), builder.equal(rootMessage.get("recepientId"), rootUser.get("id"))), 
				builder.and(builder.equal(rootMessage.get("recepientId"), userId), builder.equal(rootMessage.get("senderId"), rootUser.get("id")))));
		predicates.add(builder.equal(rootMessage.get("postedDate"), subquery.getSelection()));
		criteriaQuery.select(builder.construct(DashboardMessage.class, rootUser, rootMessage));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootMessage.get("postedDate")));
		final TypedQuery<DashboardMessage> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllMessageAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Message> root = criteriaQuery.from(Message.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(root.get("senderId"), rootUser.get("id")));
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
	public List<AdmMessageLine> findAllMessageAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmMessageLine> criteriaQuery = builder.createQuery(AdmMessageLine.class);
		final Root<Message> rootMessage = criteriaQuery.from(Message.class);
		final Root<User> rootSender = criteriaQuery.from(User.class);
		final Root<User> rootRecepient = criteriaQuery.from(User.class);
		final Subquery<String> subqueryTradename = criteriaQuery.subquery(String.class);
		final Root<Company> subrootTradename = subqueryTradename.from(Company.class);
		final Expression<String> sender = builder.concat(rootSender.get("firstName"), builder.concat(" ", rootSender.get("lastName")));
		final Expression<String> recepient = builder.concat(rootRecepient.get("firstName"), builder.concat(" ", rootRecepient.get("lastName")));
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootMessage.get("senderId"), rootSender.get("id")));
		predicates.add(builder.equal(rootMessage.get("recepientId"), rootRecepient.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootMessage.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(sender), "%" + search.toLowerCase() + "%"));
		}
		subqueryTradename.select(subrootTradename.get("tradename")).where(builder.equal(subrootTradename.get("id"), rootSender.get("companyId")));
		final Expression<String> tradename = subqueryTradename.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootMessage.get("postedDate")) : builder.asc(rootMessage.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(sender) : builder.asc(sender); break;
		case 3: order = hasDesc ? builder.desc(recepient) : builder.asc(recepient); break;
		default: order = hasDesc ? builder.desc(rootMessage.get("consulted")) : builder.asc(rootMessage.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmMessageLine.class, rootMessage, rootSender, tradename, recepient));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmMessageLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
