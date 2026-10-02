package com.rinitec.algerieoffice.persistence.dao.users.feedback;

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

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.company.communication.EvaluationLine;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticEvaluation;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardEvaluation;
import com.rinitec.algerieoffice.web.modal.user.communication.UserEvaluationLine;

@Repository
public class EvaluationRepositoryImpl implements EvaluationRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllEvaluationCompanyCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Evaluation> root = criteriaQuery.from(Evaluation.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(root.get("userId"), rootUser.get("id")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EvaluationLine> findAllEvaluationCompanyCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EvaluationLine> criteriaQuery = builder.createQuery(EvaluationLine.class);
		final Root<Evaluation> rootEvaluation = criteriaQuery.from(Evaluation.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Boolean> subqueryProfile = criteriaQuery.subquery(Boolean.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final Subquery<Boolean> subqueryCollaborator = criteriaQuery.subquery(Boolean.class);
		final Root<Collaborator> subrootCollaborator = subqueryCollaborator.from(Collaborator.class);
		final Subquery<Long> subqueryIdentity = criteriaQuery.subquery(Long.class);
		final Root<Identity> subrootIdentity = subqueryIdentity.from(Identity.class);
		final Subquery<Long> subqueryBlacklist = criteriaQuery.subquery(Long.class);
		final Root<BlacklistCompany> subrootBlacklist = subqueryBlacklist.from(BlacklistCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootEvaluation.get("companyId"), companyId));
		predicates.add(builder.equal(rootEvaluation.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			predicates.add(builder.between(rootEvaluation.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		subqueryProfile.select(subrootProfile.get("enabled")).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		subqueryCollaborator.select(subrootCollaborator.get("approuved")).where(builder.equal(subrootCollaborator.get("userId"), rootUser.get("id")));
		subqueryIdentity.select(builder.count(subrootIdentity)).where(builder.equal(subrootIdentity.get("identityID").get("userId"), rootUser.get("id")));
		subqueryBlacklist.select(subrootBlacklist.get("userId")).where(builder.and(builder.equal(subrootBlacklist.get("companyId"), companyId), 
				builder.equal(subrootBlacklist.get("userId"), rootUser.get("id"))));
		final Expression<Boolean> enabled = subqueryProfile.getSelection();
		final Expression<Boolean> collaborator = subqueryCollaborator.getSelection();
		final Expression<Long> identities = subqueryIdentity.getSelection();
		final Expression<Long> blacked = subqueryBlacklist.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEvaluation.get("postedDate")) : builder.asc(rootEvaluation.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootEvaluation.get("note")) : builder.asc(rootEvaluation.get("note"));
		}
		criteriaQuery.select(builder.construct(EvaluationLine.class, rootUser, rootAccount.get("pseudo"), collaborator, enabled, identities, rootEvaluation, blacked));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EvaluationLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEvaluationUserCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Evaluation> root = criteriaQuery.from(Evaluation.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
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
	public List<UserEvaluationLine> findAllEvaluationUserCriteria(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserEvaluationLine> criteriaQuery = builder.createQuery(UserEvaluationLine.class);
		final Root<Evaluation> rootEvaluation = criteriaQuery.from(Evaluation.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvaluation.get("userId"), userId));
		predicates.add(builder.equal(rootEvaluation.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootEvaluation.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootCompany.get("id")));
		final Expression<String> url = subquerySeo.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEvaluation.get("postedDate")) : builder.asc(rootEvaluation.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootEvaluation.get("note")) : builder.asc(rootEvaluation.get("note"));
		}
		criteriaQuery.select(builder.construct(UserEvaluationLine.class, rootCompany, url, rootEvaluation));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<UserEvaluationLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public AnalyticEvaluation findAnalyticEvaluation(final Long companyId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnalyticEvaluation> criteriaQuery = builder.createQuery(AnalyticEvaluation.class);
		final Root<Evaluation> root = criteriaQuery.from(Evaluation.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<Evaluation> subrootLiked = subqueryLiked.from(Evaluation.class);
		final Subquery<Double> subqueryNote = criteriaQuery.subquery(Double.class);
		final Root<Evaluation> subrootNote = subqueryNote.from(Evaluation.class);
		final Subquery<Long> subqueryCount = criteriaQuery.subquery(Long.class);
		final Root<Evaluation> subrootCount = subqueryCount.from(Evaluation.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesLiked = new ArrayList<>();
		final List<Predicate> predicatesNote = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicatesLiked.add(builder.equal(subrootLiked.get("companyId"), companyId));
		predicatesLiked.add(builder.isTrue(subrootLiked.get("liked")));
		predicatesNote.add(builder.equal(subrootNote.get("companyId"), companyId));
		if(filter != null) {
			final DateTime begin = ParseUtil.getBeginDate(filter);
			final DateTime end = ParseUtil.getEndDate(filter);
			predicates.add(builder.between(root.get("postedDate"), begin, end));
			predicatesLiked.add(builder.between(subrootLiked.get("postedDate"), begin, end));
			predicatesNote.add(builder.between(subrootNote.get("postedDate"), begin, end));
		}
		subqueryLiked.select(builder.count(subrootLiked)).where(predicatesLiked.toArray(new Predicate[0]));
		subqueryNote.select(builder.avg(subrootNote.get("note"))).where(predicatesNote.toArray(new Predicate[0]));
		subqueryCount.select(builder.count(subrootCount));
		final Expression<Long> countLiked = subqueryLiked.getSelection();
		final Expression<Double> avgNote = subqueryNote.getSelection();
		final Expression<Long> count = subqueryCount.getSelection();
		criteriaQuery.select(builder.construct(AnalyticEvaluation.class, builder.count(root), countLiked, avgNote, count));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<AnalyticEvaluation> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public List<DashboardEvaluation> findLastDashboardEvaluation(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<DashboardEvaluation> criteriaQuery = builder.createQuery(DashboardEvaluation.class);
		final Root<Evaluation> rootEvaluation = criteriaQuery.from(Evaluation.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvaluation.get("companyId"), companyId));
		predicates.add(builder.equal(rootEvaluation.get("userId"), rootUser.get("id")));
		predicates.add(builder.notEqual(rootUser.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryCompany.getSelection();
		criteriaQuery.select(builder.construct(DashboardEvaluation.class, rootUser, tradename, rootEvaluation.get("liked"),  rootEvaluation.get("note")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEvaluation.get("postedDate")));
		final TypedQuery<DashboardEvaluation> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
