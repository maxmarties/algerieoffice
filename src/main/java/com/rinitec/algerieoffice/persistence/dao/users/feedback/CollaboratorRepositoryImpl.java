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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.company.communication.CollaboratorLine;

@Repository
public class CollaboratorRepositoryImpl implements CollaboratorRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllCollaboratorCompanyCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Collaborator> root = criteriaQuery.from(Collaborator.class);
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
	public List<CollaboratorLine> findAllCollaboratorCompanyCriteria(final Long companyId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CollaboratorLine> criteriaQuery = builder.createQuery(CollaboratorLine.class);
		final Root<Collaborator> rootCollaborator = criteriaQuery.from(Collaborator.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Boolean> subqueryProfile = criteriaQuery.subquery(Boolean.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final Subquery<Boolean> subqueryCollaborator = criteriaQuery.subquery(Boolean.class);
		final Root<Collaborator> subrootCollaborator = subqueryCollaborator.from(Collaborator.class);
		final Subquery<Long> subqueryIdentity = criteriaQuery.subquery(Long.class);
		final Root<Identity> subrootIdentity = subqueryIdentity.from(Identity.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final Subquery<Long> subqueryBlacklist = criteriaQuery.subquery(Long.class);
		final Root<BlacklistCompany> subrootBlacklist = subqueryBlacklist.from(BlacklistCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootCollaborator.get("companyId"), companyId));
		predicates.add(builder.equal(rootCollaborator.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			predicates.add(builder.between(rootCollaborator.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		subqueryProfile.select(subrootProfile.get("enabled")).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		subqueryCollaborator.select(subrootCollaborator.get("approuved")).where(builder.equal(subrootCollaborator.get("userId"), rootUser.get("id")));
		subqueryIdentity.select(builder.count(subrootIdentity)).where(builder.equal(subrootIdentity.get("identityID").get("userId"), rootUser.get("id")));
		final Expression<String> expAutor = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(expAutor).where(builder.equal(subrootAutor.get("id"), rootCollaborator.get("approuvedBy")));
		subqueryBlacklist.select(subrootBlacklist.get("userId")).where(builder.and(builder.equal(subrootBlacklist.get("companyId"), companyId), 
				builder.equal(subrootBlacklist.get("userId"), rootUser.get("id"))));
		final Expression<Boolean> enabled = subqueryProfile.getSelection();
		final Expression<Boolean> collaborator = subqueryCollaborator.getSelection();
		final Expression<Long> identities = subqueryIdentity.getSelection();
		final Expression<String> autor = subqueryAutor.getSelection();
		final Expression<Long> blacked = subqueryBlacklist.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCollaborator.get("postedDate")) : builder.asc(rootCollaborator.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootCollaborator.get("approuved")) : builder.asc(rootCollaborator.get("approuved"));
		}
		criteriaQuery.select(builder.construct(CollaboratorLine.class, rootUser, rootAccount.get("pseudo"), collaborator, enabled, 
				identities, rootCollaborator, autor, blacked));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<CollaboratorLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
