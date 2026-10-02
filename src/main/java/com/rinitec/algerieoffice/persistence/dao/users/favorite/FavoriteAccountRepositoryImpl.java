package com.rinitec.algerieoffice.persistence.dao.users.favorite;

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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteAccount;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedAccount;
import com.rinitec.algerieoffice.web.modal.user.globe.FavoriteAccountLine;

@Repository
public class FavoriteAccountRepositoryImpl implements FavoriteAccountRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllFavoriteAccountCriteria(final Long userId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteAccount> root = criteriaQuery.from(FavoriteAccount.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(!StringUtils.isEmpty(filter) || !StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			predicates.add(builder.equal(root.get("accountId"), rootUser.get("id")));
			if(!StringUtils.isEmpty(filter)) {
				final boolean hasPro = filter.equals("true");
				predicates.add(hasPro ? builder.isNotNull(rootUser.get("companyId")) : builder.isNull(rootUser.get("companyId")));
			}
			if(!StringUtils.isEmpty(search)) {
				final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
				final Expression<String> username = builder.lower(exp);
				predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
			}
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<FavoriteAccountLine> findAllFavoriteAccountCriteria(final Long userId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FavoriteAccountLine> criteriaQuery = builder.createQuery(FavoriteAccountLine.class);
		final Root<FavoriteAccount> rootFavorite = criteriaQuery.from(FavoriteAccount.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Profile> subqueryProfile = criteriaQuery.subquery(Profile.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final Subquery<Long> subqueryIdentity = criteriaQuery.subquery(Long.class);
		final Root<Identity> subrootIdentity = subqueryIdentity.from(Identity.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("accountId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(!StringUtils.isEmpty(filter)) {
			final boolean hasPro = filter.equals("true");
			predicates.add(hasPro ? builder.isNotNull(rootUser.get("companyId")) : builder.isNull(rootUser.get("companyId")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		subqueryProfile.select(subrootProfile).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		subqueryIdentity.select(builder.count(subrootIdentity)).where(builder.equal(subrootIdentity.get("identityID").get("userId"), rootUser.get("id")));
		final Expression<Profile> profile = subqueryProfile.getSelection();
		final Expression<String> tradename = subqueryCompany.getSelection();
		final Expression<Long> countIdentities = subqueryIdentity.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		case 2: order = hasDesc ? builder.desc(rootUser.get("companyId")) : builder.asc(rootUser.get("companyId")); break;
		case 3: order = hasDesc ? builder.desc(rootFavorite.get("alert")) : builder.asc(rootFavorite.get("alert")); break;
		default: order = hasDesc ? builder.desc(rootFavorite.get("postedDate")) : builder.asc(rootFavorite.get("postedDate"));
		}
		criteriaQuery.select(builder.construct(FavoriteAccountLine.class, rootFavorite, rootUser, rootAccount.get("pseudo"), profile, 
				tradename, countIdentities));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<FavoriteAccountLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<FollowedAccount> findAllFollowedAccount(final Long userId, final String search, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FollowedAccount> criteriaQuery = builder.createQuery(FollowedAccount.class);
		final Root<FavoriteAccount> rootFavorite = criteriaQuery.from(FavoriteAccount.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("accountId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryCompany.getSelection();
		criteriaQuery.select(builder.construct(FollowedAccount.class, rootUser, tradename, rootFavorite.get("alert")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(exp));
		final TypedQuery<FollowedAccount> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllFavoriteUser(final Long accountId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<FavoriteAccount> rootFavorite = criteriaQuery.from(FavoriteAccount.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("accountId"), accountId));
		predicates.add(builder.equal(rootFavorite.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootFavorite.get("alert")));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), rootUser.get("email")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
