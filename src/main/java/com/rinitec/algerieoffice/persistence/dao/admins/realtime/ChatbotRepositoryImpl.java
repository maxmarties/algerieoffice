package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

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

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmChatboterLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmChatbotLine;
import com.rinitec.algerieoffice.web.modal.company.communication.ChatbotLine;

@Repository
public class ChatbotRepositoryImpl implements ChatbotRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllChatbotCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Chatbot> root = criteriaQuery.from(Chatbot.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isNull(root.get("account")));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.or(builder.like(builder.lower(root.get("email")), "%" + search.toLowerCase() + "%"), 
					builder.like(builder.lower(root.get("message")), "%" + search.toLowerCase() + "%")));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ChatbotLine> findAllChatbotCriteria(final Long companyId, final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ChatbotLine> criteriaQuery = builder.createQuery(ChatbotLine.class);
		final Root<Chatbot> rootChatbot = criteriaQuery.from(Chatbot.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootChatbot.get("companyId"), companyId));
		predicates.add(builder.isNull(rootChatbot.get("account")));
		if(filter != null) {
			predicates.add(builder.between(rootChatbot.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.or(builder.like(builder.lower(rootChatbot.get("email")), "%" + search.toLowerCase() + "%"), 
					builder.like(builder.lower(rootChatbot.get("message")), "%" + search.toLowerCase() + "%")));
		}
		final Expression<String> expAutor = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(expAutor).where(builder.equal(subrootAutor.get("id"), rootChatbot.get("consultedBy")));
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootChatbot.get("postedDate")) : builder.asc(rootChatbot.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootChatbot.get("domaine")) : builder.asc(rootChatbot.get("domaine")); break;
		default: order = hasDesc ? builder.desc(rootChatbot.get("consulted")) : builder.asc(rootChatbot.get("consulted"));
		}
		criteriaQuery.select(builder.construct(ChatbotLine.class, rootChatbot, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ChatbotLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmChatbotCriteria(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Chatbot> root = criteriaQuery.from(Chatbot.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.or(builder.isNull(root.get("companyId")), builder.and(builder.isNotNull(root.get("companyId")), builder.isNotNull(root.get("account")))));
		if(filter != null) {
			predicates.add(filter ? builder.isNull(root.get("companyId")) : builder.and(builder.isNotNull(root.get("companyId")), builder.isNotNull(root.get("account"))));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(root.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmChatbotLine> findAllAdmChatbotCriteria(final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmChatbotLine> criteriaQuery = builder.createQuery(AdmChatbotLine.class);
		final Root<Chatbot> rootChatbot = criteriaQuery.from(Chatbot.class);
		final Subquery<String> subqueryTradename = criteriaQuery.subquery(String.class);
		final Root<Company> subrootTradename = subqueryTradename.from(Company.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.or(builder.isNull(rootChatbot.get("companyId")), builder.and(builder.isNotNull(rootChatbot.get("companyId")), builder.isNotNull(rootChatbot.get("account")))));
		if(filter != null) {
			predicates.add(filter ? builder.isNull(rootChatbot.get("companyId")) : builder.and(builder.isNotNull(rootChatbot.get("companyId")), builder.isNotNull(rootChatbot.get("account"))));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(rootChatbot.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		subqueryTradename.select(subrootTradename.get("tradename")).where(builder.equal(subrootTradename.get("id"), rootChatbot.get("companyId")));
		final Expression<String> expAutor = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(expAutor).where(builder.equal(subrootAutor.get("id"), rootChatbot.get("consultedBy")));
		final Expression<String> tradename = subqueryTradename.getSelection();
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootChatbot.get("postedDate")) : builder.asc(rootChatbot.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootChatbot.get("domaine")) : builder.asc(rootChatbot.get("domaine")); break;
		default: order = hasDesc ? builder.desc(rootChatbot.get("consulted")) : builder.asc(rootChatbot.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmChatbotLine.class, rootChatbot, tradename, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmChatbotLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmChatboterCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Chatbot> root = criteriaQuery.from(Chatbot.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isNotNull(root.get("companyId")));
		predicates.add(builder.isNull(root.get("account")));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(root.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(builder.lower(rootCompany.get("tradename")), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmChatboterLine> findAllAdmChatboterCriteria(final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmChatboterLine> criteriaQuery = builder.createQuery(AdmChatboterLine.class);
		final Root<Chatbot> rootChatbot = criteriaQuery.from(Chatbot.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isNotNull(rootChatbot.get("companyId")));
		predicates.add(builder.isNull(rootChatbot.get("account")));
		predicates.add(builder.equal(rootChatbot.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootChatbot.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(rootCompany.get("tradename")), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootChatbot.get("postedDate")) : builder.asc(rootChatbot.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootChatbot.get("consulted")) : builder.asc(rootChatbot.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmChatboterLine.class, rootChatbot, rootCompany));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmChatboterLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
